package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class NetworkId extends DomainObjectId<UUID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3382170597687130563L;

	public NetworkId(UUID id) {
		super(id);
	}
	public NetworkId(String id) {
		super(UUID.fromString(id));
	}
	@Override
	protected void assignId() {
		this.id = UUID.randomUUID();
	}


	@Override
	public UUID toUUID() {
		return id;
	}
	@Override
	public boolean sameValueAs(DomainObjectId<UUID> other) {
		return this.getId().equals(other.getId());
	}

}
