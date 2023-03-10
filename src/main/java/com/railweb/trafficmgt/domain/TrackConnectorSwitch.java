package com.railweb.trafficmgt.domain;

import javax.measure.Quantity;
import javax.measure.quantity.Speed;
import javax.persistence.Entity;

import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.SwitchId;
import com.railweb.trafficmgt.domain.network.LineTrack;
import com.railweb.trafficmgt.domain.network.NodeTrack;

import lombok.Data;

@Data
@Entity
public class TrackConnectorSwitch extends AbstractEntity<SwitchId> {

	private NodeTrack nodeTrack;
	private LineTrack lineTrack;
	private Quantity<Speed> maxSpeed;
	private boolean straight;
	
	private boolean events = false;

	@Override
	public SwitchId getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
