package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class EngineGroupId extends DomainObjectId<UUID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2223994233869362488L;

	public EngineGroupId(UUID id) {
		super(id);
	}

	public EngineGroupId(String id) {
		super(UUID.fromString(id));
	}
	@Override
	public UUID toUUID() {
		return getId();
	}

	

	@Override
	public boolean sameValueAs(DomainObjectId<UUID> other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void assignId() {
		// TODO Auto-generated method stub
		
	}

}
