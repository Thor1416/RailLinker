package com.railweb.trafficmgt.domain.train;

import javax.measure.Quantity;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Time;

import com.railweb.trafficmgt.domain.network.LineGradient;

public class PenaltyTable {


	
	public static Quantity<Time> getDecPenalty(Train train, LineGradient gradient, Quantity<Speed> speed) {
		PenaltyTableRow row = train.getTrainType() != null ? 
				train.getTrainType().getRowForSpeed(gradient, speed) :null;
		return row != null ? row.getDeaccelration() : null;
	}

	public static Quantity<Time> getAccPenalty(Train train, LineGradient gradient, Quantity<Speed> speed) {
		PenaltyTableRow row = train.getTrainType() != null ? 
				train.getTrainType().getRowForSpeed(gradient, speed) :null;
		return row != null ? row.getAcceleration() : null;
	}

}
