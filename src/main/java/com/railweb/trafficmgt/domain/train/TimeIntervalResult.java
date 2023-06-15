package com.railweb.trafficmgt.domain.train;

import java.util.Collections;
import java.util.Set;

import com.railweb.trafficmgt.domain.ids.NetSegmentId;

import lombok.Getter;
import lombok.ToString;
@ToString
public class TimeIntervalResult<T extends NetSegmentId<?>> {
	
	public enum Status{
		OK, OVERLAPPING
	}
	@Getter
	private final Status status;
	@Getter
	private final Set<TimeInterval<T>> overlappingIntervals;
	
	public TimeIntervalResult(Status status) {
		this.status=status;
		this.overlappingIntervals = Collections.emptySet();
	}

	public TimeIntervalResult(Status status, Set<TimeInterval<T>> overlappingIntervals){
		this.status=status;
		this.overlappingIntervals=overlappingIntervals;
	}
}
