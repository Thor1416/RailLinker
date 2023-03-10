package com.railweb.shared.domain.items;

import java.io.Serializable;
import java.util.Collection;

import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.base.IdentifiableDomainObject;
import com.railweb.shared.domain.events.DomainEvent;

public interface ItemSetObservable<T extends IdentifiableDomainObject<ID>, 
									ID extends ObserverId<U,V,E> & Serializable,
									U extends DomainObjectId<?>,
									V extends DomainObjectId<?>,
									E extends DomainEvent>
									extends IdentifiableDomainObject<ID>{

	Collection<T> getItems();
	
	void addObserver(ItemObserver<T,ID,U,V,E> observer);
	
	void removeObserver(ItemObserver<T,ID,U,V,E> observer);
	
	default void notifyObservers(T item, DomainEvent event) {
		
		for(ItemObserver<T,ID,U,V,E> observer: getObservers()) {
			ItemSetObserverId<U,V,ID> observerId = observer.getObserverId();
			if(observerId != null && observerId.getClass().isInstance(id())) {
				if(observerId.getItemSetOwnerId().equals(this.id())) {
					observer.handleEvent(item,event, observerId);
				}
			}
		}
	} 
	
	 Collection<ItemObserver<T,ID,U,V,E>> getObservers();

	default <U extends DomainObjectId<?>, V extends DomainObjectId<?>> boolean hasObservers(ID id) {
		return getObservers().stream()
				.anyMatch(observer ->observer.id().equals((V)id.getObserverId()));
	}
}
