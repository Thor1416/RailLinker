package com.railweb.shared.domain.util;

public class ReferenceHolder<T> implements Reference<T> {

	private T reference;
	
	public ReferenceHolder() {
		reference=null;
	}
	public ReferenceHolder(T reference) {
		this.reference = reference;
	}
	
	@Override
	public T get() {
		return this.reference;
	}

	@Override
	public void set(T reference) {
		this.reference = reference;
		
	}

}
