package com.railweb.trafficmgt.domain.events;

import java.time.Duration;
import java.time.Instant;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.ids.TrainId;

import lombok.Getter;

@Getter
public class TimeBeforeChangedEvent implements DomainEvent {

	private TrainId trainId;
	private Duration length;
	private Duration oldLength;
	private Instant occuredOn;
	
	public TimeBeforeChangedEvent(TrainId trainId, Duration oldLength, Duration length, Instant occuredOn) {
		this.trainId=trainId;
		this.oldLength = length;
		this.length = length;
		this.occuredOn = occuredOn;
	}

	@Override
	public Instant occurredOn() {
		return occuredOn; 
	}

	@Override
	public boolean isConsumed() {
		// TODO Auto-generated method stub
		return false;
	}

}
