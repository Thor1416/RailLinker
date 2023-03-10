package com.railweb.shared.domain.items;

import com.google.common.collect.Iterables;
import com.railweb.shared.domain.base.IdentifiableDomainObject;

public interface ItemIterable<T extends IdentifiableDomainObject> extends Iterable<T> {
	
	default T getById(Long id) {
		return Iterables.tryFind(this, item->item.id().equals(id)).orNull();
		
	} 
}
