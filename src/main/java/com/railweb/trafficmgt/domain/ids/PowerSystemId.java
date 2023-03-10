package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.DomainObjectId;


@Embeddable
public class PowerSystemId extends DomainObjectId<UUID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6543072313361285586L;

	public PowerSystemId(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}

	public PowerSystemId(String uuid) {
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
