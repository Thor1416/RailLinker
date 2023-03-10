package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class TrainRouteId extends DomainObjectId<UUID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected TrainRouteId(UUID id) {
		super(id);
	}
	public TrainRouteId(String uuid) {
		super(UUID.fromString(uuid));
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
