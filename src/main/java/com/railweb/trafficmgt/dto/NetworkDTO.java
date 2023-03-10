package com.railweb.trafficmgt.dto;

import java.time.OffsetDateTime;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.railweb.shared.web.BaseDTO;
import com.railweb.trafficmgt.domain.network.Network;

import lombok.Data;

@Data
public class NetworkDTO implements BaseDTO {

	public NetworkDTO(Network newNet) {
		// TODO Auto-generated constructor stub
	}
	@JsonProperty(required=true)
	@NotEmpty
	@NotBlank
	private Long id;
	@JsonProperty(required=true)
	@NotEmpty
	@NotBlank
	private String name;
	private String code;
	private Long owner;
	private OffsetDateTime validFrom;
	private OffsetDateTime validTo;

	private Set<Long> timetables;
	private Set<Long> lineclasses;
	private Set<Long> regions;
	private Set<Long> trainRoutes;
	private Set<Long> trackSystems;
	private Set<Long> networkNodes;
	private Set<Long> lines;

}
