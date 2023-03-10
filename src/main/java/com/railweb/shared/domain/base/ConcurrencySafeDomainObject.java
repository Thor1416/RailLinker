package com.railweb.shared.domain.base;

import org.springframework.lang.Nullable;

public interface ConcurrencySafeDomainObject {
	
	@Nullable
	Long version();
}
