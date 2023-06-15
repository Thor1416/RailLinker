package com.railweb.trafficmgt.domain.network;

import java.util.Set;

import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ids.TrackId;
import com.railweb.trafficmgt.domain.train.Platform;

public class NodeTrack extends Track<NodeId> {

	public NodeTrack(NodeId ownerId, String tracknumber) {
		super(ownerId, tracknumber);
	}


	private Set<Platform> platforms; 

	public boolean isPlatform() {
		return platforms.isEmpty();
	}

	@Override
	public TrackId getId() {
		// TODO Auto-generated method stub
		return null;
	}


}
