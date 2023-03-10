package com.railweb.trafficmgt.domain.network;

import com.railweb.shared.domain.base.ValueObject;

import lombok.Getter;
import lombok.Setter;

public class NodeAbbr implements ValueObject<NodeAbbr> {

	@Getter @Setter
	private String value;
	
	public NodeAbbr(String abbr) {
		this.value = abbr;
	}

	@Override
	public boolean sameValueAs(NodeAbbr other) {
		// TODO Auto-generated method stub
		return false;
	}

}
