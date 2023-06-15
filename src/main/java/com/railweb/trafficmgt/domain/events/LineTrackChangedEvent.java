package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.ids.TrainId;

public class LineTrackChangedEvent implements DomainEvent {

	public LineTrackChangedEvent(TrainId id, SpecialTrainTimeIntervalList sttil, Instant clock) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Instant occurredOn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConsumed() {
		// TODO Auto-generated method stub
		return false;
	}

}
