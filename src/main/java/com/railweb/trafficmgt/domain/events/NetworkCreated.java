package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.network.Network;

import lombok.Getter;

public class NetworkCreated implements DomainEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -343175172492275911L;
	@Getter
	private NetworkId id; 

	public NetworkCreated(Network network) {
		this.id = network.getId();
	}

	@Override
	public Instant occurredOn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConsumed() {
		// TODO Auto-generated method stub
		return false;
	}

}
