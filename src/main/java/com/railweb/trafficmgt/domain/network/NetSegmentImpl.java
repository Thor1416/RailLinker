package com.railweb.trafficmgt.domain.network;

import java.time.Instant;
import java.util.Iterator;

import com.google.common.collect.Iterators;
import com.railweb.shared.domain.base.AbstractAggregateRoot;
import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.items.ItemList;
import com.railweb.trafficmgt.domain.events.TimeIntervalEvent;
import com.railweb.trafficmgt.domain.train.TimeInterval;

abstract class NetSegmentImpl<ID extends DomainObjectId<?>, T extends Track>
						extends AbstractAggregateRoot<ID> 
						implements NetSegment<ID,T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9072327907090285310L;

	protected NetSegmentImpl(AbstractAggregateRoot<ID> source) {
		super(source);
	}

	protected ItemList<T> tracks;

	public Iterator<TimeInterval> iterator() {
		return Iterators.concat(Iterators.transform(tracks.iterator(), Track::iterator));
	}

	@Override
	public void addTimeInterval(TimeInterval interval, Instant createdOn) {
		interval.getTrack().addTimeInterval(interval);
		registerEvent(new TimeIntervalEvent<ID,T>(this,interval,TimeIntervalEvent.Type.ADDED, createdOn));
				
	}

	@Override
	public void removeTimeInterval(TimeInterval interval, Instant removedOn) {
		interval.getTrack().removeTimeInterval(interval);
		registerEvent(new TimeIntervalEvent<ID,T>(this,interval, TimeIntervalEvent.Type.REMOVED, removedOn));
	}

	@Override
	public void updateTimeInterval(TimeInterval interval, Instant updatedOn) {
		T track = this.getTrackForInterval(interval);
		if (track == null) {
			throw new IllegalStateException("Segment doesn't contain interval.");
		}
		track.removeTimeInterval(interval);
		interval.getTrack().addTimeInterval(interval);
		registerEvent(new TimeIntervalEvent<ID,T>(this,interval, TimeIntervalEvent.Type.CHANGED, updatedOn));

	}

	private T getTrackForInterval(TimeInterval interval) {
		for (T track : tracks) {
			if (track.getIntervalList().contains(interval))
				return track;
		}
		return null;
	}
}
