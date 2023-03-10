package com.railweb.trafficmgt.filters;

import com.railweb.trafficmgt.domain.train.TimeInterval;

public class ModelPredicates {

	   public static boolean nodeInterval(TimeInterval interval) {
	        return interval.isNodeOwner();
	    }

}
