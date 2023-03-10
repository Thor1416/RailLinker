package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.railweb.shared.anno.ValueObject;
import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.trafficmgt.domain.network.NodeAbbr;
import com.railweb.trafficmgt.domain.network.NodePrefix;
import com.railweb.trafficmgt.domain.values.NetworkNodeIdTuple;

@ValueObject
@Embeddable
public class NetworkNodeId extends DomainObjectId<NetworkNodeIdTuple>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3523227902224070523L;

	public NetworkNodeId(NetworkNodeIdTuple id) {
		super(id);
	}


	@Override
	protected void assignId() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean sameValueAs(DomainObjectId<NetworkNodeIdTuple> other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UUID toUUID() {
		// TODO Auto-generated method stub
		return null;
	}

	public NodeAbbr abbr() {
		return id.getNodeId().getAbbr();
	}

	public NodePrefix prefix() {
		return id.getNodeId().getPrefix();
	}
	
	public NetworkId network() {
		return id.getNetworkId();
	}
}
