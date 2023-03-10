package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.DomainObjectId;

import lombok.Data;

@Embeddable
@Data
public class TrackId extends DomainObjectId<UUID>{



	/**
	 * 
	 */
	private static final long serialVersionUID = -6413370340061924728L;

	public TrackId(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}

	public TrackId(String uuid) {
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
