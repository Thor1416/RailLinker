package com.railweb.trafficmgt.domain;

import java.util.Collections;
import java.util.Set;

import com.railweb.trafficmgt.domain.train.TimeInterval;

import lombok.Getter;
import lombok.ToString;
@ToString
public class TimeIntervalResult {
	
	public enum Status{
		OK, OVERLAPPING
	}
	@Getter
	private final Status status;
	@Getter
	private final Set<TimeInterval> overlappingIntervals;
	
	public TimeIntervalResult(Status status) {
		this.status=status;
		this.overlappingIntervals = Collections.emptySet();
	}

	public TimeIntervalResult(Status status, Set<TimeInterval> overlappingIntervals){
		this.status=status;
		this.overlappingIntervals=overlappingIntervals;
	}
}
