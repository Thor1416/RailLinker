package com.railweb.trafficmgt.domain.network;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.railweb.shared.converters.LengthConverter;
import com.railweb.shared.converters.SpeedConverter;
import com.railweb.shared.domain.base.AbstractAggregateRoot;
import com.railweb.shared.domain.base.AbstractDomainEntity;
import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.LineId;
import com.railweb.trafficmgt.domain.ids.TimeIntervalId;
import com.railweb.trafficmgt.domain.ids.TrackId;
import com.railweb.trafficmgt.domain.train.TimeInterval;
import com.railweb.trafficmgt.domain.train.TimeIntervalDirection;
import com.railweb.trafficmgt.exception.network.TrackNotFoundException;
import com.railweb.trafficmgt.infra.persistence.network.LineEntity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Slf4j
public class Line extends NetSegmentImpl<LineId,LineTrack>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5370085949456248731L;
	private Boolean bidirectional;
	@OneToMany
	private Set<LinePath> linePaths;
	@OneToOne
	private LineClass lineClass;
	@Convert(converter=SpeedConverter.class)
	private Quantity<Speed> speed;
	@Convert(converter=LengthConverter.class)
	private Quantity<Length> length;
	@Enumerated(EnumType.STRING)
	private LineGradient gradient;
	@ManyToOne
	private Network net;
	
	@OneToMany
	@JoinTable(name = "line_timeinterval_linepath",
				joinColumns = @JoinColumn(name="line_id", referencedColumnName="id"),
				inverseJoinColumns = @JoinColumn(name="linepath_id",referencedColumnName="id"))
	@MapKey(name="timeInterval_id")
	private Map<TimeIntervalId,LinePath> timeIntervals;
	
	@Column(name="created_by")
	@CreatedBy
	private String createdBy;
	
	@Column(name="modified_by")
	private String modifiedBy;

	@Version
	private Long version;
	
	public Boolean isBiDirectional(){return bidirectional;}

	public void setTopSpeed(Quantity<Speed> topSpeed) {
		if(topSpeed != null && topSpeed.getValue().doubleValue()<=0) {
			throw new IllegalArgumentException("Top speed should be a positive number");
		}
		this.speed = topSpeed;
	}
	
	public String toString() {
		return this.toString(TimeIntervalDirection.FORWARD);
	}
	
	public String toString(TimeIntervalDirection direction) {
		return String.format("%s-%s", this.getFrom(direction).getAbbr(),this.getTo(direction).getAbbr());
	}
	
	public Node getFrom() {
		return this.getNet().getFrom(this.linePaths.stream().findFirst().get());
	}
	
	public Node getTo() {
		return this.getNet().getTo(this.linePaths.stream().findFirst().get());
	}
	
	public Node getTo(TimeIntervalDirection direction) {
		return (direction == TimeIntervalDirection.FORWARD) ? getFrom() : getTo();
	}
	
	public Node getFrom(TimeIntervalDirection direction) {
		return (direction == TimeIntervalDirection.FORWARD) ? getTo() : getFrom();
	}

	@Override
	public List<LineTrack> getTracks() {
		List<LineTrack> linetracks = new ArrayList<LineTrack>();
		for(LinePath lp: linePaths) {
			if(!linetracks.contains(lp.getTrack())) {
				linetracks.add(lp.getTrack());
			}
		}
		return linetracks;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LineTrack getTrackById(TrackId id) {
		for(LinePath lp:  linePaths) {
			if(lp.getTrack().getId() == id) {
				return lp.getTrack();
			}
		}
		return null;
	}

	@Override
	public LineId getId() {
		return id;
	}

	public void addLinePath(LinePath linePath) {
		this.linePaths.add(linePath);
	}

	public Line(AbstractAggregateRoot<LineId> source, Boolean bidirectional, Set<LinePath> linePaths, LineClass lineClass, Quantity<Speed> speed,
			Quantity<Length> length, LineGradient gradient, Network net, String createdBy, String modifiedBy) {
		super(source);
		this.bidirectional = bidirectional;
		this.linePaths = linePaths;
		this.lineClass = lineClass;
		this.speed = speed;
		this.length = length;
		this.gradient = gradient;
		this.net = net;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}

	@Override
	protected AbstractAggregateRoot<LineId>.AggregateRootBehavior<?> initialBehavior() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTimeInterval(TimeInterval<LineId> interval, Instant createdOn) throws TrackNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTimeInterval(TimeInterval<LineId> interval, Instant removedOn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTimeInterval(TimeInterval<LineId> interval, Instant updatedOn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Line fromJpaEntity(AbstractEntity<LineId> jpaEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LineEntity toJpaEntity(AbstractDomainEntity<LineId> domainEntity) {
		// TODO Auto-generated method stub
		return null;
	}


}