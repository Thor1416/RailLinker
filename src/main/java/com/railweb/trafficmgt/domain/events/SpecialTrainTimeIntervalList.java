package com.railweb.trafficmgt.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpecialTrainTimeIntervalList {

	public enum Type{MOVED, STOP_TIME, SPEED, TRACK, RECALCULATE}
	
	private final Type type;
	private final int start;
	private final int changed;
}
