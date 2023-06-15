package com.railweb.trafficmgt.domain.train;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;

import javax.measure.Quantity;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Time;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.NetSegmentId;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ids.TimeIntervalListId;

@Entity
public class TimeIntervalList<T extends NetSegmentId<?>> extends AbstractEntity<TimeIntervalListId> 
							implements List<TimeInterval<T>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<TimeInterval<T>> wrapee = new ArrayList<>();

	public TimeInterval getInterval(TimeInterval i, int relativeIndex) {
		if(relativeIndex  == 0) {
			return i;
		}else {
			int ind = this.indexOf(i)+ relativeIndex;
			if(ind<0 || ind >= this.size()) {
				return null;
			}
			return this.get(ind);
		}
	}
	
	
	
	/*add time interval to the list*/
	public <T extends NetSegmentId<?>> void addIntervalForRouteSegment(TimeInterval<T> interval) {
		
		// Update overlappingintervals
		interval.setOverlappingIntervals(this.testIntervalForRouteSegmentOI(interval).getOverlappingIntervals());
		
		for(TimeInterval<T> item:interval.getOverlappingIntervals()) {
			item.getOverlappingIntervals().add(interval);
		}
		
		addIntervalImpl(interval);
	}


	private void addIntervalImpl(TimeInterval interval) {
		int i=0;
		for(TimeInterval item :wrapee) {
			if(item.getInterval().compareOpenNormalized(interval.getInterval()) ==-1) {
				this.add(i, interval);
				return;
			}
			i++;
		}
	}

	/**
     * tests if specified time interval is available to be added. It returns overlapping intervals.
     *
     * @param interval time interval to be tested
     * @return result
     */

	public TimeIntervalResult<?> testIntervalForRouteSegmentOI(TimeInterval<? extends NetSegmentId<?>> interval) {
		Set<TimeInterval<? extends NetSegmentId<?>>> overlaps = null;
		TimeIntervalResult.Status status = TimeIntervalResult.Status.OK;
		
		for(TimeInterval<? extends NetSegmentId<?>> item: wrapee) {
			if(item.compareClosedNormalized(interval) == 0 && item != interval) {
				if(status == TimeIntervalResult.Status.OK) {
					status = TimeIntervalResult.Status.OVERLAPPING;
					overlaps = new HashSet<>();
				}
				overlaps.add(interval);
			}
		}
		return new TimeIntervalResult(status,overlaps);
	}

    /**
     * tests if specified time frame is available. It doesn't return ovelapping intervals.
     *
     * @param interval time interval to be tested
     * @return result
     */
    public TimeIntervalResult testIntervalForRouteSegment(TimeInterval interval) {
        for (TimeInterval item : this) {
            if (item.compareOpenNormalized(interval) == 0 && item != interval) {
                return new TimeIntervalResult(TimeIntervalResult.Status.OVERLAPPING, Collections.singleton(item));
            }
        }
        return new TimeIntervalResult(TimeIntervalResult.Status.OK);
    }


	@Override
	public int size() {
		return wrapee.size();
	}


	@Override
	public boolean isEmpty() {
		return wrapee.isEmpty();
	}


	@Override
	public boolean contains(Object o) {
		return wrapee.contains(o);
	}


	@Override
	public Iterator<TimeInterval<? extends NetSegmentId<?>>> iterator() {
		return wrapee.iterator();
	}


	@Override
	public Object[] toArray() {
		return wrapee.toArray();
	}


	@Override
	public <T> T[] toArray(T[] a) {
		return wrapee.toArray(a);
	}


	@Override
	public boolean add(TimeInterval interval) {
		wrapee.add(interval);
		interval.setList(this);
		return true;
	}


	@Override
	public boolean remove(Object o) {
		return wrapee.remove(o);
	}


	@Override
	public boolean containsAll(Collection<?> c) {
		return wrapee.containsAll(c);
	}


	@Override
	public boolean addAll(Collection<? extends TimeInterval<T>> collection) {
		wrapee.addAll(collection);
		for(TimeInterval interval: collection) {
			interval.setList(this);
		}
		return true;
	}


	@Override
	public boolean addAll(int index, Collection<? extends TimeInterval<T>> collection) {
		Objects.requireNonNull(collection);
		wrapee.addAll(index, collection);
		for(TimeInterval interval: collection) {
			interval.setList(this);
		}
		return true;
	}


	@Override
	@SuppressWarnings("unchecked")
	public boolean removeAll(Collection<?> c) throws ClassCastException {
		Objects.requireNonNull(c);
		Collection<? extends TimeInterval> collection =(Collection<? extends TimeInterval>) c;
		wrapee.removeAll(c);
		for(TimeInterval interval:collection) {
			interval.setList(null);
		}
		return true;
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean retainAll(Collection<?> c) {
		Collection<? extends TimeInterval> col =(Collection<? extends TimeInterval>) c;
		List<? extends TimeInterval> addiCol = new ArrayList<TimeInterval>(col);
		addiCol.removeAll(col);
		for(TimeInterval interval:addiCol) {
			interval.setList(null);
		}
		return wrapee.retainAll(col);
	}


	@Override
	public void clear() {
		for(TimeInterval interval :wrapee) {
			interval.setList(null);
		}
		wrapee.clear();
	}


	@Override
	public TimeInterval get(int index) {
		return wrapee.get(index);
	}


	@Override
	public TimeInterval set(int index, TimeInterval element) {
		element.setList(this);
		return wrapee.set(index, element);
	}


	@Override
	public void add(int index, TimeInterval element) {
		element.setList(this);
		wrapee.add(index, element);
	}


	@Override
	public TimeInterval remove(int index) {
		return wrapee.remove(index);
	}


	@Override
	public int indexOf(Object o) {
		return wrapee.indexOf(o);
	}


	@Override
	public int lastIndexOf(Object o) {
		return wrapee.lastIndexOf(o);
	}


	@Override
	public ListIterator<TimeInterval<? extends NetSegmentId<?>>> listIterator() {
		return wrapee.listIterator();
	}


	@Override
	public ListIterator<TimeInterval<? extends NetSegmentId<?>>> listIterator(int index) {
		return wrapee.listIterator(index);
	}


	@Override
	public List<TimeInterval<? extends NetSegmentId<?>>> subList(int fromIndex, int toIndex) {
		return wrapee.subList(fromIndex, toIndex);
	}


	public boolean updateInterval(TimeInterval interval) {
		return (interval.isNodeOwner()) ? this.updateNodeInterval(interval): this.updateLineInterval(interval);
	}
	
	public boolean updateNodeInterval(TimeInterval interval) {
		   if (!interval.isNodeOwner()) {
	            throw new IllegalArgumentException("Node is not owner of the time interval.");
	        }
	        boolean changed = interval.getChanged();
	        if (interval.isAttached()) {
	            interval.updateInOwner();
	        }
	        return changed;
	}
	
	public boolean updateLineInterval(TimeInterval interval) {
		if(!interval.isLineOwner()) {
			throw new IllegalArgumentException("Line is not owner of the interval.");
		}
		
		TimeIntervalCalculation calculation = interval.getCalculation();
		Quantity<Speed> computedSpeed = calculation.computeLineSpeed();
		Quantity<Time> runnigntime = calculation.computeRunningTime(computedSpeed);
		
		return true;
	}


	@Override
	public TimeIntervalListId getId() {
		// TODO Auto-generated method stub
		return null;
	}



	public boolean areNodesInTimeIntervals(NodeId sourceNode, NodeId destNode) {
		boolean foundSource = false;
		boolean foundDest = false;
		
		for(TimeInterval timeInterval: wrapee) {
			if(timeInterval.getFromNode().sameValueAs(sourceNode)) {
				foundSource = true;
			}
			if(timeInterval.getToNode().sameValueAs(destNode)) {
				foundDest = true;
			}

		}
		
		return (foundSource && foundDest);
	}
}
