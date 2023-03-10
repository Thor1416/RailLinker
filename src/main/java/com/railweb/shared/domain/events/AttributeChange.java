package com.railweb.shared.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AttributeChange {

	private final String name;
	private final Object oldValue;
	private final Object newValue;
	private final String category; 
	
	public AttributeChange(String name, Object oldValue, Object newValue) {
		this(name,oldValue,newValue,null);
	}
}
