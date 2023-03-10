package com.railweb.trafficmgt.application.orc;

import java.util.List;

import com.railweb.trafficmgt.dto.TrainDTO;
import com.railweb.trafficmgt.dto.TrainRouteDTO;

public interface TrainRouteOrchstrator {
	//Train routes
	List<TrainRouteDTO> getAllTrainRoutes();
	List<TrainDTO> getTrainsByTrainRoute(TrainRouteDTO trainRouteDTO);
	TrainRouteDTO getTrainRouteById(Long Id);
	Boolean deleteTrainRoute(TrainRouteDTO trainRouteDTO);
	Boolean deleteTrainRoute(TrainRouteDTO trainRouteDTO, Boolean deleteTrainsOnRoute);
		
}
