package com.railweb.shared.domain.items;

import com.railweb.shared.domain.base.IdentifiableDomainObject;

public interface ItemWithIdSet<T extends IdentifiableDomainObject<?>, ID > 
	extends ItemSet<T>, ItemIterable<T> {

}
