package com.railweb.usermgt.model.ids;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;


public class RoleId extends DomainObjectId<Long>{

	protected RoleId(Long id) {
		super(id);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3544018564854664310L;

	
	@Override
	protected void assignId() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean sameValueAs(DomainObjectId<Long> other) {
		return this.id == other.getId();
	}

	@Override
	public UUID toUUID() {
		// TODO Auto-generated method stub
		return null;
	}

}
