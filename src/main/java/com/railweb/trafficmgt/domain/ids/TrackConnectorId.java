package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class TrackConnectorId extends DomainObjectId<UUID>{

	protected TrackConnectorId(UUID id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public TrackConnectorId(String uuid) {
		super(UUID.fromString(uuid));
	}
	@Override
	protected void assignId() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean sameValueAs(DomainObjectId<UUID> other) { 
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UUID toUUID() {
		// TODO Auto-generated method stub
		return null;
	}

}
