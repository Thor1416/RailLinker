package com.railweb.trafficmgt.domain.train;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;

import javax.measure.Quantity;
import javax.measure.quantity.Speed;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

import com.railweb.admin.domain.OperatingCompagny;
import com.railweb.shared.converters.SpeedConverter;
import com.railweb.shared.domain.base.AbstractAggregateRoot;
import com.railweb.shared.domain.i18n.TranslatedString;
import com.railweb.shared.domain.util.ReferenceHolder;
import com.railweb.trafficmgt.domain.TimeIntervalList;
import com.railweb.trafficmgt.domain.TrackSystem;
import com.railweb.trafficmgt.domain.TrainCycleType;
import com.railweb.trafficmgt.domain.TrainNumber;
import com.railweb.trafficmgt.domain.TrainsCycleItem;
import com.railweb.trafficmgt.domain.computation.TrainCalculator;
import com.railweb.trafficmgt.domain.computation.TrainRouteSelection;
import com.railweb.trafficmgt.domain.events.LineTrackChangedEvent;
import com.railweb.trafficmgt.domain.events.NodeChangedEvent;
import com.railweb.trafficmgt.domain.events.SpecialTrainTimeIntervalList;
import com.railweb.trafficmgt.domain.events.SpecialTrainTimeIntervalList.Type;
import com.railweb.trafficmgt.domain.events.TimeAfterChangedEvent;
import com.railweb.trafficmgt.domain.events.TimeBeforeChangedEvent;
import com.railweb.trafficmgt.domain.events.TrainChangedEvent;
import com.railweb.trafficmgt.domain.events.TrainClassChangedEvent;
import com.railweb.trafficmgt.domain.events.TrainDescriptionChangedEvent;
import com.railweb.trafficmgt.domain.events.TrainNameChangedEvent;
import com.railweb.trafficmgt.domain.events.TrainNumberUpdatedEvent;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ids.PowerSystemId;
import com.railweb.trafficmgt.domain.ids.TimetableId;
import com.railweb.trafficmgt.domain.ids.TrainId;
import com.railweb.trafficmgt.domain.network.LineTrack;
import com.railweb.trafficmgt.domain.network.Node;
import com.railweb.trafficmgt.domain.network.NodeTrack;
import com.railweb.trafficmgt.exception.IllegalSpeedException;
import com.railweb.trafficmgt.exception.IllegalStopTimeException;
import com.railweb.trafficmgt.exception.TrainStopNotValidException;
import com.railweb.trafficmgt.exception.TrainStopOutOfRangeException;
import com.railweb.trafficmgt.exception.TrainStopTooEarlyException;

import lombok.Data;
import lombok.ToString;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

/**
 * Aggregate root representing a train, which is one of the central clases in 
 * the domain model, as it scheduled to leave a station and at least one new station. 
 * It is identified by its train number and timetable or a code.
 * 
 * It always has a train class, a top speed and som stops.
 * 
 * @author Thorbjørn Simonsen
 * @version 0.1
 */
