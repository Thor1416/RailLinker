package com.railweb.trafficmgt.domain.network;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.measure.Quantity;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Length;
import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import com.railweb.shared.converters.LengthConverter;
import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.trafficmgt.domain.TimeIntervalList;
import com.railweb.trafficmgt.domain.TimeIntervalResult;
import com.railweb.trafficmgt.domain.ids.TrackId;
import com.railweb.trafficmgt.domain.train.TimeInterval;
import com.railweb.trafficontrol.model.Block;

import lombok.Data;

@Data
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Track extends AbstractEntity<TrackId>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5475322187752864936L;

	protected String tracknumber;
	
	protected NetSegment<? extends DomainObjectId<?>, ? extends Track> owner;
	
	private final TimeIntervalList intervalList;
	@Convert(converter=LengthConverter.class)
	private Quantity<Length> length;
	
	@Enumerated(EnumType.STRING)
	private LineGradient gradient;
	
	private Set<Block> blocks;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="track_timeInterval_mapping",
		joinColumns=@JoinColumn(name="track_id",referencedColumnName="track_id"),
		inverseJoinColumns=@JoinColumn(name="list"))
	@MapKeyColumn(name="block_id")
	private Map<Block, TimeIntervalList> blockIntervalMap;
	

	protected Track(NetSegment<? extends DomainObjectId<?>,? extends Track> owner) {
		this.owner = owner;
		this.intervalList = new TimeIntervalList();
	}
	protected Track(NetSegment<? extends DomainObjectId<?>, ? extends Track> owner, String trackNumber) {
		this(owner);
		this.tracknumber=trackNumber;
	}
	public Iterator<TimeInterval> iterator() {
        return intervalList.iterator();
    }
	public boolean isEmpty() {
		return intervalList.isEmpty();
	}
	public boolean isFreeInterval(TimeInterval interval){
		TimeIntervalResult result = intervalList.testIntervalForRouteSegment(interval);
		return result.getStatus() == TimeIntervalResult.Status.OK;
	}
	public TimeIntervalResult testTimeIntervalOI(TimeInterval interval) {
		return intervalList.testIntervalForRouteSegmentOI(interval);
	}
	public void addTimeInterval(TimeInterval interval) {
		this.intervalList.add(interval);
		for(Block block: blocks) {
			Quantity<Dimensionless> fraction = (Quantity<Dimensionless>) block.getLength().divide(length);
			interval.divide(fraction.getValue());
		}
		
	}
	public void removeTimeInterval(TimeInterval interval) {
		// TODO Auto-generated method stub
		
	}
	public boolean hasBlocks() {
		return blocks.isEmpty();
	}
}
