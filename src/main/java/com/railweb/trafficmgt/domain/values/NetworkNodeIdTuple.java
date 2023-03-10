package com.railweb.trafficmgt.domain.values;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.ValueObject;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.ids.NodeId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class NetworkNodeIdTuple implements ValueObject<NetworkNodeIdTuple> {
	
	private NetworkId networkId;
	private NodeId nodeId;
	@Override
	public boolean sameValueAs(NetworkNodeIdTuple other) {
		return nodeId.sameValueAs(other.nodeId) && networkId.sameValueAs(other.networkId);
	}

}
