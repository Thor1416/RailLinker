package com.railweb.shared.domain.items;

import java.io.Serializable;

import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.base.IdentifiableDomainObject;

public interface ItemSetObserver<T extends IdentifiableDomainObject<ID>,
									ID extends DomainObjectId<?>,
									OID extends ObserverId<?,?,?>>
								extends IdentifiableDomainObject<ID>{

	OID getObserverId();
	
	void notify(ItemSetObservable<T,ID, OID> observable, T item);
}
