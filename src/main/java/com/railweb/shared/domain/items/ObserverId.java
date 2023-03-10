package com.railweb.shared.domain.items;

import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.base.IdentifiableDomainObject;
import com.railweb.shared.domain.events.DomainEvent;



public abstract class ObserverId<ID extends DomainObjectId<?>,
								O extends DomainObjectId<?>,
								E extends DomainEvent> 
					extends DomainObjectId<ObserverId<?,?,?>>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4918001555067593283L;
	private final ID itemId;
	private final O observerId;
	private final Class<E> eventType;

	public ObserverId(ID itemId, O observer,  Class<E> eventType) {
		super(ObserverId.class);
		this.itemId = itemId;
		this.observerId = observer;
		this.eventType = eventType;
		
	}
	
	public ID getItemId() {
		return itemId;
	}
	public O getObserverId() {
		return observerId;
	}
	public Class<E> getEventType() {
        return eventType;
	}
}
