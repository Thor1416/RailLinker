package com.railweb.trafficmgt.application.orc;

import java.util.List;

import com.railweb.infrastructure.dto.TrackDTO;
import com.railweb.infrastructure.dto.TrackSystemDTO;
import com.railweb.trafficmgt.application.command.TrainSearchCommand;
import com.railweb.trafficmgt.domain.commands.CreateTrainCommand;
import com.railweb.trafficmgt.dto.StationDTO;
import com.railweb.trafficmgt.dto.TimetableDTO;
import com.railweb.trafficmgt.dto.TrainClassDTO;
import com.railweb.trafficmgt.dto.TrainCyclesDTO;
import com.railweb.trafficmgt.dto.TrainDTO;
import com.railweb.trafficmgt.dto.TrainStopDTO;

public interface TrainOrchestrator {

	List<TrainDTO> getAllTrains();
	List<TrainDTO> getAllTrainsInTimetable(Long timetable);
	List<TrainDTO> getAllTrainsInTimetable(TimetableDTO timetableDTO);
	TrainDTO getByID(Long Id);
	TrainDTO getByNumber(Long timetabelId, String number);
	
	List<TrainDTO> getByStation(StationDTO stationDTO, TimetableDTO timetableDTO);
	List<TrainDTO> getByStation(Long stationId, Long timetableId);
	List<TrainDTO> getCurrentTrain(StationDTO stationDTO);
	TrainDTO getByPreviousJoinedTrain(TrainDTO trainDTO);
	TrainDTO getByNextJoinedTrain(TrainDTO trainDTO);
	List<TrainDTO> searchTrain(TrainSearchCommand trainSearchCommend);
 	List<TrainDTO> getTrainsByTrack(TrackDTO trackDTO);

	void createTrain(CreateTrainCommand command);
	Boolean deleteTrain(TrainDTO trainDTO);
	TrainDTO updateTrain(TrainDTO trainDTO);
	
	//Train Classes
	List<TrainDTO> getTrainsByClass(TrainClassDTO trainClassDTO);
	List<TrainDTO> getTrainsByClass(Long trainClassId);
	List<TrainDTO> getTrainsByClassAndTimetable(TrainClassDTO trainClassDTO,
			TimetableDTO timetableDTO);
	List<TrainDTO> getTrainsByClassAndTimetable(Long trainClassId, Long timetableID);
	
	//TrainStop
	List<TrainStopDTO> getTrainStopsByTrain(TrainDTO trainDTO);
	List<TrainStopDTO> getTrainStopsByTrain(Long trainId);
	TrainStopDTO getTrainStopByTrainAndNumber(TrainDTO trainDTO, Integer stopNumber);
	TrainStopDTO getTrainStopByTrainAndStation(Long TrainId, Long staionId);
	TrainStopDTO getTrainStopByTrainNumberAndStation(String trainNumber, 
			Long timetableId, Long staionId);
	TrainStopDTO updateTrainStop(TrainStopDTO trainStopDTO);
	void deleteTrainStop(TrainStopDTO trainStopDTO);
	
	//Train class
	List<TrainDTO> getTrainByTrainClass(TrainClassDTO trainClassDTO);
	
	//Train Cycles
	List<TrainCyclesDTO> getTrainCyclesByTrainDTO(TrainDTO trainDTO); 
	List<TrainCyclesDTO> getTrainCyclesByTrainId(Long trainId);
	
	TrackSystemDTO getTrackSystem(TrainDTO trainDTO);
	TrackSystemDTO getTrackSystem(Long trainID);
}
