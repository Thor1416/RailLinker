package com.railweb.shared.infra.eventlog;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.railweb.shared.domain.events.DomainEvent;

/**
 * The domain event log service is responsible for storing and retrieving
 * {@link DomainEvent}s. These can be used for auditing or for integration with
 * other systems / bounded contexts.
 *
 * @see StoredDomainEvent
 * @see DomainEventLog
 * @see DomainEventLogAppender
 */
@Service
public class DomainEventLogService {

	private static final int LOG_SIZE = 20;

	private final StoredDomainEventRepository storedEventDomainRepository;
	private final ObjectMapper objectMapper;

	DomainEventLogService(StoredDomainEventRepository storedDomainEventRepository, ObjectMapper objectMapper) {
		this.storedEventDomainRepository = storedDomainEventRepository;
		this.objectMapper = objectMapper;
	}

	private static long calculateHighFromLow(long low) {
		return low + LOG_SIZE - 1;
	}

	private static boolean isValidId(@NonNull DomainEventLogId id) {
		if (id.getHigh() - id.getLow() + 1 != LOG_SIZE) {
			return false;
		}
		return (id.getLow() - 1) % LOG_SIZE == 0;
	}

	/**
	 * Returns the domain event log with the given Id.
	 * 
	 * @param logId the ID of the log to retrieve.
	 * @return the log or anempty {@code Optional} if the log does not exist
	 */
	@NonNull
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<DomainEventLog> retrieveLog(@NonNull DomainEventLogId logId) {
		Objects.requireNonNull(logId, "logId must not be null");
		if (!isValidId(logId)) {
			return Optional.empty();
		}
		DomainEventLogId currentId = currentLogId();
		
		List<StoredDomainEvent> events =
				storedEventDomainRepository.findEventsBetween(logId.getLow(), logId.getHigh())
				.collect(Collectors.toList());
		
		if(events.isEmpty() && !logId.equals(currentId)) {
			return Optional.empty();
		}
		
		var previousId = previousLogId(logId).orElse(null);
		var nextId = logId.equals(currentId) ? null : nextLogId(logId);		
		
		return Optional.of(new DomainEventLog(logId, previousId, nextId,events));
	}

	@NonNull
	private DomainEventLogId currentLogId() {
		
		Long max = storedEventDomainRepository.findHighestDomainEventId();
		if(max == null) {
			max = 0L;
		}
		
		long remainder = max % LOG_SIZE;
		if(remainder == 0 && max == 0) {
			remainder = LOG_SIZE;
		}
		
		long low = max - remainder + 1;
		if(low < 1) {
			low = 1;
		}
		long high = calculateHighFromLow(low);
		return new DomainEventLogId(low,high); 
	}
	
	@NonNull
	private Optional<DomainEventLogId> previousLogId(@NonNull DomainEventLogId logId){
		Objects.requireNonNull(logId,"logId must not be null");
		
		if(logId.isFirst()) {
			return Optional.empty();
		}
		
		long low = logId.getLow() -  LOG_SIZE;
		if(low < 1) {
			low = 1;
		}
		
		long high = calculateHighFromLow(low);
		
		return Optional.of(new DomainEventLogId(low,high));
	}
	
	@NonNull
	private DomainEventLogId nextLogId(@NonNull DomainEventLogId logId) {
		long low = logId.getHigh() + 1;
		long high = calculateHighFromLow(low);
		
		return new DomainEventLogId(low,high);
	}
}