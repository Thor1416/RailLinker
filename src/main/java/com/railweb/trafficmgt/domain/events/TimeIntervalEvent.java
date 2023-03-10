package com.railweb.trafficmgt.domain.events;

import java.time.Instant;

import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.network.NetSegment;
import com.railweb.trafficmgt.domain.network.Track;
import com.railweb.trafficmgt.domain.train.TimeInterval;

import lombok.Getter;


public class TimeIntervalEvent<ID extends DomainObjectId<?>, T extends Track> implements DomainEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2601363941684617784L;
	@Getter
	private final NetSegment<ID,T> owner;
	@Getter
	private final TimeInterval interval;
	@Getter
	private final TimeIntervalEvent.Type type;
	
	private final Instant occuredOn;
	
	public enum Type{ADDED,REMOVED, CHANGED}
	
	public TimeIntervalEvent(NetSegment<ID, T> owner, TimeInterval interval, 
													Type type, Instant occuredOn) {
		this.owner = owner;
		this.interval = interval;
		this.type = type;
		this.occuredOn = occuredOn;
	}
	@Override
	public Instant occurredOn() {
		return occuredOn;
	}


}
