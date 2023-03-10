package com.railweb.trafficmgt.dto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.railweb.shared.web.BaseDTO;

import lombok.Data;

@Data
public class PageDTO<T extends BaseDTO> {

	Long totalElements;
	int totalPages;
	int number;
	int size;
	List<T> contents;
	Sort sort;

	public PageDTO(Page<T> page) {
		this.contents = page.getContent();
		this.totalElements =page.getTotalElements();
		this.size = page.getSize();
		this.number = page.getNumber();
		this.sort = page.getSort();
	}
}
