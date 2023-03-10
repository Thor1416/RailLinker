package com.railweb.shared.domain.util;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Pair<U,V> {

	private U first;
	private V second;
	public Pair(U first, V second) {
		this.first = first;
		this.second = second;
	}
	
	
}
