package com.railweb.shared.domain.util;

import java.util.Iterator;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;

public class CollectionUtils {

	

	public static <T> Iterable<T> closedIntervalIterable(Iterable<T> iterable, T start, T end){
		return ()->closedIntervalIterator(iterable.iterator(), start, end);
	}
	
	 /**
     * Returns iterator which contains only closed interval delimited by the parameters.
     *
     * @param iterator source iterator
     * @param start start element
     * @param end end element
     * @return iterator with interval
     */
	public static <T> Iterator<T> closedIntervalIterator(Iterator<T> iterator, T start, T end){
		class ClosedIterator extends AbstractIterator<T>{
			boolean in = false;
			boolean stopped = false;
			
			@Override
			protected T computeNext() {
				if(!in) {
					T value = Iterators.find(iterator, item -> item == start, null);
					if(value != null) {
						in = true;
						stopped = value==end;
						return value;
					}
				}
				if(!stopped && in && iterator.hasNext()) {
					T value = iterator.next();
					if(value==end) {
						stopped = true;
					}
					return value;
				}
				return endOfData();
			}
		}
		return new ClosedIterator();
	}
}
