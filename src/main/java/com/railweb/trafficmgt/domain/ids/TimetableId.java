package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.railweb.shared.anno.ValueObject;
import com.railweb.shared.domain.base.DomainObjectId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValueObject
@Embeddable
@Data
@EqualsAndHashCode(callSuper=true)
public class TimetableId extends DomainObjectId<UUID>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7623301625329977199L;

	public TimetableId() {
		super(UUID.randomUUID());
		// TODO Auto-generated constructor stub
	}
	
	public TimetableId(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}

	public TimetableId(String uuid) {
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
