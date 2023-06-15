package com.railweb.trafficmgt.application.validation;

import com.railweb.trafficmgt.domain.ids.TrackId;
import com.railweb.trafficmgt.domain.train.TimeInterval;

public interface TrackValidator {

	boolean validateTrackPlatform(TrackId trackId);
	boolean isTimeIntervalOverlappingOnTrack(TimeInterval<?> newTimeInterval); 
}
