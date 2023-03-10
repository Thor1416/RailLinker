package com.railweb.shared.domain.events;

import java.time.Instant;

import org.springframework.lang.NonNull;

import com.railweb.shared.domain.base.DomainObject;

public interface DomainEvent extends DomainObject {
	   public enum Type {
		   ADDED, REMOVED, CHANGED

	}

	/**
     * Returns the time and date on which the event occurred.
     */
    @NonNull
    Instant occurredOn();

}
