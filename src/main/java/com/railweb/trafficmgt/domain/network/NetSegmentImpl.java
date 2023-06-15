package com.railweb.trafficmgt.domain.network;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.collect.Iterators;
import com.railweb.shared.domain.base.AbstractAggregateRoot;
import com.railweb.trafficmgt.domain.ids.NetSegmentId;
import com.railweb.trafficmgt.domain.train.TimeInterval;


abstract class NetSegmentImpl<ID extends NetSegmentId<?>, T extends Track<ID>>
						extends AbstractAggregateRoot<ID> 
						implements NetSegment<ID,T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9072327907090285310L;

	protected NetSegmentImpl(AbstractAggregateRoot<ID> source) {
		super(source);
	}

	protected List<T> tracks;

	public Iterator<TimeInterval<ID>> iterator() {
		List<Iterator<TimeInterval<? extends NetSegmentId<?>>>> iterators = new ArrayList<>();
		
		for(T track: tracks) {
			iterators.add(track.iterator());
		}
		
		return new TimeIntervalIterator<>(iterators.iterator());
		
	}
	
	private static class TimeIntervalIterator<ID extends NetSegmentId<?>, 
							N extends TimeInterval<? extends NetSegmentId<?>>> 
						implements Iterator<TimeInterval<ID>>{
		
		private final Iterator<Iterator<TimeInterval<? extends NetSegmentId<?>>>> iteratorOfIterators;
		private Iterator<TimeInterval<? extends NetSegmentId<?>>> currentIterator;
		private TimeInterval<ID> nextElement;

		public TimeIntervalIterator(Iterator<Iterator<TimeInterval<? extends NetSegmentId<?>>>> iteratorOfIterators) {
			this.iteratorOfIterators = iteratorOfIterators;
			currentIterator= getNextIterator();
		}
		
		private Iterator<TimeInterval<? extends NetSegmentId<?>>> getNextIterator(){
			
			while(iteratorOfIterators.hasNext()) {
				
				Iterator<TimeInterval<? extends NetSegmentId<?>>> iterator =
													iteratorOfIterators.next();
				if(iterator.hasNext()) {
					return iterator;
				}
			}
			return null;
		}
		
		@Override
		public boolean hasNext() {
			if(nextElement != null) {
				return true;
			}
			while (currentIterator != null && !currentIterator.hasNext()) {
				currentIterator = getNextIterator();
			}
			if(currentIterator !=null) {
				nextElement = (TimeInterval<ID>) currentIterator.next();
				return true;
			}
			
			return false;
		}

		@Override
		public TimeInterval<ID> next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			
			TimeInterval<ID> result = nextElement;
			nextElement = null;
			return result;
		}
	}
}
