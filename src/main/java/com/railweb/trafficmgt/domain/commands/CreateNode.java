package com.railweb.trafficmgt.domain.commands;

import java.util.Optional;
import java.util.UUID;

import com.railweb.shared.domain.command.Command;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.network.Network;
import com.railweb.trafficmgt.dto.NetworkDTO;
import com.railweb.trafficmgt.dto.NodeDTO;

import lombok.Data;

@Data
public class CreateNode implements Command {

	private Optional<NodeId> nodeId;
	private Optional<Network> network;
	private	Optional<UUID> networkId;
	private Optional<NodeDTO> nodeDTO;
	private Optional<NetworkDTO> networkDTO;
	
	public CreateNode(NodeId nodeId, Network network) {
		this.nodeId = Optional.of(nodeId);
		this.network = Optional.of(network);
	}

	public CreateNode(NodeId nodeId, NetworkDTO network) {
		this.nodeId = Optional.of(nodeId);
		this.networkDTO = Optional.of(network);
	}

}
