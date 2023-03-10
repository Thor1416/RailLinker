package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.ids.LineId;
import com.railweb.trafficmgt.domain.ids.NetworkId;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LineRemovedEvent implements DomainEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final LineId lineId;
	private final Instant occuredOn;
	private final NetworkId network;

	public LineId getLineId() {
		return lineId;
	}

	@Override
	public Instant occurredOn() {
		return occuredOn;
	}



}
