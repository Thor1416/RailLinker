package com.railweb.trafficmgt.application.validation.impl;

import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.railweb.trafficmgt.application.validation.TrackValidator;
import com.railweb.trafficmgt.domain.ids.TrackId;
import com.railweb.trafficmgt.domain.train.TimeInterval;
import com.railweb.trafficmgt.domain.train.TimeIntervalList;

@Service
public class TrackValidatorImpl implements TrackValidator {

	@Override
	public boolean validateTrackPlatform(TrackId trackId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTimeIntervalOverlappingOnTrack(TimeInterval<?> newTimeInterval) {
		TimeIntervalList<?> tiList = newTimeInterval.getTrain().getTimeIntervalList();
		
		for(Iterator<TimeInterval> i =tiList.iterator(); i.hasNext();) {
			TimeInterval existingInterval = i.next();
			if(existingInterval.getTrackId().sameValueAs(newTimeInterval.getTrackId()) && 
					existingInterval.compareOpenNormalized(newTimeInterval)!=0) {
				return true;
			}
		}
		return false;
	}

}
