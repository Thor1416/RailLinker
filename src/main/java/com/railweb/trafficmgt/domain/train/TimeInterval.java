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
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import com.railweb.shared.converters.SpeedConverter;
import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.trafficmgt.domain.ManagedFreightOption;
import com.railweb.trafficmgt.domain.TimeIntervalDirection;
import com.railweb.trafficmgt.domain.TimeIntervalList;
import com.railweb.trafficmgt.domain.TrackConnector;
import com.railweb.trafficmgt.domain.ids.TimeIntervalId;
import com.railweb.trafficmgt.domain.network.Line;
import com.railweb.trafficmgt.domain.network.LineTrack;
import com.railweb.trafficmgt.domain.network.NetSegment;
import com.railweb.trafficmgt.domain.network.Node;
import com.railweb.trafficmgt.domain.network.NodeTrack;
import com.railweb.trafficmgt.domain.network.Station;
import com.railweb.trafficmgt.domain.network.Track;
import com.railweb.trafficmgt.domain.train.Train.NameType;
import com.railweb.trafficontrol.model.Block;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Entity
public class TimeInterval extends AbstractEntity<TimeIntervalId> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4924257378896632472L;
	@EmbeddedId
	private TimeIntervalId id; 
	@Embedded
	private Interval interval;
	@ManyToOne
	private Train train;
	
	@Any(metaColumn=@Column(name="ownerType"))
	@AnyMetaDef(idType="long", metaType="string",
				metaValues= {
						@MetaValue(targetEntity=Node.class, value="N"),
						@MetaValue(targetEntity=Line.class, value="L"),
						@MetaValue(targetEntity=Block.class, value="B"),
						@MetaValue(targetEntity=Station.class, value="S")
				})
	@JoinColumn(name = "trackID")
	private NetSegment<? extends DomainObjectId<?>,? extends Track> owner;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Track track;
	
	@Convert(converter = SpeedConverter.class)
	private Quantity<Speed> speedLimit;
	
	private Duration addedTime;
	
	@JoinTable(name = "overlaps",
			joinColumns = @JoinColumn(name="interval1", referencedColumnName="timeIntervalID"),
			inverseJoinColumns = @JoinColumn(name="interval2",referencedColumnName="timeIntervalId")
	)
	@ManyToMany
	private Set<TimeInterval> overlappingIntervals;
	
	@Enumerated(EnumType.ORDINAL)
	private TimeIntervalDirection direction;
	
	@Convert(converter = SpeedConverter.class)
	private Quantity<Speed> usedSpeed;
		
	private Boolean changed;
	
	@Basic(fetch=FetchType.LAZY)
	@ElementCollection
	private Set<String> comments;
	
	private Boolean ignoreLength;
	private Boolean showComments;
	
	private Boolean shunt;
	@Enumerated
	private ManagedFreightOption freightOption;
	
	@Transient
	private TimeIntervalCalculation calculation;
	
	  /**
     * creates instance of an time interval.
     *
     * @param train train
     * @param owner owner (node track, node, ...)
     * @param start start time
     * @param end end time
     * @param speed speed for line time interval
     * @param direction direction of the line time interval
     * @param track track
     * @param addedTime added time
     */
	public TimeInterval(Train train, NetSegment<? extends DomainObjectId<?>, ? extends Track> owner,
			OffsetTime start, OffsetTime end, Quantity<Speed> speed,
			TimeIntervalDirection direction, Track track, Duration addedTime) {
		
		this.setTrain(train);
		this.setOwner(owner);
		this.interval = IntervalFactory.createInterval(start,end);
		this.direction = direction;
		this.track = track;
		this.addedTime = addedTime;
		this.speedLimit = speed;
	}
	public TimeInterval(Train train, NetSegment<? extends DomainObjectId<?>,? extends Track> owner,
			OffsetTime start, OffsetTime end, Track track) {
		this(train,owner,start, end, null, null, track, Duration.ZERO);
	}
	
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
	public int compareOpenNormalized(TimeInterval o) {
		return this.getInterval().compareOpenNormalized(o.getInterval());
	}
	 /**
     * compares intervals for trains. Closed/bounded interval. It uses
     * normalized intervals.
     *
     * @param o interval
     * @return comparison
     */
    public int compareClosedNormalized(TimeInterval o) {
        return this.getInterval().compareClosedNormalized(o.getInterval());
    }
    
    public String toString() {
    	String ownerStr;
    	if(getOwner() != null) {
    		if(isNodeOwner()) {
    			ownerStr = getOwner().toString();
    		}
    		else if(isLineOwner()) {
    			ownerStr = getOwnerAsLine().toString(getDirection());
    		}else if(isBlockOwner()) {
    			ownerStr = getOwnerAsBlock().toString(getDirection());
    		}else {
    			ownerStr = "-";
    		}
    	}else {
    		ownerStr="-";
    	}
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
		return getTrack() == null ? "-": getTrack().getTracknumber();
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
	public void setTrack(Track track) {
		if(track != this.track) {
			this.track = track;
			this.setChanged(true);
		}
	}
	
	public Set<TimeInterval> getOverlappingInterval(){
		if(overlappingIntervals==null) {
			overlappingIntervals = new HashSet<>();
		}
		return overlappingIntervals;
	}
	
	

	public boolean isOverlapping() {
		return (overlappingIntervals != null) && overlappingIntervals.isEmpty();
	}
	
	 /**
     * @return <code>false</code> if there is no platform for train that needs one (in any other case it returns <code>true</code>)
     */
	public boolean isPlatformOK() {
		if(this.isStop()){
			return !(train.getTrainClass().isPlatform() && !((NodeTrack)track).isPlatform());
		}
		return true;
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
	
	/**
     * @return from node for interval that belongs to line otherwise <code>null</code>
     */
	public Node getFrom() {
		return (owner instanceof Line) ? ((Line) owner).getFrom(direction): null;
	}
	/**
     * @return to node for interval that belongs to line otherwise <code>null</code>
     */
	public Node getTo() {
		return (owner instanceof Line) ? ((Line) owner).getFrom(direction): null;
	}
	
	/**
     * @return if the current interval is straight from previous one
     */
	public boolean isFromStraight() {
		if(isNodeOwner()) {
			return this.getFromTrackConnector()
					.map(c->c.getStraightNodeTrack().orElse(null) == getTrack())
					.orElse(false);
		}else {
			return this.getPreviousTrainInterval().isToStraight();
		}
	}
	public boolean isToStraight() {
		if(isNodeOwner()) {
			return this.getToTrackConnector().map(c->c.getStraightNodeTrack().orElse(null)==getTrack()).orElse(false);
		}else {}
		return this.getNextTrainInterval().isFromStraight();
	}
	
	public Optional<TrackConnector> getFromTrackConnector(){
		TimeInterval prevInterval = this.getPreviousTrainInterval();
		if(isNodeOwner()){
			Node node = getOwnerAsNode();
			return prevInterval == null ? Optional.empty() : 
				node.getTrackConnectors().getForLineTrack((LineTrack) prevInterval.getTrack());
		}else {
			return prevInterval.getToTrackConnector();
		}
	}
	
	public TimeInterval getTrainInterval(int relativeIndex) {
		return train.getInterval(this,relativeIndex);
	}
	
	public TimeInterval getPreviousTrainInterval() {
		return this.getTrainInterval(-1);
	}

	 /**
     * @return connector used for departure from the route segment
     */
	public Optional<TrackConnector> getToTrackConnector(){
		TimeInterval nextInterval = this.getNextTrainInterval();
		if(isNodeOwner()) {
			Node node = getOwnerAsNode();
			return nextInterval == null ? Optional.empty() :
				node.getTrackConnectors().getForLineTrack((LineTrack) nextInterval.getTrack());
		}else {
			return nextInterval.getFromTrackConnector();
		}
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
		owner.removeTimeInterval(this, time);
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
	public void setList(TimeIntervalList timeIntervalList) {
		// TODO Auto-generated method stub
		
	}

	public boolean isOwner() {
		// TODO Auto-generated method stub
		return false;
	}

	public Node getOwnerAsNode() {
		return isOwner() ? (Node) owner : null;
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
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isNodeOwner() {
		// TODO Auto-generated method stub
		return false;
	}
	private void setIntervalImpl(OffsetTime start, OffsetTime end) {
		this.interval = IntervalFactory.createInterval(start, end);
		if(this.interval.getLength().isZero()) {
			this.freightOption=null;
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
