package com.railweb.trafficmgt.domain.network;

import java.time.Instant;
import java.util.List;

import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.base.IdentifiableDomainObject;
import com.railweb.trafficmgt.domain.ids.TrackId;
import com.railweb.trafficmgt.domain.train.TimeInterval;

public interface NetSegment<ID extends DomainObjectId<?>,T extends Track> 
					extends Iterable<TimeInterval>, IdentifiableDomainObject<ID> {

	void addTimeInterval(TimeInterval interval, Instant createdOn);

	void removeTimeInterval(TimeInterval interval, Instant removedOn);

	void updateTimeInterval(TimeInterval interval, Instant updatedOn);

	List<T> getTracks();

	boolean isEmpty();

	T getTrackById(TrackId id);
	
}
