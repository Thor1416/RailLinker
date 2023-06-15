package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class EngineClassId extends DomainObjectId<UUID>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3608119893858857861L;
	public EngineClassId(UUID id) {
		super(id);

	}
	public EngineClassId(String id) {
		super(UUID.fromString(id));
	}

	@Override
	protected void assignId() {
		this.id =UUID.randomUUID();
		
	}

	@Override
	public UUID toUUID() {
		return id;
	}
	@Override
	public boolean sameValueAs(DomainObjectId<UUID> other) {
		// TODO Auto-generated method stub
		return false;
	}


	
}
