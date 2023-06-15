package com.railweb.trafficmgt.domain.values;

import javax.annotation.Nonnull;
import javax.persistence.Embeddable;
import javax.persistence.Id;

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
	@Nonnull
	@Id
	private final NodeAbbr abbr;
	@Nonnull
	@Id
	private final NodePrefix prefix;
	@Override
	public boolean sameValueAs(NodeIdTuple other) {
		return abbr.sameValueAs(other.abbr) && prefix.sameValueAs(other.prefix);
	}
	public NodeIdTuple(NodeAbbr abbr, NodePrefix prefix) {
		this.abbr = abbr;
		this.prefix = prefix;
	}
}
