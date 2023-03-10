package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.trafficmgt.domain.ids.TrainId;
import com.railweb.trafficmgt.domain.train.Train.NameType;

import lombok.Getter;


public class TrainNameChangedEvent extends TrainEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3252261859007730797L;
	@Getter
	private final String oldName;
	@Getter
	private final String newName;
	@Getter
	private final NameType nameType;
	
	public TrainNameChangedEvent(TrainId trainId,String oldName, String newName,
			NameType nameType, Instant instant) {
		super(trainId);
		this.oldName = oldName;
		this.newName = newName;
		this.nameType = nameType;
	}


	@Override
	public Instant occurredOn() {
		// TODO Auto-generated method stub
		return null;
	}

}
