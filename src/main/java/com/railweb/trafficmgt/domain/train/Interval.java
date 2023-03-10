package com.railweb.trafficmgt.domain.train;

import java.time.Duration;
import java.time.OffsetTime;

public interface Interval {
	
	OffsetTime getStart();
	
	OffsetTime getEnd();
	
	OffsetTime getNormalizedStart();
	
	OffsetTime getNormalizedEnd();
	
	Duration getLength();
	
	Interval normalize();

	boolean isNormalized();
	
	boolean isOverMidnight();
	
	int compareOpen(Interval interval);
	
	int compareOpenNormalized(Interval interval);
	
	int compareClosed(Interval interval);
	
	int compareClosedNormalized(Interval interval);
	
	Interval getNonNormalizedInvalOverNidnight();
}
