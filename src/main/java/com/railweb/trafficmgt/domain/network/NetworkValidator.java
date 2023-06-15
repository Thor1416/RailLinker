package com.railweb.trafficmgt.domain.network;

import com.railweb.shared.domain.events.DomainEvent;

public interface NetworkValidator {

	boolean validate(DomainEvent event);
}
