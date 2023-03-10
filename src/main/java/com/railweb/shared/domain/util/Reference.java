package com.railweb.shared.domain.util;

import java.util.function.Supplier;

public interface Reference<T> extends Supplier<T> {

	void set(T object);
}
