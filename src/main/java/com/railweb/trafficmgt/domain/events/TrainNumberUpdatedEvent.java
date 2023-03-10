package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.TrainNumber;
import com.railweb.trafficmgt.domain.ids.TrainId;

public class TrainNumberUpdatedEvent implements DomainEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8450804659504461042L;
	private final TrainNumber oldNumber;
	private final TrainNumber newNumber;
	private final TrainId trainId;
	

	public TrainNumberUpdatedEvent(TrainId trainId, TrainNumber oldNumber, TrainNumber number) {
		this.newNumber = number;
		this.oldNumber = oldNumber;
		this.trainId = trainId;
	}


	@Override
	public Instant occurredOn() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
