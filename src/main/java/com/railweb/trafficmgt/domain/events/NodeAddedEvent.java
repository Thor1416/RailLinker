package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.network.Network;
import com.railweb.trafficmgt.domain.network.Node;

import lombok.Getter;

public class NodeAddedEvent implements DomainEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2470317868761560265L;
	@Getter
	private final Node node;
	@Getter
	private final Network owner;
	
	private final Instant occuredOn;

	public NodeAddedEvent(Network network, Node node, Instant occuredOn) {
		this.owner = network;
		this.node = node;
		this.occuredOn = occuredOn;
	}

	@Override
	public Instant occurredOn() {
		return occuredOn;
	}

}
