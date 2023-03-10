package com.railweb.shared.domain.items;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.railweb.shared.domain.base.DomainObjectId;

public interface ItemSet<T> extends Set<T> {
	
	default void replaceAll(Collection<? extends T> list) {
		this.retainAll(list);
		this.addAll(list);
	}
	
	default Optional<T> find(Predicate<T> predicate){
		return Iterables.tryFind(this, predicate::test).toJavaUtil();
	}
	default Set<T> findAll(Predicate<T> predicate){
		return FluentIterable.from(this).filter(predicate::test).toSet();
	}
	void removeObserver(ItemObserver<T,?> observer);
	void addAllObservers(Collection<ItemObserver<T,?>> observers);
	void removeAllObservers(Collection<ItemObserver<T,?>> observers);

	<IDO extends DomainObjectId<?>> void addObserver(ItemObserver<T, IDO> observer);
}
