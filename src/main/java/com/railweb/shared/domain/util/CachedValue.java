package com.railweb.shared.domain.util;

import java.util.Objects;

public class CachedValue<T> {

	private boolean cached;
	private T value;
	
	public CachedValue() {}
	public CachedValue(boolean cached, T value) {
		this.cached = cached;
		this.value = value;
	}
	
	public void clear() {
		cached=false;
	}
	public boolean setValue(T value) {
		boolean different =!Objects.equals(this.value, value) && cached;
		this.value = value;
		this.cached = true;
		return different;
	}
	public T getValue() {
		return value;
	}
	public boolean isCached() {
		return cached;
	}
}
