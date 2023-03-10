package com.railweb.trafficmgt.dto.commands;


import com.railweb.shared.domain.command.Command;
import com.railweb.trafficmgt.dto.NetworkDTO;

import lombok.Getter;

@Getter
public class CreateNetworkCommand implements Command {
	
	private NetworkDTO argument;
	private CreationType type;

	public enum CreationType{
		/**
		 * Copy the whole network inclusive time tables, trains routes etc, 
		 */
		COPY,
		/** Create a similar network, but with no nodes,lines or routes
		 * 
		 */
		BLANK,
		/**
		 * 
		 */
		SCRATCH,
		/**
		 * Copy time tables etc
		 * 
		 */
		DEEP
	}



	public CreateNetworkCommand(NetworkDTO arg, CreationType type) {
		this.argument = arg;
		this.type = type;
	}
}
