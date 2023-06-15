package com.railweb.trafficmgt.domain.network;

import java.time.Instant;
import java.util.List;

import com.railweb.shared.domain.base.IdentifiableDomainObject;
import com.railweb.trafficmgt.domain.ids.NetSegmentId;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ids.TrackId;
import com.railweb.trafficmgt.domain.train.TimeInterval;
import com.railweb.trafficmgt.exception.network.TrackNotFoundException;

public interface NetSegment<ID extends NetSegmentId<?>,T extends Track<ID>> 
					extends Iterable<TimeInterval<ID>>, IdentifiableDomainObject<ID> {

	List<T> getTracks();

	boolean isEmpty();

	T getTrackById(TrackId id);

	void addTimeInterval(TimeInterval<ID> interval, Instant createdOn)
			throws TrackNotFoundException;
	
	void removeTimeInterval(TimeInterval<ID> interval, Instant removedOn);
	
	void updateTimeInterval(TimeInterval<ID> interval, Instant updatedOn);
}
