package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.DomainObjectId;

@Embeddable
public class PenaltyTableRowId extends DomainObjectId<UUID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1606008712833497348L;

	public PenaltyTableRowId(UUID uuid) {
		super(uuid);
	}

	public PenaltyTableRowId(String uuid) {
		super(UUID.fromString(uuid));
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void assignId() {
		this.id= UUID.randomUUID();
		
	}

	@Override
	public UUID toUUID() {
		return this.id;
	}

	@Override
	public boolean sameValueAs(DomainObjectId<UUID> other) {
		// TODO Auto-generated method stub
		return false;
	}

}
