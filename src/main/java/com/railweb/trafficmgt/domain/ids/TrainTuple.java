package com.railweb.trafficmgt.domain.ids;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.ValueObject;
import com.railweb.trafficmgt.domain.TrainNumber;

import lombok.Data;

@Data
@Embeddable
public class TrainTuple implements ValueObject<TrainTuple> {

	private TimetableId timetable;
	private TrainNumber trainNumber;
	
	@Override
	public boolean sameValueAs(TrainTuple other) {
		// TODO Auto-generated method stub
		return false;
	}

	public TrainTuple(TimetableId timetable, TrainNumber trainNumber) {
		this.timetable = timetable;
		this.trainNumber = trainNumber;
	}

	
}
