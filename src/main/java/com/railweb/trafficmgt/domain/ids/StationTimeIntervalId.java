package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class StationTimeIntervalId extends DomainObjectId<UUID> {

	protected StationTimeIntervalId(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6350928903502539098L;
	
	public StationTimeIntervalId(String uuid) {
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
