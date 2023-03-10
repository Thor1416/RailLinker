package com.railweb.shared.domain.util;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.railweb.shared.domain.base.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Location implements ValueObject<Location> {

	@Id
	private Long id;
	private Integer x;
	private Integer y;
	@Override
	public boolean sameValueAs(Location other) {
		// TODO Auto-generated method stub
		return false;
	}
}
