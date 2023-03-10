package com.railweb.trafficmgt.domain.network;

import java.util.Set;

import com.railweb.trafficmgt.domain.ids.TrackId;
import com.railweb.trafficmgt.domain.train.Platform;

public class NodeTrack extends Track {

	public NodeTrack(Node owner, String tracknumber) {
		super(owner, tracknumber);
	}


	private Set<Platform> platforms; 

	public boolean isPlatform() {
		return platforms.isEmpty();
	}

	public Node getOwnerAsNode() {
		return (Node) owner;
	}

	@Override
	public TrackId getId() {
		// TODO Auto-generated method stub
		return null;
	}


}
