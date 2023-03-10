package com.railweb.shared.domain.i18n;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class StringWithLocaleId extends DomainObjectId<Long> {

	protected StringWithLocaleId(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void assignId() {
		this.id=1L;
		
	}

	@Override
	public boolean sameValueAs(DomainObjectId<Long> other) {
		return this.id == other.getId();
	}

	@Override
	public UUID toUUID() {
		return UUID.fromString(id.toString());
	}

}
