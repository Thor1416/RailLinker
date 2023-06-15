package com.railweb.admin.domain.id;

import java.util.UUID;

import javax.persistence.Embeddable;

import org.springframework.lang.NonNull;

import com.railweb.shared.domain.base.DomainObjectId;

@Embeddable
public class RegionId extends DomainObjectId<UUID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2144041684069446302L;

	public RegionId(String uuid) {
		super(UUID.fromString(uuid));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void assignId() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public UUID toUUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sameValueAs(DomainObjectId<UUID> other) {
		// TODO Auto-generated method stub
		return false;
	}

}
