package com.railweb.dto;

import java.util.Set;

import lombok.Data;

@Data
public class RegionDTO {

	private Long id;
	private String name;
	private Long parentId;
	private Set<Long> nodes;
}
