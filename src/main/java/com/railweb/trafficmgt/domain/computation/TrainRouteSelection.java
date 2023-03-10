package com.railweb.trafficmgt.domain.computation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.railweb.trafficmgt.domain.network.Track;
import com.railweb.trafficmgt.domain.train.TimeInterval;
import com.railweb.trafficmgt.domain.train.Train;

@Component
public class TrainRouteSelection {

	private TrackSelectionComputation trackSelection;
	private TrainRouteTracksComputation tracksComputation;
	
	@Autowired
	private TrainRouteSelection(TrackSelectionComputation trackSelection,
			TrainRouteTracksComputation tracksComputation) {
		this.trackSelection = trackSelection;
		this.tracksComputation = tracksComputation; 
	}
    /**
     * Changes track - in case of different track route (change the whole chain).
     *
     * @param train train
     * @param interval interval
     * @param newTrack requested track
     */
	public void changeTrack(Train train, TimeInterval interval, Track track) {
		
	}
	public void recalculateLineSpeed(Train train, TimeInterval interval) {
		// TODO Auto-generated method stub
		
	}
}
