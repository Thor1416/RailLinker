package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class ImageId extends DomainObjectId<UUID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -727516946333563869L;

	public ImageId(UUID id) {
		super(id);
	}
	public ImageId(String id) {
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
		// TODO Auto-generated method stub
		return false;
	}
	


}
