package com.railweb.trafficmgt.application.validation;

import com.railweb.trafficmgt.domain.train.TimeInterval;
import com.railweb.trafficmgt.domain.train.Train;

public interface TrackAllocationValidator {

	boolean isTrackAllocatedDuringTimeInterval(Train train, TimeInterval newTimeInterval);
}
