package com.railweb.trafficmgt.domain;

import java.util.Optional;

import com.railweb.shared.domain.base.IdentifiableDomainObject;
import com.railweb.shared.domain.util.Visitable;
import com.railweb.trafficmgt.domain.ids.TrackConnectorId;
import com.railweb.trafficmgt.domain.network.Node;
import com.railweb.trafficmgt.domain.network.NodeTrack;

public interface TrackConnector extends IdentifiableDomainObject<TrackConnectorId>, Visitable<TimetableVisitor> {

	Node getNode();
	
	Node.Side getOrientation();
	
	void setOrientation(Node.Side orientation);

	TrackConnectorSwitches getSwitches();
	
	default Optional<NodeTrack> getStraightNodeTrack(){
		return  getSwitches().find(TrackConnectorSwitch::isStraight)
				.map(TrackConnectorSwitch::getNodeTrack);
	}
}
