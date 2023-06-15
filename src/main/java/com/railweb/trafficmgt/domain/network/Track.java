package com.railweb.trafficmgt.domain.network;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import com.railweb.shared.converters.LengthConverter;
import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.NetSegmentId;
import com.railweb.trafficmgt.domain.ids.TimeIntervalListId;
import com.railweb.trafficmgt.domain.ids.TrackId;
import com.railweb.trafficmgt.domain.train.TimeInterval;
import com.railweb.trafficmgt.domain.train.TimeIntervalList;
import com.railweb.trafficmgt.domain.train.TimeIntervalResult;

import lombok.Data;

@Data
@MappedSuperclass
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Track<N extends NetSegmentId<?>> extends AbstractEntity<TrackId>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5475322187752864936L;

	protected String tracknumber;
	@ManyToOne
	protected NetSegment<N, ? extends Track<N>> owner;
	
	private final TimeIntervalList<N> intervalList;
	@Convert(converter=LengthConverter.class)
	private Quantity<Length> length;
	private LineGradient gradient;
	private List<TrackSection> trackSections;
	
	private Map<TrackSection, TimeIntervalListId> allocationMap;
	

	protected Track(NetSegment<N,? extends Track<N>> owner) {
		this.owner = owner;
		this.intervalList = new TimeIntervalList<>();
	}
	protected Track(NetSegment<N, ? extends Track<N>> owner, String trackNumber) {
		this(owner);
		this.tracknumber=trackNumber;
	}
	
	public boolean isFreeInterval(TimeInterval<N> interval){
		TimeIntervalResult<N> result = intervalList.testIntervalForRouteSegment(interval);
		return result.getStatus() == TimeIntervalResult.Status.OK;
	}
	public TimeIntervalResult<N> testTimeIntervalOI(TimeInterval<N> interval) {
		return (TimeIntervalResult<N>) intervalList.testIntervalForRouteSegmentOI(interval);
	}
	public void addTimeInterval(TimeInterval<N> interval) {
		this.intervalList.add(interval);
		
		
	}
	public void removeTimeInterval(TimeInterval interval) {
		// TODO Auto-generated method stub
		
	}
	
	public Iterator<TimeInterval<? extends NetSegmentId<?>>> iterator(){
		return intervalList.iterator();
	}
	
	private void reallocateTimeIntervals() {
		
	}
}
