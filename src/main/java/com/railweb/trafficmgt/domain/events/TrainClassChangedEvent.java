package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.trafficmgt.domain.ids.TrainId;
import com.railweb.trafficmgt.domain.train.TrainClass;

public class TrainClassChangedEvent extends TrainEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -91718502904020404L;

	public TrainClassChangedEvent(TrainId trainId, TrainClass oldTrainClass, TrainClass trainClass) {
		super(trainId);
	}

	@Override
	public Instant occurredOn() {
		// TODO Auto-generated method stub
		return null;
	}

}
