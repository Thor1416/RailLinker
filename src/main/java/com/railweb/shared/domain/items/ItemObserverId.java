package com.railweb.shared.domain.items;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.util.Pair;

import lombok.Getter;

public class ItemObserverId<U extends DomainObjectId<?>, V extends DomainObjectId<?>> 
									extends DomainObjectId<Pair<U, V>> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6811968653546919970L;
	@Getter
	private final U itemOwnerId;
	@Getter
	private final V observerId;
	
	public ItemObserverId(U itemOwnerId, V observerId) {
		super(new Pair<>(itemOwnerId, observerId));
		this.itemOwnerId = itemOwnerId;
		this.observerId = observerId;
	}

	@Override
	protected void assignId() {
		// do nothing, the ID is assigned in the constructor
	}

	@Override
	public UUID toUUID() {
		// TODO: implement this method
		return null;
	}

	@Override
	public boolean sameValueAs(Pair<U, V> other) {
		return Objects.equals(this.getId(), other);
	}


}
