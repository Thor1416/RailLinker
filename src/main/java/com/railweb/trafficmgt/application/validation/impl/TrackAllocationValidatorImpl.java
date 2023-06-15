package com.railweb.trafficmgt.application.validation.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.railweb.trafficmgt.application.validation.TrackAllocationValidator;
import com.railweb.trafficmgt.domain.train.TimeInterval;
import com.railweb.trafficmgt.domain.train.Train;
import com.railweb.trafficmgt.infra.repo.TrainRepository;

@Service
public class TrackAllocationValidatorImpl implements TrackAllocationValidator {

	private TrainRepository trainRepository;

	public TrackAllocationValidatorImpl(TrainRepository trainRepository) {
		this.trainRepository = trainRepository;
	}

	@Override
	public boolean isTrackAllocatedDuringTimeInterval(Train train, TimeInterval newTimeInterval) {
		List<Train> overlappingTrains = trainRepository.findOverlappingTrains(train.getId(),
				newTimeInterval.getStart(),newTimeInterval.getEnd(), newTimeInterval.getTrackId());
		for(Train overlappingTrain : overlappingTrains) {
			if(overlappingTrain.isTrackAllocated(newTimeInterval.getTrackId())) {
				   return true; // Track already allocated to another train during the given time interval
			}
		}

        return false; // Track is available during the given time interval
	}
	
	
}
