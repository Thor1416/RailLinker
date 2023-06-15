package com.railweb.trafficmgt.domain.values;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.ValueObject;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.network.NodeAbbr;
import com.railweb.trafficmgt.domain.network.NodePrefix;

import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class NetworkNodeIdTuple implements ValueObject<NetworkNodeIdTuple> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6089344345459009855L;
	private NetworkId networkId;
	private NodePrefix prefix;
	private NodeAbbr abbr;
	@Override
	public boolean sameValueAs(NetworkNodeIdTuple other) {
		return networkId.sameValueAs(other.networkId) && prefix.sameValueAs(other.prefix)
				&& abbr.sameValueAs(other.abbr);
	}
	public NetworkNodeIdTuple(NetworkId netId, NodeId nodeId) {
		this.networkId = netId;
		this.abbr = nodeId.getAbbr();
		this.prefix = nodeId.getPrefix();
	}

}
