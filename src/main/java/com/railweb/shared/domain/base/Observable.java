package com.railweb.shared.domain.base;

import com.railweb.shared.domain.events.DomainEvent;

public interface Observable<T> {

	void addObserver(Observer<T> observer);
	void removeObserver(Observer<T> observer);
	void notifyObserver(T t, DomainEvent event);
}
