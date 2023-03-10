package com.railweb.trafficmgt.application.orc;

import java.util.List;

import com.railweb.dto.RegionDTO;
import com.railweb.infrastructure.dto.TrackDTO;
import com.railweb.trafficmgt.application.command.CreateStationCommand;
import com.railweb.trafficmgt.domain.network.Network;
import com.railweb.trafficmgt.dto.StationDTO;
import com.railweb.trafficmgt.dto.TrainDTO;
import com.railweb.trafficmgt.dto.TrainRouteDTO;

public interface StationOrchestrator {

	List<StationDTO> getAllStations();
	StationDTO getStationById();
	List<StationDTO> getStationByRegion(RegionDTO regionDto);
	List<StationDTO> getStationByTrainRoute(TrainRouteDTO trainRoutDTO);
	StationDTO getStationByAbbr(String abbv, Network net);
	StationDTO getStationByAbbr(String abbr, Long netId);
	
	StationDTO addStation(CreateStationCommand command);
	StationDTO updateStation(StationDTO stationDTO);
	void deleteStation(StationDTO stationDTO);
	List<TrainDTO> getDepartureTable(StationDTO stationDTO);
	
	TrackDTO addTrackToStation(StationDTO stationDTO,TrackDTO trackDTO);
	
}
