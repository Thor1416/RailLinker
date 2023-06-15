package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.railweb.shared.anno.ValueObject;
import com.railweb.shared.domain.base.DomainObjectId;

@ValueObject
@Embeddable
public class TimeIntervalListId extends DomainObjectId<UUID> {
	/**
	* 
	*/
	private static final long serialVersionUID = -1241905385234223900L;

	public TimeIntervalListId(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}

	public TimeIntervalListId(String uuid) {
		super(UUID.fromString(uuid));
	}
	
	public TimeIntervalListId() {
		super(UUID.randomUUID());
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
