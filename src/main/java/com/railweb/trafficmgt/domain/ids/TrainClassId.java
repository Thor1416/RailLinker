package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.DomainObjectId;

@Embeddable
public class TrainClassId  extends DomainObjectId<UUID> {

	public TrainClassId(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}

	public TrainClassId(String id) {
		super(UUID.fromString(id));
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
