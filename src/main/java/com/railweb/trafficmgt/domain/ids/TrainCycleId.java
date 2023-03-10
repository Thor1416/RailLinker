package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

import lombok.EqualsAndHashCode;
@EqualsAndHashCode
public class TrainCycleId extends DomainObjectId<UUID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3982096769758586791L;

	protected TrainCycleId(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}

	public TrainCycleId(String uuid) {
		super(UUID.fromString(uuid));
	}
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
