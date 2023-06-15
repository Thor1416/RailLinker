package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.ids.TrainId;

public class TrainChangedEvent implements DomainEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3167769938752890943L;
	private final TrainId trainId;
	private final SpecialTrainTimeIntervalList list;
	private final Instant occurredOn; 

	public TrainChangedEvent(TrainId trainId, SpecialTrainTimeIntervalList list, Instant occurredOn) {
		this.trainId = trainId;
		this.list = list;
		this.occurredOn = occurredOn;
	}

	@Override
	public Instant occurredOn() {
		return occurredOn;
	}

	@Override
	public boolean isConsumed() {
		// TODO Auto-generated method stub
		return false;
	}

}
