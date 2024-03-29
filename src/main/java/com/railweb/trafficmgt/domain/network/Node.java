package com.railweb.trafficmgt.domain.network;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Version;

import org.hibernate.envers.Audited;
import org.springframework.context.ApplicationContext;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.geo.Point;

import com.google.common.collect.Iterators;
import com.railweb.admin.domain.id.InfrastructureManagerId;
import com.railweb.shared.converters.LengthConverter;
import com.railweb.shared.domain.base.AbstractAggregateRoot;
import com.railweb.shared.domain.items.ItemList;
import com.railweb.shared.domain.util.Visitable;
import com.railweb.trafficmgt.domain.Location;
import com.railweb.trafficmgt.domain.TimeIntervalResult;
import com.railweb.trafficmgt.domain.TimetableVisitor;
import com.railweb.trafficmgt.domain.TrackConnectors;
import com.railweb.trafficmgt.domain.events.TimeIntervalEvent;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ids.TrackId;
import com.railweb.trafficmgt.domain.train.TimeInterval;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * Node that can consist of several tracks. Each tracks provides its own list
 * of time intervals.
 *
 * @author Thorbj�rn Simonsen
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Audited
public class Node extends AbstractAggregateRoot<NodeId> implements NetSegment<NodeId,NodeTrack>, Visitable<TimetableVisitor> {

	@EmbeddedId
	@Setter(AccessLevel.NONE)
	private NodeId id;
	@Version
	private Long version;
	
	private Set<NetworkId> nets;
	private String name;
	private InfrastructureManagerId owner;
	
	@OneToMany
	@MapKey(name="net_id")
	private Map<NetworkId, Location> locations;
	private Double longitude;
	private Double lattitude;
	private Point  GPSLocation;
	
	@OneToMany
	@OrderColumn(name="track_order")
	private ItemList<NodeTrack> tracks;
	
	@ManyToOne
	@LastModifiedBy
	private String lastEditedBy;
	
	@LastModifiedDate
	@Column(columnDefinition="TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime lastEdit;
	
	@ManyToOne
	@CreatedBy
	private String createdBy;
	private ZoneId defaultTimezone;
	@Convert(converter=LengthConverter.class)
	private Quantity<Length> length; 
	
	public enum Side{
		LEFT{
			@Override
			public Side opposite() {
				return RIGHT;
			}
		},
		RIGHT{
			@Override
			public Side opposite() {
				return LEFT;
			}
		};
		
		public abstract Side opposite();
	}

	private final TrackConnectors trackConnectors;
 
	
	@Override
	public void accept(TimetableVisitor visitor) {
		visitor.visit(this);
		
	}
	
	public String toString() {
		return getName();
	} 
	
	public Set<TimeInterval> getOverlappingTimeIntervals(TimeInterval interval){
		Set<TimeInterval> out = null;
		for(NodeTrack track : tracks) {
			TimeIntervalResult result=track.testTimeIntervalOI(interval);
			if(result.getStatus() == TimeIntervalResult.Status.OVERLAPPING) {
				if(out==null) {
					out = new HashSet<>(result.getOverlappingIntervals());
				}
			}else {
				out.addAll(result.getOverlappingIntervals());
			}
		}
		if(out==null) {
			return Collections.emptySet();
		}else {
			return out;
		}
	}

	@Override
	public void addTimeInterval(TimeInterval interval, Instant createdOn) {
		interval.getTrack().addTimeInterval(interval); 
		registerEvent(new TimeIntervalEvent<NodeId,NodeTrack>(this,interval,TimeIntervalEvent.Type.ADDED, createdOn));
	}

	@Override
	public void removeTimeInterval(TimeInterval interval, Instant removedOn) {
		interval.getTrack().removeTimeInterval(interval);
		registerEvent(new TimeIntervalEvent<NodeId,NodeTrack>(this,interval, TimeIntervalEvent.Type.REMOVED, removedOn));
	}

	@Override
	public void updateTimeInterval(TimeInterval interval, Instant updatedOn) {
		NodeTrack track = this.getTrackForInterval(interval);
		if(track == null) {
			 throw new IllegalStateException("Node doesn't contain interval.");
		}
		track.removeTimeInterval(interval);
		interval.getTrack().addTimeInterval(interval);
		registerEvent(new TimeIntervalEvent<NodeId,NodeTrack>(this,interval,TimeIntervalEvent.Type.CHANGED, updatedOn));
	}

	@Override
	public boolean isEmpty() {
		for(NodeTrack track : tracks) {
			if(!track.isEmpty()) return false;
		}
		return true;
	}

	@Override
	public NodeTrack getTrackById(TrackId id) {
		for(NodeTrack track: tracks) {
			if(track.getId().equals(id)) {
				return track;
			}
		}
		return null;
	}

	@Override
	public Iterator<TimeInterval> iterator() {
		return Iterators.concat(Iterators.transform(tracks.iterator(), Track::iterator));
	}

	@Override
	public NodeId getId() {
		return id;
	}

	public NodeAbbr getAbbr(){
		return id.getAbbr();
	}
	
	private NodeTrack getTrackForInterval(TimeInterval interval) {
		for(NodeTrack track: tracks) {
			if(track.getIntervalList().contains(interval)) {
				return track;
			}
		}
		return null;
	}

	public Quantity<Speed> getSpeed() {
		// TODO Auto-generated method stub
		return null;
	}

	public Quantity<Speed> getNotStraightSpeed() {
		// TODO Auto-generated method stub
		return null;
	}

	public Node(AbstractAggregateRoot<NodeId> source) {
		super(source);
		this.trackConnectors = new TrackConnectors();
	}

	public Node(ApplicationContext applicationContext, NodeId entityId) {
		super(applicationContext, entityId);
		this.trackConnectors = new TrackConnectors();
	}

	@Override
	protected AbstractAggregateRoot<NodeId>.AggregateRootBehavior<?> initialBehavior() {
		// TODO Auto-generated method stub
		return null;
	}

}
