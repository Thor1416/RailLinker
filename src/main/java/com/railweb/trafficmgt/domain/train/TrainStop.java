package com.railweb.trafficmgt.domain.train;

import java.time.ZonedDateTime;

import com.railweb.shared.domain.base.AbstractDomainEntity;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ids.PlatformId;
import com.railweb.trafficmgt.domain.ids.TrainStopId;

import lombok.Data;

@Data
public class TrainStop extends AbstractDomainEntity<TrainStopId>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8926196334108801449L;
	private NodeId stopNode;
	private ZonedDateTime arrival;
	private ZonedDateTime departure;
	private Boolean needsPlatform;
	private PlatformId platform;
	
	public boolean isValid() {
		return arrival.isBefore(departure);
	}

	public boolean isBefore(TrainStop trainStop) {
		// TODO Auto-generated method stub	
		return false;
	}
	public String toString() {
		return "Trainstop with id:" + id + "at" + stopNode.toString();
	}

	public void updateInterval(TimeInterval interval) {
		// TODO Auto-generated method stub
		
	}

	
}
