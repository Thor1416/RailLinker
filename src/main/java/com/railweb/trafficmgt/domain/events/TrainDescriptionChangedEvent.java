package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.trafficmgt.domain.ids.TrainId;

public class TrainDescriptionChangedEvent extends TrainEvent {


	private String oldDesc;
	private String newDesc;
	public TrainDescriptionChangedEvent(TrainId source, String oldDesc, String newDesc) {
		super(source);
		this.oldDesc = oldDesc;
		this.newDesc = newDesc;
	}
	@Override
	public Instant occurredOn() {
		// TODO Auto-generated method stub
		return null;
	}
}
