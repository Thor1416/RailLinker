package com.railweb.shared.domain.base;

import java.io.Serializable;

import org.springframework.lang.Nullable;

public interface IdentifiableDomainObject<ID extends Serializable> extends DomainObject {
	/**
	 * Returns the ID of this domain object.
	 *
	 * @return the ID or {@code null} if an ID has not been assigned yet.
	 */
	@Nullable
	ID id();
}
