package com.railweb.trafficontrol.model;

import javax.measure.Quantity;
import javax.measure.quantity.Length;

import com.railweb.trafficmgt.domain.train.TimeIntervalDirection;

import lombok.Data;

@Data
public class Block {

	private Quantity<Length> length;
	
	public String toString(TimeIntervalDirection direction) {
		// TODO Auto-generated method stub
		return null;
	}
}
