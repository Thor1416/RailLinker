package com.railweb.trafficmgt.dto.impl;

import com.railweb.trafficmgt.dto.AbbrDTO;
import com.railweb.trafficmgt.infra.repo.NodeRepository.AbbrAndName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbbrDTOImpl implements AbbrDTO {

	private String abbr;
	private Long networkId;
	private String name;
	
	public AbbrDTOImpl(AbbrAndName model) {}
}
