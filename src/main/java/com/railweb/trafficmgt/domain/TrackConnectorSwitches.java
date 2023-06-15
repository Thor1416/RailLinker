package com.railweb.trafficmgt.domain;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.Iterables;
import com.railweb.trafficmgt.domain.network.NodeTrack;

public interface TrackConnectorSwitches extends Set<TrackConnectorSwitch> {

	default Optional<TrackConnectorSwitch> getForNodeTrack(NodeTrack nodeTrack){
		return this.find(s-> s.getNodeTrack().id().sameValueAs(nodeTrack.id()));
	}
	
	default boolean containsNodeTrack(NodeTrack nodeTrack) {
		return this.getForNodeTrack(nodeTrack).isPresent();
	}

	default Optional<TrackConnectorSwitch> find(Predicate<TrackConnectorSwitch> predicate){
		return Iterables.tryFind(this,predicate::test).toJavaUtil();
	}
}
