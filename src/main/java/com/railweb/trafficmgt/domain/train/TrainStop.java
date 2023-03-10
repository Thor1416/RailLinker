package com.railweb.trafficmgt.domain.train;

import java.time.ZonedDateTime;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.TrainStopId;
import com.railweb.trafficmgt.domain.network.Station;

import lombok.Data;

@Data
@Entity
public class TrainStop extends AbstractEntity<TrainStopId>{

	@EmbeddedId
	private TrainStopId id;
	private Station station;
	private ZonedDateTime arrival;
	private ZonedDateTime departure;
	private Platform platform;
	
	public boolean isValid() {
		return arrival.isBefore(departure);
	}

	public boolean isBefore(TrainStop trainStop) {
		// TODO Auto-generated method stub
		return false;
	}
	public String toString() {
		return "Trainstop with id:" + id + "at" + station.getName();
	}

	public void updateInterval(TimeInterval interval) {
		// TODO Auto-generated method stub
		
	}

	
}
