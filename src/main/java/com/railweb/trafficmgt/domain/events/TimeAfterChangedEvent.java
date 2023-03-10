package com.railweb.trafficmgt.domain.events;

import java.time.Duration;
import java.time.Instant;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.ids.TrainId;
import com.railweb.trafficmgt.domain.train.Train;

import lombok.Getter;

@Getter
public class TimeAfterChangedEvent implements DomainEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7610641029334787284L;
	private Train train;
	private Duration length;
	private Duration oldLength;
	private Instant occuredOn;
	
	public TimeAfterChangedEvent(TrainId trainId, Duration oldLength, Duration length, Instant occuredOn) {
		this.oldLength = length;
		this.length = length;
	}

	@Override
	public Instant occurredOn() {
		return occuredOn;
	}

}
