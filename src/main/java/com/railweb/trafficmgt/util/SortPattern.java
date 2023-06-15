package com.railweb.trafficmgt.util;

import java.util.LinkedList;
import java.util.List;

public class SortPattern {

	private String pattern;
	private List<SortPatternGroup> groups;
	
	public SortPattern(String pattern) {
		this.pattern = pattern;
		this.groups = new LinkedList<>();
	}

	public String getPattern() {
		return pattern;
	}

	public List<SortPatternGroup> getGroups() {
		return groups;
	}
	
	
	
}
