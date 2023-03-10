package com.railweb.trafficmgt.domain.network;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.ValueObject;

import lombok.Getter;
import lombok.Setter;
@Embeddable
public class NodePrefix implements ValueObject<NodePrefix> {

	@Getter
	@Setter
	private String value;
	
	public NodePrefix(String prefix) {
		this.value = prefix;
	}

	@Override
	public boolean sameValueAs(NodePrefix other) {
		// TODO Auto-generated method stub
		return false;
	}

}
