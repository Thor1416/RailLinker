package com.railweb.trafficmgt.application.orc;

import java.util.Optional;

import com.railweb.trafficmgt.domain.ids.TrainClassId;
import com.railweb.trafficmgt.domain.train.TrainClass;
import com.railweb.trafficmgt.dto.TrainClassDTO;

public interface TrainClassOrchestrator {

	Boolean removeTrainClass(Long trainClassId, Long netId);

	TrainClass createTrainClass(Optional<TrainClassDTO> trainClass);

	TrainClassDTO getTrainClass(TrainClassId id);

}
