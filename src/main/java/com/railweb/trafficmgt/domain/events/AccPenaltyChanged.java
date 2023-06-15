package com.railweb.trafficmgt.domain.events;

import java.time.Instant;
import java.util.Objects;

import javax.measure.Quantity;
import javax.measure.quantity.Time;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.trafficmgt.domain.train.PenaltyTableRow;

public class AccPenaltyChanged implements DomainEvent  {

	private final PenaltyTableRow source;
	private final Quantity<Time> oldValue;
	private final Quantity<Time> newValue;
	private final Instant occurredOn;
	
	public AccPenaltyChanged(@NonNull PenaltyTableRow source, 
			@Nullable Quantity<Time> oldValue, @NonNull Quantity<Time> newValue, @NonNull Instant occurredOn) {
		this.source = Objects.requireNonNull(source,"PenaltyTablerow cannot bee null;");
		this.oldValue = oldValue;
		this.newValue = Objects.requireNonNull(newValue,"New value cannot bee null;");
		this.occurredOn = Objects.requireNonNull(occurredOn, "occurredOn must not be null");
	}

	@Override
	public Instant occurredOn() {
		return occurredOn;
	}

	@Override
	public boolean isConsumed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
