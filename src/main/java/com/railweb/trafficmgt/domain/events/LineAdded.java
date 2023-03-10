package com.railweb.trafficmgt.domain.events;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

import org.springframework.lang.NonNull;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.ids.LineId;
import com.railweb.trafficmgt.domain.ids.LinePathId;

import lombok.Getter;

public class LineAdded implements DomainEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4720918502512819875L;
	@Getter
	private final LineId lineId;
	private final Instant occuredOn;
	@Getter
	private Set<LinePathId> linepathsId;
	
	
	public LineAdded(@NonNull LineId lineid, @NonNull Instant occurredOn) {
		this.lineId = Objects.requireNonNull(lineid,"Line cannot be null");
		this.occuredOn = Objects.requireNonNull(occurredOn,"OccurredOn cannot be null");
	}

	@Override
	public Instant occurredOn() {
		return occuredOn;
	}

	public LineAdded(LineId lineId, Instant occuredOn, Set<LinePathId> linepathsId) {
		this.lineId = lineId;
		this.occuredOn = occuredOn;
		this.linepathsId = linepathsId;
	}

}
