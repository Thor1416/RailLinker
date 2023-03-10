package com.railweb.trafficmgt.dto.pages;

import java.util.List;

import com.railweb.trafficmgt.dto.NetworkDTO;

import lombok.Data;

@Data
public class NetworkPageDTO {

	private Integer pageNo;
	private Integer totalPages;
	private List<NetworkDTO> content;
}
