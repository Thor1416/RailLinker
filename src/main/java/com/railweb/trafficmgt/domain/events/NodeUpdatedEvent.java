package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.network.Node;

public class NodeUpdatedEvent implements DomainEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Node node;
	private Instant occuredOn;
	
	public NodeUpdatedEvent(Node node, Instant occuredOn) {
		this.node= node;
		this.occuredOn = occuredOn;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@Override
	public Instant occurredOn() {
		return occuredOn;
	}

}
