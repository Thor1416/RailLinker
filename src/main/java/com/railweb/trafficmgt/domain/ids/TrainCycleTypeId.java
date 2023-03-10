package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.DomainObjectId;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
public class TrainCycleTypeId extends DomainObjectId<UUID> {

	protected TrainCycleTypeId(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}

	public TrainCycleTypeId(String uuid) {
		super(UUID.fromString(uuid));
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -498580642977135109L;

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
