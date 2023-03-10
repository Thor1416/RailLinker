package com.railweb.shared.domain.i18n;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

import lombok.Data;

@Data
public class LocalizedStringId extends DomainObjectId<UUID> {

	public LocalizedStringId(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void assignId() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean sameValueAs(DomainObjectId<UUID> other) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public UUID toUUID() {
		// TODO Auto-generated method stub
		return null;
	}

}
