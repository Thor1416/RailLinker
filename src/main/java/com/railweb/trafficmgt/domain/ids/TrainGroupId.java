package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.DomainObjectId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Embeddable
public class TrainGroupId extends DomainObjectId<UUID> {
	/**
	* 
	*/
	private static final long serialVersionUID = 3389746002042583337L;

	public TrainGroupId(UUID uuid) {
		super(uuid);
	}
	public TrainGroupId(String uuid) {
		super(UUID.fromString(uuid));
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