@Data
@ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@Configurable(preConstruction = true)
@Audited
public class Train extends AbstractAggregateRoot<TrainId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7854208827444127515L;
	@EmbeddedId
	private TrainId id;
	@NonNull
	private NetworkId net;
	private String code;

	/* Weight and tractive force */
	@ManyToMany
	@JoinTable(name = "train_trainType", joinColumns = { @JoinColumn(name = "train_number"),
			@JoinColumn(name = "timetableId") }, inverseJoinColumns = @JoinColumn(name = "trainTypeId"))
	@NonNull
	private TrainType trainType;

	@Convert(converter = SpeedConverter.class)
	private Quantity<Speed> topSpeed;

	@OneToMany
	@JoinTable(name = "trains_trainStop", joinColumns = { @JoinColumn(name = "train_number", referencedColumnName = "train_number"),
			@JoinColumn(name = "timetableId", referencedColumnName = "timtableId") }, inverseJoinColumns = @JoinColumn(name = "trainStop_id", referencedColumnName = "id"))
	@MapKeyColumn(name = "StopNumber")
	private Map<Integer, TrainStop> trainStops;

	private TimeIntervalList timeIntervalList;
	@OneToOne(fetch = FetchType.LAZY)
	private Train previousJoinedTrain;
	@OneToOne(fetch = FetchType.LAZY)
	private Train nextJoinedTrain;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "trains_trainsCyleMap", joinColumns = { @JoinColumn(name = "train_number", referencedColumnName = "train_number"),
			@JoinColumn(name = "timetableId", referencedColumnName = "timetableId") }, inverseJoinColumns = @JoinColumn(name = "cycleMapId", referencedColumnName = "id"))
	@MapKeyJoinColumn(name = "trainCycleTypeId")
	private Map<TrainCycleType, TrainCycleMap> cycles;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Train_RunDay", joinColumns = { @JoinColumn(name = "train_number", referencedColumnName = "train_number"),
			@JoinColumn(name = "timetableId", referencedColumnName = "timetableId") }, inverseJoinColumns = @JoinColumn(name = "rundayId", referencedColumnName = "id"))
	private RunDays rundays;
	
	@ElementCollection
	@CollectionTable(name="running_period", joinColumns= {@JoinColumn(name = "train_number"),
			@JoinColumn(name = "timetableId")})
	private SortedSet<Period> runningPeriod;
	
	@OneToMany
	private OperatingCompagny operator;

	/* Technological times */
	@OneToOne(fetch = FetchType.LAZY)
	private TimeInterval timeBefore;
	@OneToOne(fetch = FetchType.LAZY)
	private TimeInterval timeAfter;

	@Transient
	private TrainNameDelegate trainNameDelegate;
	@Embedded
	private PowerSystemId powersystem;
	@ManyToOne
	private TrainClass trainClass;
	@ManyToOne
	private TrackSystem trackSystem;

	private String description;
	@ElementCollection
	private Set<String> notes;

	private String logoPath;
	
	private Duration changeDirectionStop;
	private TrainStatus proccesStatus;

	/* Cached Map for train cycles */
	@Embedded
	private TrainCachedCycles cachedCycles;

	public enum NameType {
		NORMAL, COMPLETE
	}

	public Train(ApplicationContext applicationContext, TrainNumber number, TimetableId timetableId) {
		super(applicationContext, new TrainId(number, timetableId));
		this.trainNameDelegate = new TrainNameDelegate(this);
		timeIntervalList = new TimeIntervalList();
		trainStops = new HashMap<>();
		cachedCycles = new TrainCachedCycles();
	}

	public void setNumber(TrainNumber number) {
		if (this.id.getTrainNumber() != number) {
			TimetableId timetableId = this.getId().getTimetableId();
			TrainNumber oldNumber = this.id.getTrainNumber();
			this.id = new TrainId(number, timetableId);
			registerEvent(new TrainNumberUpdatedEvent(this.id, oldNumber, number));
			this.refreshCachedNames();
		}
	}

	public String getDefaultName() {
		return trainNameDelegate.getName();
	}

	public String getDefaultCompleteName() {
		return trainNameDelegate.getCompleteName();
	}

	public TranslatedString getName(NameType nameType) {
		return trainNameDelegate.getName(nameType);
	}

	/**
	 * Change the description of a train
	 */
	public void setDeScription(String description) {
		if (!Objects.equals(this.description, description)) {
			String oldDesc = this.description;
			this.description = description;
			registerEvent(new TrainDescriptionChangedEvent(this.id, oldDesc, description));
			this.refreshCachedNames();
		}
	}

	/**
	 * @param the speed to set
	 * @throws IllegalSpeedException    if the speed is 0 or less
	 * @throws IllegalArgementException if speed is null
	 */

	public void setTopSpeed(Quantity<Speed> topSpeed) throws IllegalSpeedException {
		if (topSpeed == null) {
			throw new IllegalArgumentException("Speed cannot be null for train:" + this.id.getTrainNumber());
		}
		ComparableQuantity<Speed> topSpeed2 = (ComparableQuantity<Speed>) topSpeed;
		if (!topSpeed2.isGreaterThan(Quantities.getQuantity(0, Units.KILOMETRE_PER_HOUR))) {
			throw new IllegalSpeedException("The speed of a train cannot be 0 or less");
		}
		if (!Objects.equals(topSpeed, this.topSpeed)) {
			this.topSpeed = topSpeed;
		}
	}

	public void refreshCachedNames() {
		trainNameDelegate.refreshCachedNames();

	}

	public void setTrainClass(TrainClass trainClass, TrainCalculator calculator) {
		if (!Objects.equals(this.trainClass, trainClass)) {
			TrainClass oldTrainClass = this.trainClass;
			this.trainClass = trainClass;
			calculator.recalculate(this, 0);
			registerEvent(new TrainClassChangedEvent(this.id, oldTrainClass, trainClass));
			this.refreshCachedNames();
		}
	}

	public boolean isConflicting() {
		for (TimeInterval interval : timeIntervalList) {
			if (interval.isOverlapping()) {
				return true;
			}
		}
		return ((timeBefore != null && timeBefore.isOverlapping()) || (timeAfter != null && timeAfter.isOverlapping()));
	}

	/**
	 * @return set of trains with conflicting times
	 */
	public Set<Train> getConflictingTrains() {
		ReferenceHolder<Set<Train>> conflictsRef = new ReferenceHolder<>();
		for (TimeInterval interval : timeIntervalList) {
			this.addOverlappingTrains(interval, conflictsRef);
		}
		this.addOverlappingTrains(timeBefore, conflictsRef);
		this.addOverlappingTrains(timeAfter, conflictsRef);
		if (conflictsRef.get() == null) {
			return Collections.emptySet();
		} else {
			return conflictsRef.get();
		}

	}

	public TrainStop addTrainStop(Integer number, TrainStop trainStop)
			throws TrainStopOutOfRangeException, IllegalArgumentException, TrainStopNotValidException, TrainStopTooEarlyException {
		if (trainStop == null) {
			throw new IllegalArgumentException("Trainstop cannot be null");
		}
		if (this.trainStops.size() < (number - 1)) {
			throw new TrainStopOutOfRangeException("Cannot insert Trainstop for" + this.id.toString() + "at position" + number);
		}
		if (!trainStop.isValid()) {
			throw new TrainStopNotValidException("Train stop not valid");
		}

		if (!this.timeIntervalList.stream().filter(t -> t.getOwner().equals(trainStop.getStation())).findFirst().isPresent()) {
			throw new TrainStopNotValidException("Trainstop not on specified train");
		}
		if (this.trainStops.get(number - 1).isBefore(trainStop)) {
			this.trainStops.put(number, trainStop);
			return trainStop;
		} else {
			throw new TrainStopTooEarlyException(
					"Trainstop" + trainStop.toString() + "is before last trainstop:" + this.trainStops.get(number - 1).toString());
		}
	}

	public Node getStartNode() {
		return timeIntervalList.get(0).getOwnerAsNode();
	}

	public boolean checkPlatforms() {
		for (TimeInterval interval : timeIntervalList) {
			if (!interval.isPlatformOK()) {
				return false;
			}
		}
		return true;
	}

	public Node getEndNode() {
		return timeIntervalList.get(timeIntervalList.size() - 1).getOwnerAsNode();
	}

	public OffsetTime getStartTime(ZoneOffset offset) {
		return timeIntervalList.get(0).getStart(offset);
	}

	/**
	 * @param length technological time before train start
	 */
	public void setTimeBefore(Duration length, Instant clock) {
		if (length.isNegative()) {
			throw new IllegalArgumentException("Time before cannot be negative");
		}
		Duration oldLength = this.timeBefore.getLength();
		if (length.isZero() && timeBefore != null) {
			if (isAttached()) {
				timeBefore.removeFromOwner(clock);
			}
			timeBefore = null;
			registerEvent(new TimeBeforeChangedEvent(this.getId(), oldLength, length,clock));
		} else if (!length.isZero() && timeBefore == null) {
			TimeInterval firstInterval = this.getFirstInterval();
			timeBefore = new TimeInterval(this, firstInterval.getOwner(), firstInterval.getStart().plus(length).plusSeconds(1),
					firstInterval.getStart().minusSeconds(1), firstInterval.getTrack());
			if (isAttached()) {
				timeBefore.updateInOwner();
			}
			registerEvent(new TimeBeforeChangedEvent(this.getId(), oldLength, length, clock));
		}
	}

	public void setTimeAfter(Duration length, Instant clock) {
		if (length.isNegative()) {
			throw new IllegalArgumentException("Time after cannot be negative");
		}
		Duration oldLength = this.timeAfter.getLength();
		if (length.isZero() && timeAfter != null) {
			if (isAttached()) {
				timeAfter.removeFromOwner(clock);
			}
			timeAfter = null;
			registerEvent(new TimeAfterChangedEvent(this.getId(), oldLength, length, clock));

		} else if (!length.isZero() && timeAfter == null) {
			TimeInterval lastInterval = this.getLastInterval();
			timeAfter = new TimeInterval(this, lastInterval.getOwner(), lastInterval.getEnd().plusSeconds(1),
					lastInterval.getEnd().plus(length).minusSeconds(1), lastInterval.getTrack());
			if (isAttached()) {
				timeAfter.addToOwnerWithoutCheck();
			}
			registerEvent(new TimeAfterChangedEvent(this.getId(), oldLength, length, clock));
		} else if (!length.isZero()) {
			TimeInterval lastInterval = this.getLastInterval();
			// recalculate time
			OffsetTime start = lastInterval.getEnd().plusSeconds(1);
			OffsetTime end = start.plus(length).plusSeconds(1);
			timeAfter.setInterval(start, end);
			timeAfter.setTrack(lastInterval.getTrack());
			if (isAttached()) {
				timeAfter.updateInOwner();
			}
			if (!oldLength.equals(length)) {
				registerEvent(new TimeAfterChangedEvent(this.getId(), oldLength, length, clock));
			}
		} else {

		}
	}

	public Duration getTimeBefore() {
		return (timeBefore != null) ? timeBefore.getLength().plusSeconds(2) : Duration.ZERO;
	}

	public Duration getTimeAfter() {
		return (timeAfter != null) ? timeAfter.getLength().plusSeconds(2) : Duration.ZERO;
	}

	public TimeInterval getLastInterval() {
		// TODO Auto-generated method stub
		return null;
	}

	public TimeInterval getFirstInterval() {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isAttached() {
		// TODO Auto-generated method stub
		return false;
	}

	public void move(OffsetTime time, TrainCalculator calculator, Instant clock) {
		if (timeIntervalList.get(0).getStart(time.getOffset()).compareTo(time) == 0) {
			return;
		}
		timeIntervalList.get(0).move(time);
		calculator.recalculate(this, 0);
		registerEvent(new TrainChangedEvent(this.getId(), new SpecialTrainTimeIntervalList(Type.MOVED, 0, 0), clock));
	}

	public void changeStopTime(TimeInterval nodeInterval, Duration length, 
									TrainCalculator calculator,  Instant clock) 
											throws IllegalStopTimeException {
		// check time
		if (length.isNegative()) {
			throw new IllegalStopTimeException("Stop time cannot be negative");
		}
		if (changeDirectionStop != null && this.checkTimeIntervalChangeDirectionStop(nodeInterval, length)) {
			length = changeDirectionStop;
		}
		Duration oldLength = nodeInterval.getLength();
		if (length.compareTo(oldLength) == 0)
			return;

		int index = timeIntervalList.indexOf(nodeInterval);
		if (index == -1 || index == 0 || index == (timeIntervalList.size() - 1) || !nodeInterval.isOwner()) {
			throw new IllegalStopTimeException("Cannot change interval");
		}
		int changedIndex = index;

		// change stop time
		nodeInterval.setLength(oldLength);

		// compute running time of line before if stop is changed
		if (length.isZero() || oldLength.isZero()) {
			changedIndex = changedIndex - 1;
		}
		calculator.recalculate(this, index);
		
		SpecialTrainTimeIntervalList sttil = new SpecialTrainTimeIntervalList(Type.STOP_TIME, changedIndex, index);
		registerEvent(new TrainChangedEvent(this.id(),sttil, clock));
	}

	/**
	 * changes velocity of the train on the specified line.
	 *
	 * @param lineInterval line interval
	 * @param speed        velocity to be set
	 * @param addedTime    added time
	 */
	public void changeSpeedAndAddedTime(TimeInterval lineInterval, Quantity<Speed> speed,
			Duration addedTime, TrainCalculator calculator, Instant clock) {

		int index = timeIntervalList.indexOf(lineInterval);
		if (index == -1 || !lineInterval.isLineOwner()) {
			throw new IllegalArgumentException("Cannot change interval");
		}

		lineInterval.setSpeedLimit(speed);
		lineInterval.setAddedTime(addedTime);
		int changedIndex = index;

		// line interval before (if there is not stop ...)
		if ((index - 2) >= 0 && timeIntervalList.get(index - 1).getLength().isZero()) {
			changedIndex = index - 2;
		}

		calculator.recalculate(this, changedIndex);
		SpecialTrainTimeIntervalList sttil = new SpecialTrainTimeIntervalList(Type.STOP_TIME, changedIndex, index);
		registerEvent(new TrainChangedEvent(this.id(), sttil, clock));
	}

	public void changeNodeTrack(TimeInterval interval, NodeTrack track, 
			TrainRouteSelection selection, TrainCalculator calculator, Instant clock) {
		if (!interval.isNodeOwner()) {
			throw new IllegalArgumentException("No node interval.");

		}
		if (interval.getTrack().equals(track)) {
			return;
		}
		selection.changeTrack(this, interval, track);
		interval.setTrack(track);

		// update - from/to straight could change
		calculator.recalculate(this, 0);
		
		SpecialTrainTimeIntervalList sttil = new SpecialTrainTimeIntervalList(Type.TRACK, 0, timeIntervalList.indexOf(interval));
		registerEvent(new TrainChangedEvent(this.id(), sttil, clock));
		NodeId nid = track.getOwnerAsNode().getId();
		registerEvent(new NodeChangedEvent(nid, this.id(), sttil, clock));
	}

	public void changeLineTrack(TimeInterval lineInterval, LineTrack lineTrack, 
			TrainRouteSelection selection, TrainCalculator calculator, Instant clock) {
		if (!lineInterval.isLineOwner()) {
			throw new IllegalArgumentException("No line interval");
		}
		if (lineInterval.getTrack().equals(lineTrack)) {
			return;
		}
		selection.changeTrack(this, lineInterval, lineTrack);

		// update - from/to straight could change
		calculator.recalculate(this, 0);
		
		SpecialTrainTimeIntervalList sttil = new SpecialTrainTimeIntervalList(Type.TRACK, 0, timeIntervalList.indexOf(lineInterval));
		registerEvent(new LineTrackChangedEvent(this.getId(), sttil, clock));

	}

	private void updateTechnologicalTimes() {
		// TODO Auto-generated method stub

	}

	private void addOverlappingTrains(TimeInterval interval, ReferenceHolder<Set<Train>> conflictsRef) {
		if (interval != null && interval.isOverlapping()) {
			for (TimeInterval i2 : interval.getOverlappingIntervals()) {
				if (conflictsRef.get() == null) {
					conflictsRef.set(new HashSet<>());
				}
				conflictsRef.get().add(i2.getTrain());
			}
		}
	}

	private boolean checkTimeIntervalChangeDirectionStop(TimeInterval interval, Duration length) {
		return interval.isOwner() && length.isZero() && interval.isDirectionChange();
	}

	public void fireEvent(TrainNameChangedEvent event) {
		registerEvent(event);

	}

	public Collection<TrainsCycleItem> getCycleItemsForInterval(TrainCycleType type, TimeInterval interval) {
		return null;
	}

	public Map<TrainCycleType, TrainCycleMap> getCycles() {
		return Collections.unmodifiableMap(cycles);
	}

	protected void addCycleItem(TrainsCycleItem item, Integer number) {
		TrainCycleType cycleType = item.getCycle().getType();
		RunDays rundays = item.getCycle().getRundays();
		cachedCycles.addCycleItem(timeIntervalList, cycles.get(cycleType));
	}

	public TimeInterval getInterval(TimeInterval timeInterval, int relativeIndex) {
		return timeIntervalList.getInterval(timeInterval, relativeIndex);
	}

	@Override
	protected AbstractAggregateRoot<TrainId>.AggregateRootBehavior<?> initialBehavior() {
		// TODO Auto-generated method stub
		return null;
	}
}
