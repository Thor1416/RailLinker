package com.railweb.shared.domain.items;

import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.base.IdentifiableDomainObject;
import com.railweb.shared.domain.events.DomainEvent;

public interface ItemObserver<T extends IdentifiableDomainObject<ID>, 
								ID extends DomainObjectId<?>,
								U extends DomainObjectId<?>,
								V extends DomainObjectId<?>,
								E extends DomainEvent>
 								extends IdentifiableDomainObject<ID> {

	
	ID getObserverId();
	void notifyObservers(T item, DomainEvent event);
}
