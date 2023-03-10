package com.railweb.trafficmgt.application.orc;

import com.railweb.trafficmgt.application.command.CreateNodeCommand;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.dto.NodeDTO;

public interface NodeOrchestrator {

	NodeDTO createNode(NetworkId netId, CreateNodeCommand command);
	
	NodeDTO updateNode(NetworkId netId, Long nodeId, NodeDTO nodedto);

	Boolean deleteNode(NetworkId netId, Long nodeId);
	
	NodeDTO retrieveNode(NetworkId netId, String abbr);

}
