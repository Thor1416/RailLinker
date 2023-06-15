package com.railweb.trafficmgt.domain.train;

import java.time.Duration;
import java.time.OffsetTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.assertj.core.util.Lists;

import com.google.common.collect.Iterators;
import com.railweb.shared.domain.i18n.TranslatedString;
import com.railweb.shared.domain.util.CollectionUtils;
import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.TrainsCycleItemId;
import com.railweb.trafficmgt.domain.network.Node;
import com.railweb.trafficmgt.util.TimeUtil;

import lombok.Getter;

@Entity
@Getter
public class TrainsCycleItem extends AbstractEntity<TrainsCycleItemId>{
	
	@ManyToOne
	private Train train;
	private Integer cycleOrder;
	@ManyToOne
	private TimeInterval to; 
	@ManyToOne
	private TimeInterval from;
	@ManyToOne
	private TrainsCycle cycle;
	
	private Set<TranslatedString> comments;
	private Duration setupTime;
	
	
	public TrainsCycleItem(TrainsCycle cycle, Train train, Set<TranslatedString> comments,
			TimeInterval to, TimeInterval from) {
		
		this.cycle = cycle;
		this.train = train;
		this.comments = comments;
		this.from = (train.getFirstInterval() != from) ? from : null;
		this.to = (train.getLastInterval() != to) ? to : null;
	}
	
	public boolean containsInterval(TimeInterval timeInterval) {
		return Iterators.contains(CollectionUtils.closedIntervalIterator(train.getTimeIntervalList().iterator(),
				getFromInterval(), getToInterval()), timeInterval);
	}

	public List<TimeInterval> getIntervals(){
		return Lists.newArrayList(CollectionUtils.closedIntervalIterator(train.getTimeIntervalList().iterator(),
				getFromInterval(), getToInterval()));
	}
	 /**
     * @return always returns from time interval (if not specified then firt interval of the train)
     */
    public TimeInterval getFromInterval() {
        return from != null ? from : train.getFirstInterval();
    }

    /**
     * @return always returns to interval (if not specified then last interval of the train)
     */
    public TimeInterval getToInterval() {
        return to != null ? to : train.getLastInterval();
    }

    public Node getFromNode() {
        return this.getFromInterval().getOwnerAsNode();
    }

    public Node getToNode() {
        return this.getToInterval().getOwnerAsNode();
    }

    public OffsetTime getStartTime() {
        return this.getFromInterval().getEnd();
    }

    public OffsetTime getEndTime() {
        return this.getToInterval().getStart();
    }

    /**
     * @return normalized startTime in case start and end are not normalized
     */
    public OffsetTime getNormalizedStartTime() {
        OffsetTime startTime = this.getStartTime();
        return !TimeUtil.isNormalizedTime(startTime) ? TimeUtil.normalizeTime(startTime) : startTime;
    }

    /**
     * @return normalized endTime in case start and end are not normalized
     */
    public OffsetTime getNormalizedEndTime() {
        OffsetTime startTime = this.getStartTime();
        OffsetTime endTime = this.getEndTime();
        return !TimeUtil.isNormalizedTime(startTime) ? TimeUtil.normalizeTime(endTime) : endTime;
    }

    public TrainsCycleItem getNextItem() {
        return getCycle().getNextItem(this);
    }

    public TrainsCycleItem getNextItemCyclic() {
        return getCycle().getNextItemCyclic(this);
    }

    public TrainsCycleItem getPreviousItem() {
        return getCycle().getPreviousItem(this);
    }

    public TrainsCycleItem getPreviousItemCyclic() {
        return getCycle().getPreviousItemCyclic(this);
    }

	@Override
	public TrainsCycleItemId getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
