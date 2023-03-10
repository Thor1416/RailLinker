package com.railweb.usermgt.model.ids;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.DomainObjectId;

@Embeddable
public class UserId extends DomainObjectId<UUID>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3026206043196383826L;

	public UserId(UUID id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public UserId() {
		super(UUID.randomUUID());
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void assignId() {
		this.id = UUID.randomUUID();
		
	}
	@Override
	public boolean sameValueAs(DomainObjectId<UUID> other) {
		return this.getId()==other.getId();
	}
	@Override
	public UUID toUUID() {
		return id;
	}



}
