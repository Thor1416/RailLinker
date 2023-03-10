package com.railweb.trafficmgt.domain.commands;


import com.railweb.shared.domain.command.Command;
import com.railweb.trafficmgt.dto.NetworkDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateNetwork implements Command {
	
	private NetworkDTO dto;
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
	
}
