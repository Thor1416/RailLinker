package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.DomainObjectId;
@Embeddable
public class PlatformId extends DomainObjectId<UUID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7720778828608548258L;

	public PlatformId(UUID uuid) {
		super(uuid);
	}
	public PlatformId(String uuid) {
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
		return this.id;
	}

}
