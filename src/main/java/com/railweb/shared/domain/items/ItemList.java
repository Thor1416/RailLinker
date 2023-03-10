package com.railweb.shared.domain.items;

import java.util.List;

public interface ItemList<T> extends List<T> {

	default void move(T item, int index) {
		int oldIndex = this.indexOf(item);
		if(oldIndex == -1) {
			throw new IllegalArgumentException("Item not in list");

		} 
		
		this.move(oldIndex, index);
	}
	
	void move(int oldIndex, int newIndex);
}
