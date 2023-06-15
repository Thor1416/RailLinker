package com.railweb.trafficmgt.domain.train;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.measure.Quantity;
import javax.measure.quantity.Speed;
import javax.persistence.Basic;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.railweb.shared.converters.SpeedConverter;
import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.TrackConnector;
import com.railweb.trafficmgt.domain.ids.LineId;
import com.railweb.trafficmgt.domain.ids.NetSegmentId;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ids.TimeIntervalId;
import com.railweb.trafficmgt.domain.ids.TrackId;
import com.railweb.trafficmgt.domain.network.Line;
import com.railweb.trafficmgt.domain.network.NodeTrack;
import com.railweb.trafficmgt.domain.network.Track;
import com.railweb.trafficmgt.domain.train.Train.NameType;
import com.railweb.trafficontrol.model.Block;

import lombok.Builder;
import lombok.Data;

@Data
@Entity
@MappedSuperclass
public abstract class TimeInterval<T extends NetSegmentId<?>> extends AbstractEntity<TimeIntervalId> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4924257378896632472L;
	@Embedded
	private Interval interval;
	@ManyToOne
	private Train train;
	@ManyToOne
	@JoinColumn(name="ownerId")
	private T ownerId;
	
	private TrackId trackId;
	
	@Convert(converter = SpeedConverter.class)
	private Quantity<Speed> speedLimit;
	
	private Duration addedTime;
	
	@JoinTable(name = "overlaps",
			joinColumns = @JoinColumn(name="interval1", referencedColumnName="timeIntervalId"),
			inverseJoinColumns = @JoinColumn(name="interval2",referencedColumnName="timeIntervalId")
	)
	@ManyToMany
	private Set<TimeInterval<T>> overlappingIntervals;
	
	@Enumerated(EnumType.ORDINAL)
	private TimeIntervalDirection direction;
	
	@Convert(converter = SpeedConverter.class)
	private Quantity<Speed> usedSpeed;
		
	private Boolean changed;
	
	@Basic(fetch=FetchType.LAZY)
	@ElementCollection
	private Set<String> comments;

	
	private NodeId fromNode;
	
	private NodeId toNode;
	
	private Boolean ignoreLength;
	private Boolean showComments;
	
	@ManyToOne
	private TimeInterval<?> previousInterval;
	@ManyToOne
	private TimeInterval<?> nextInterval;

	@ManyToOne
	private TrackConnector toTrackConnector;
	@ManyToOne
	private TrackConnector fromTrackConnector;
	
	
	@Transient
	private TimeIntervalCalculation calculation;
	
	  /**
     * creates instance of an time interval.
     *
     * @param train train
     * @param ownerId id for the owner owner (node track, node, ...)
     * @param start start time
     * @param end end time
     * @param speed speed for line time interval
     * @param direction direction of the line time interval
     * @param track track
     * @param addedTime added time
     */
	public TimeInterval(Train train, OffsetTime start, OffsetTime end,
			Quantity<Speed> speed, TimeIntervalDirection direction,
			TrackId trackId, Duration addedTime) {
		this.setTrain(train);
		this.interval = IntervalFactory.createInterval(start,end);
		this.direction = direction;
		this.trackId = trackId;
		this.addedTime = addedTime;
		this.speedLimit = speed;
	}
	public TimeInterval(Train train, OffsetTime start, OffsetTime end, TrackId track) {
		this(train,start, end, null, null, track, Duration.ZERO);
	}
	
	
	//For JPA use only
	public TimeInterval() {}
	
	@PostLoad
	private void init() {
		if(this.train != null) {
			this.calculation = new TimeIntervalCalculation(train, this);
		}
	}
	
	
	public OffsetTime getEnd() {
		return interval.getEnd();
	}
	
	/** @return end time
	 */
	public OffsetTime getEnd(ZoneOffset offset) {
		if(interval.getEnd().getOffset().equals(offset)) {
			return interval.getEnd();
		}else {
			return interval.getEnd().withOffsetSameInstant(offset);
		}
		
	}
	
	public OffsetTime getStart() {
		return interval.getStart();
	}
	
	public OffsetTime getStart(ZoneOffset offset) {
		if(interval.getStart().getOffset().equals(offset)) {
			return interval.getStart();
		}else {
			return interval.getStart().withOffsetSameInstant(offset);
		}
	}
	
	
	public void setEnd(OffsetTime end) {
		if(!interval.getEnd().equals(end)){
			this.setIntervalImpl(interval.getStart(),end);
			this.setChanged(true);
		}
	}
	
	public void setStart(OffsetTime start) {
		if(!interval.getStart().isEqual(start)) {
			this.setIntervalImpl(start, interval.getEnd());
			this.setChanged(true);
		}
	}
	
	public void setTrain(Train train) {
		this.train= train;
		this.calculation = new TimeIntervalCalculation(train,this);
	}
	
	  /**
     * compares intervals for route part. Open/unbounded interval. It uses
     * normalized intervals.
     *
     * @param o interval
     * @return comparison
     */
	public int compareOpenNormalized(TimeInterval<T> o) {
		return this.getInterval().compareOpenNormalized(o.getInterval());
	}
	 /**
     * compares intervals for trains. Closed/bounded interval. It uses
     * normalized intervals.
     *
     * @param interval2 interval
     * @return comparison
     */
    public int compareClosedNormalized(TimeInterval<? extends NetSegmentId<?>> interval2) {
        return this.getInterval().compareClosedNormalized(interval2.getInterval());
    }
    
    public String toString() {
    	String ownerStr= ownerId.toString();
    	OffsetTime start = this.getInterval().getStart();
    	OffsetTime end = this.getInterval().getEnd();
    	if(start.compareTo(end) !=0) {
    		return String.format("%s[%s]{%s}(%s,%s)", ownerStr, getTrain().getName(NameType.NORMAL).translate(),
    				getTrackString(),start.toString(),end.toString());
    	}else {
    		return String.format("%s[%s]{%s}(%s)", ownerStr, getTrain().getName(NameType.NORMAL).translate(),
    				getTrackString(),end.toString());
    	}
    }
	
	private String getTrackString() {
		return getTrackId() == null ? "-": getTrackId().toString();
	}
	
	public void setSpeedLimit(Quantity<Speed> speedLimit) {
		if(!this.speedLimit.isEquivalentTo(speedLimit)) {
			this.speedLimit = speedLimit;
			this.setChanged(true);
		}
	}
	
	public void setSpeed(Quantity<Speed> speed) {
		if(!this.usedSpeed.isEquivalentTo(speed)) {
			this.usedSpeed = speed;
			this.setChanged(true);
		}
	}

    /**
     * @param addedTime the addedTime to set
     */
	public void setAddedTime(Duration addedTime){
		if(this.addedTime.compareTo(addedTime) != 0) {
			this.addedTime = addedTime;
			this.setChanged(true);
		}
	}
    /**
     * @param track the track to set
     */
	public void setTrackId(TrackId trackId) {
		if(trackId != this.trackId) {
			this.trackId = trackId;
			this.setChanged(true);
		}
	}
	
	public Set<TimeInterval<T>> getOverlappingInterval(){
		if(overlappingIntervals==null) {
			overlappingIntervals = new HashSet<>();
		}
		return overlappingIntervals;
	}
	
	

	public boolean isOverlapping() {
		return (overlappingIntervals != null) && overlappingIntervals.isEmpty();
	}

	 /**
     * shifts time interval with specified amount of time.
     *
     * @param timeShift shift time
     */
	public void shift(Duration shift) {
		if(!shift.isZero()) {
			this.setIntervalImpl(this.getInterval().getStart().plus(shift),
								this.getInterval().getEnd().plus(shift));
			this.setChanged(true);
		}
	}
	
	 /**
     * moves interval to specified starting time.
     */
	public void move(OffsetTime aStart) {
		if(!this.interval.getStart().isEqual(aStart)) {
			Duration length = this.interval.getLength();
			this.setIntervalImpl(aStart, aStart.plus(length));
			this.setChanged(true);
		}
	}
	 /**
     * sets length of the interval.
     *
     * @param length new length of the interval
     */
	public void setLength(Duration length) {
		if(this.getInterval().getLength().compareTo(length)!=0) {
			this.setIntervalImpl(interval.getStart(), interval.getStart().plus(length));
			this.setChanged(true);
		}
	}
	public Duration getLength() {
		return this.interval.getLength();
	}
	
	
	public void setInterval(OffsetTime start, OffsetTime end) {
		if(start.compareTo(interval.getStart()) != 0 || 
				end.compareTo(interval.getEnd()) != 0) {
			this.setIntervalImpl(start, end);
			this.setChanged(true);
		}
	}
	
	public Optional<TrackConnector> getFromTrackConnector(){
		TimeInterval<?> prevInterval = this.getPreviousTrainInterval();
		if(isNodeOwner()){
			return Optional.ofNullable(fromTrackConnector);
		}else {
			return Optional.ofNullable(prevInterval.getToTrackConnector());
		}
	}
	
	public TimeInterval<?> getTrainInterval(int relativeIndex) {
		return train.getInterval(this,relativeIndex);
	}
	
	public TimeInterval<?> getPreviousTrainInterval() {
		return this.getTrainInterval(-1);
	}

	/**
     * @return true in case it is inner node interval and both track connectors are
     *         on the same side of station
     */
	public boolean isDirectionChange() {
		if(this.isLineOwner()) {
			// line interval cannot be direction change
			return false;
		}else {
			return this.getFromTrackConnector()
			.map(c->this.getToTrackConnector().map(c2->c.getOrientation() == c.getOrientation())
					.orElse(false)).orElse(false);
		}
	}
	
	public void removeFromOwner(Instant time) {
		if(!isAttached()) {
			 throw new IllegalStateException("Time interval is not attached.");
		}
		ownerId=null;
		
	
	}
	
	public boolean isAttached() {
		// TODO Auto-generated method stub
		return false;
	}

	public TimeInterval getNextTrainInterval() {
		return this.getTrainInterval(1);
	}

	private boolean isStop() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setList(TimeIntervalListId timeIntervalListId) {
		// TODO Auto-generated method stub
		
	}

	private boolean isBlockOwner() {
		// TODO Auto-generated method stub
		return false;
	}
	private Block getOwnerAsBlock() {
		// TODO Auto-generated method stub
		return null;
	}
	public Line getOwnerAsLine() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isLineOwner() {
		if(ownerId instanceof LineId) {
			return true;
		}
		return false;
	}
	public boolean isNodeOwner() {
		if(ownerId instanceof NodeId) {
			return true;
		}
		return false;
	}
	private void setIntervalImpl(OffsetTime start, OffsetTime end) {
		this.interval = IntervalFactory.createInterval(start, end);
		if(this.interval.getLength().isZero()) {
		}
		
	}
	public void updateInOwner() {
		// TODO Auto-generated method stub
		
	}
	public void addToOwnerWithoutCheck() {
		// TODO Auto-generated method stub
		
	}
	public void divide(Number value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public TimeIntervalId getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
