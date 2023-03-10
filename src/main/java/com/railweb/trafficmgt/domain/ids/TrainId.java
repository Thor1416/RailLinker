package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.springframework.lang.NonNull;

import com.railweb.shared.anno.ValueObject;
import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.trafficmgt.domain.TrainNumber;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValueObject
@Embeddable
@Data
@EqualsAndHashCode
public class TrainId extends DomainObjectId<TrainTuple> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -174059362299605872L;
	@Column(name="train_Number")
	@Embedded
	@NonNull
	private TrainNumber trainNumber; 
	
	@Embedded
	@Column(name="timetableId")
	@NonNull
	private TimetableId timetableId;
	
	public TrainId(@NonNull TrainNumber trainNumber,@NonNull TimetableId timetableId) {
		super(new TrainTuple(timetableId, trainNumber));
		this.trainNumber = trainNumber;
		this.timetableId = timetableId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TrainNumber getTrainNumber() {
		return trainNumber;
	}

	public TimetableId getTimetableId() {
		return timetableId;
	}

	@Override
	protected void assignId() {
		if(this.timetableId == null) {
			throw new IllegalArgumentException("Train cannot exist outside a timetable");
		}
		if(this.trainNumber == null) {
			this.trainNumber = new TrainNumber(1L);
		}
		this.id = new TrainTuple(timetableId,trainNumber);
	}

	@Override
	public boolean sameValueAs(DomainObjectId<TrainTuple> other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UUID toUUID() {
		// TODO Auto-generated method stub
		return null;
	}

}
