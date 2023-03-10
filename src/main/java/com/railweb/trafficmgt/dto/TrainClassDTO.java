package com.railweb.trafficmgt.dto;

import java.util.UUID;

import com.railweb.shared.web.BaseDTO;
import com.railweb.trafficmgt.domain.train.TrainClass;

import lombok.Data;

@Data
public class TrainClassDTO implements BaseDTO {

	public TrainClassDTO(TrainClass createdTrainClass) {
		// TODO Auto-generated constructor stub
	}

	private UUID id;
}
