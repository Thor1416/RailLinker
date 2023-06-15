package com.railweb.trafficmgt.domain.network;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;

import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.TrackSectionId;

public class TrackSection extends AbstractEntity<TrackSectionId> {

	
	private String name;
	private Quantity<Length> sectionLength;
	private Quantity<Speed> permittedSpeed;
	private TravelDirection direction;
	
	public enum TravelDirection{
		LEFT,RIGHT, BOTH
	}
}
