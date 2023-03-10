package com.railweb.shared.domain.base;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.MappedSuperclass;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.shared.domain.items.ObserverId;

@MappedSuperclass
public class AbstractObservableEntity<ID extends ObserverId<?,?,?>, 
										T extends AbstractObservableEntity<ID,T>>
									implements Observable<T>{

	private Set<Observer<T>> observers = new HashSet<>();

	@Override
	public void addObserver(Observer<T> observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer<T> observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObserver(T t, DomainEvent event) {
		observers.forEach(observer ->observer.handle(t, event));
	}
	
	
}
