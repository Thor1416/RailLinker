package com.railweb.shared.domain.items;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;

public class ItemSetId<U extends DomainObjectId<?>,V extends DomainObjectId<?>> 
		extends DomainObjectId<ItemSetPair<U,V>> {

	
	
	public ItemSetId() { 
		super(ItemSetPair.class);
	}
	
	public ItemSetId(String value) {
		super(ItemSetPair.class, value);
	}
	
	public ItemSetId(ItemSetPair<U,V> id) {
		super(id);
	}
	
	public U getItemId() {
		return this.id.getFirst();
	}
	public V getOwnerId() {
		return this.id.getSecond();
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
	public boolean sameValueAs(DomainObjectId<ItemSetPair<U, V>> other) {
		return (this.id.getFirst() == other.getId().getFirst()) &&
				(this.id.getSecond() == other.getId().getSecond());
	}


}
