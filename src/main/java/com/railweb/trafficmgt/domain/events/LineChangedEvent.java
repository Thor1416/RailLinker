package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.ids.LineId;

public class LineChangedEvent implements DomainEvent {

	private LineId lineId;
	private Instant occuredOn;

	public LineChangedEvent(LineId id, Instant updatedOn) {
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
