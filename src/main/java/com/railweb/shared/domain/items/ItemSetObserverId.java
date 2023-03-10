package com.railweb.shared.domain.items;

import java.io.Serializable;
import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.events.DomainEvent;

import lombok.Getter;

public class ItemSetObserverId<	U extends DomainObjectId<?>,
								V extends DomainObjectId<?>,
								ID extends DomainObjectId<?>,
								E extends DomainEvent,
								T extends ItemSetObserverEntity<ID,?,?,?,E,?>>
					extends ObserverId<ID, U, DomainEvent> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2931663844548880706L;
	@Getter
	private U itemSetOwnerId;
	@Getter
	private V itemObserverId;

	public ItemSetObserverId(ID itemId, U observer, Class<DomainEvent> eventType) {
		super(itemId, observer, eventType);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void assignId() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean sameValueAs(ObserverId<?, ?, ?> other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UUID toUUID() {
		// TODO Auto-generated method stub
		return null;
	}
}
