package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class LineId extends DomainObjectId<UUID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4257341905808978078L;

	public LineId(UUID id) {
		super(id);
	
	}
	public LineId(String id) {
		super(UUID.fromString(id));
	
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
