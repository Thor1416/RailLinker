package com.railweb.trafficmgt.domain.events;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.ids.TrainId;

import lombok.Getter;

public abstract class TrainEvent implements DomainEvent {

	@Getter
	private TrainId trainId;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TrainEvent(TrainId source) {
		this.trainId = source;
	}

}
