package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class LinePathId extends DomainObjectId<UUID>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 182822431199510515L;

	public LinePathId(UUID id) {
		super(id);
	}
	public LinePathId(String id) {
		super(UUID.fromString(id));
	}

	@Override
	protected void assignId() {
		this.id = UUID.randomUUID();
		
	}

	@Override
	public UUID toUUID() {
		return this.id;
	}
	@Override
	public boolean sameValueAs(DomainObjectId<UUID> other) {
		return this.id.equals(other.getId());
	}



}
