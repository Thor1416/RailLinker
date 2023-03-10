package com.railweb.trafficmgt.domain.values;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.ValueObject;
import com.railweb.trafficmgt.domain.network.NodeAbbr;
import com.railweb.trafficmgt.domain.network.NodePrefix;

import lombok.Data;

@Data
@Embeddable
public class NodeIdTuple implements ValueObject<NodeIdTuple> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2897855875937721715L;
	private NodeAbbr abbr;
	private NodePrefix prefix;
	@Override
	public boolean sameValueAs(NodeIdTuple other) {
		return abbr.sameValueAs(other.abbr) && prefix.sameValueAs(other.prefix);
	}
	public NodeIdTuple(NodeAbbr abbr2, NodePrefix prefix2) {
		// TODO Auto-generated constructor stub
	}
}
