package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.DomainObjectId;

@Embeddable
public class RundayId extends DomainObjectId<UUID> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 68740218498426563L;

	public RundayId(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}
	public RundayId(String uuid) {
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
