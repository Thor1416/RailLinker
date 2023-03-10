package com.railweb.trafficmgt.domain;

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

import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.TimeIntervalListId;
import com.railweb.trafficmgt.domain.train.TimeInterval;
import com.railweb.trafficmgt.domain.train.TimeIntervalCalculation;

@Entity
public class TimeIntervalList extends AbstractEntity<TimeIntervalListId> implements List<TimeInterval> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TimeIntervalListID")
	private Long id;
	@OneToMany(
			mappedBy="TimeIntervalList",
			cascade=CascadeType.ALL,
			orphanRemoval=true
	)
	@OrderColumn
	private List<TimeInterval> wrapee = new ArrayList<>();

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
	public void addIntervalForRouteSegment(TimeInterval interval) {
		
		// Update overlappingintervals
		interval.setOverlappingIntervals(this.testIntervalForRouteSegmentOI(interval).getOverlappingIntervals());
		
		for(TimeInterval item:interval.getOverlappingIntervals()) {
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

	public TimeIntervalResult testIntervalForRouteSegmentOI(TimeInterval interval) {
		Set<TimeInterval> overlaps = null;
		TimeIntervalResult.Status status = TimeIntervalResult.Status.OK;
		
		for(TimeInterval item: wrapee) {
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
	public Iterator<TimeInterval> iterator() {
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
	public boolean addAll(Collection<? extends TimeInterval> collection) {
		wrapee.addAll(collection);
		for(TimeInterval interval: collection) {
			interval.setList(this);
		}
		return true;
	}


	@Override
	public boolean addAll(int index, Collection<? extends TimeInterval> collection) {
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
	public ListIterator<TimeInterval> listIterator() {
		return wrapee.listIterator();
	}


	@Override
	public ListIterator<TimeInterval> listIterator(int index) {
		return wrapee.listIterator(index);
	}


	@Override
	public List<TimeInterval> subList(int fromIndex, int toIndex) {
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
}
