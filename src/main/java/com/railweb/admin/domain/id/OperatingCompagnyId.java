package com.railweb.admin.domain.id;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class OperatingCompagnyId extends DomainObjectId<UUID> {

	public OperatingCompagnyId(String id) {
		super(UUID.fromString(id));
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void assignId() {
		this.id=UUID.randomUUID();
		
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
