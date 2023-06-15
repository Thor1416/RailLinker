package com.railweb.trafficmgt.domain;

import com.railweb.trafficmgt.domain.network.NodeTrack;

public interface TrackConnectorSwitch{

	NodeTrack getNodeTrack();
	void setNodeTrack(NodeTrack nodeTrack);
	
	boolean isStraight();
	void setStraight(boolean straight);
}
