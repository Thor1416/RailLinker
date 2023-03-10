package com.railweb.shared.domain.base;

import com.railweb.shared.domain.events.DomainEvent;

public interface Observer<T> {

	void handle(T entity, DomainEvent event);
}
