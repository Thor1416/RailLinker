package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ids.TrainId;

import lombok.Data;

@Data
public class NodeChangedEvent implements DomainEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final NodeId node;
	private final TrainId train;
	private final SpecialTrainTimeIntervalList list;
	
	public NodeChangedEvent(NodeId node, TrainId trainId, SpecialTrainTimeIntervalList list, Instant clock) {
		this.node = node;
		this.train = trainId;
		this.list = list;
	}

	@Override
	public Instant occurredOn() {
		// TODO Auto-generated method stub
		return null;
	}

}
