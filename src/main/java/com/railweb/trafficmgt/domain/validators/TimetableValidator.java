package com.railweb.trafficmgt.domain.validators;

import com.railweb.shared.domain.events.DomainEvent;

/**
 * Validator to keep timetables valid
 * @author Thorbjoern Simonsen
 *
 */
public interface TimetableValidator {
	
	boolean validate(DomainEvent event);
}
