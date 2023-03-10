package com.railweb.trafficmgt.domain.commands;

import java.util.List;

import com.railweb.shared.web.SpecSearchCriteria;

import lombok.Data;

@Data
public class NodeSearchCommandDTO {

	private Integer page;
	private Integer size;
	private String sortDirection;
	private String sortField;
	private List<SpecSearchCriteria> params;
}
