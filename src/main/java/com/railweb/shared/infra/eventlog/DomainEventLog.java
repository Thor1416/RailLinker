package com.railweb.shared.infra.eventlog;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.railweb.shared.domain.base.IdentifiableDomainObject;

public class DomainEventLog implements IdentifiableDomainObject<DomainEventLogId> {

	private final DomainEventLogId id;
	private final DomainEventLogId previous;
	private final DomainEventLogId next;
	private final List<StoredDomainEvent> events;

	DomainEventLog(@NonNull DomainEventLogId id, @Nullable DomainEventLogId previous, @Nullable DomainEventLogId next,
			@NonNull List<StoredDomainEvent> events) {
		this.id = id;
		this.previous = previous;
		this.next = next;
		this.events = List.copyOf(events);
	}

	@Override
	@NonNull
	public DomainEventLogId id() {
		return id;
	}

	/**
	 * Returns the ID of the previous domain event log if one exists.
	 */
	@NonNull
	public Optional<DomainEventLogId> previousId() {
		return Optional.ofNullable(previous);
	}

	/**
	 * Returns the ID of the next domain event log if one exists.
	 */
	@NonNull
	public Optional<DomainEventLogId> nextId() {
		return Optional.ofNullable(next);
	}

	/**
	 * Returns the events contained in this domain event log.
	 */
	@NonNull
	public List<StoredDomainEvent> events() {
		return events;
	}
}
