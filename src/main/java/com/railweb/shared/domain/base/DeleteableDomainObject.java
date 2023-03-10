package com.railweb.shared.domain.base;

/**
 * Interface for domain objects that can be softly deleted, meaning the domain object is not physically removed from
 * anywhere but only marked as deleted.
 */
public interface DeleteableDomainObject extends DomainObject {

	boolean isDeleted();
	void delete();
}
