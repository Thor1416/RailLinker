package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class TrainTypeId extends DomainObjectId<UUID>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2015556073496928626L;

	protected TrainTypeId(UUID id) {
		super(id);

	}

	@Override
	protected void assignId() {
		this.id = UUID.randomUUID();
	}

	@Override
	public boolean sameValueAs(DomainObjectId<UUID> other) {
		return this.id.equals(other.getId());
	}

	@Override
	public UUID toUUID() {
		return id;
	}

}
