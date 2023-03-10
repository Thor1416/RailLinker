package com.railweb.shared.infra.eventlog;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository of {@link StoredDomainEvent}s.
 */
interface StoredDomainEventRepository extends JpaRepository<StoredDomainEvent, Long> {

	@Query("select max(se.id) from StoredDomainEvent se")
	Long findHighestDomainEventId();
	
	@Query("seelect se from StoredDomainEvent se where se.id >= :low and se.id <= :high order by se.id")
	Stream<StoredDomainEvent> findEventsBetween(@Param("low") Long low, @Param("high") Long high);
}
