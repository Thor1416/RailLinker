package com.railweb.shared.domain.base;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public abstract class RandomUUID implements ValueObject<RandomUUID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7201687599006337873L;

	@NotNull
	@Size(min = 16, max = 50)
	public final UUID id;

	public RandomUUID() {
		this.id = UUID.randomUUID();
	}

	public RandomUUID(UUID id) {
		this.id = id;
	}

}
