package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.network.Network;
import com.railweb.trafficmgt.domain.network.Node;

import lombok.Getter;

public class NodeRemovedEvent implements DomainEvent {

	/**
	 * @author Thorbj�rn Simonsen
	 */
	private static final long serialVersionUID = 1L;
	@Getter
	private final Node node;
	private final Instant occuredOn;
	@Getter
	private final Network network;
	
	public NodeRemovedEvent(Network network, Node node, Instant occuredOn) {
		this.node=node;
		this.occuredOn = occuredOn;
		this.network = network;
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