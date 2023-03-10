package com.railweb.admin.domain.id;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class InfrastructureManagerId extends DomainObjectId<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InfrastructureManagerId(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public InfrastructureManagerId(String id) {
		super(Long.parseLong(id));
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void assignId() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean sameValueAs(DomainObjectId<Long> other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UUID toUUID() {
		// TODO Auto-generated method stub
		return null;
	}

}
