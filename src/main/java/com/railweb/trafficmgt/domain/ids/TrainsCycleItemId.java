package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.DomainObjectId;


@Embeddable
public class TrainsCycleItemId extends DomainObjectId<UUID> {

	protected TrainsCycleItemId(UUID id) {
		super(id);

	}

	@Override
	protected void assignId() {
		this.id = UUID.randomUUID(); 
		
	}

	@Override
	public boolean sameValueAs(DomainObjectId<UUID> other) {
		return this.id.equals(other.getId());
	}

	@Override
	public UUID toUUID() {
		return this.id;
	}


}
