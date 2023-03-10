package com.railweb.trafficmgt.domain;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.railweb.trafficmgt.domain.network.Node;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Embeddable
@AllArgsConstructor
public class Location {

	private Integer x;
	private Integer y;
}
