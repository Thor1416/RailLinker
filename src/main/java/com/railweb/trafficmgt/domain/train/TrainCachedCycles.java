package com.railweb.trafficmgt.domain.train;

import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.SetMultimap;
import com.railweb.shared.domain.util.CollectionUtils;
import com.railweb.trafficmgt.filters.ModelPredicates;

public class TrainCachedCycles {

	private final SetMultimap<TimeInterval,TrainsCycleItem> map;
	
	public TrainCachedCycles() {
		map = HashMultimap.create();
	}
	
	public void add(List<TimeInterval> intervals, TrainsCycleItem item) {
		Iterable<TimeInterval> iterable = CollectionUtils.closedIntervalIterable(
				intervals, item.getFromInterval(), item.getToInterval());
		for(TimeInterval interval :iterable) {
			this.add(intervals, item);
		}
	}
    /**
     * adds trains cycle item to the list.
     *
     * @param timeIntervalList time interval list
     * @param items list of trains cycle items
     * @param item item to be added
     * @param overlapping of the overlapping is allowed
     */
	public void addCycleItem(List<TimeInterval> timeIntervalList, List<TrainsCycleItem> items,
			TrainsCycleItem item, boolean overlapping) {
		
		if(!testAddCycle(timeIntervalList,item, null, overlapping)) {
			throw new IllegalArgumentException("Overlapping item.");
		}
		ListIterator<TrainsCycleItem> i = items.listIterator();
		TrainsCycleItem current = i.hasNext() ? i.next() : null;
		
		for(TimeInterval interval: Iterables.filter(timeIntervalList, ModelPredicates::nodeInterval)) {
			if(current != null && interval  == current.getFromInterval()) {
				current = i.hasNext() ? i.next() : null;
			}
			if(current ==  null || interval == item.getFromInterval()) {
				if(current != null && i.hasPrevious()) {
					i.previous();
				}
				i.add(item);
				return;
			}
		}
	}

	private boolean testAddCycle(List<TimeInterval> timeIntervalList, TrainsCycleItem item,
			Object object, boolean overlapping) {
		// TODO Auto-generated method stub
		return false;
	}

	public void addCycleItem(TimeIntervalListId timeIntervalListId, TrainCycleMap trainCycleMap) {
		// TODO Auto-generated method stub
		
	}
}

