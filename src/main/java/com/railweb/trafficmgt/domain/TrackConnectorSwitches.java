package com.railweb.trafficmgt.domain;

import java.util.Optional;

import com.railweb.shared.domain.items.ItemSet;
import com.railweb.trafficmgt.domain.network.NodeTrack;

public interface TrackConnectorSwitches extends ItemSet<TrackConnectorSwitch> {

	default Optional<TrackConnectorSwitch> getForNodeTrack(NodeTrack nodeTrack){
		return this.find(s-> s.getNodeTrack() == nodeTrack);
	}
	
	default boolean containsNodeTrack(NodeTrack nodeTrack) {
		return this.getForNodeTrack(nodeTrack).isPresent();
	}
}
