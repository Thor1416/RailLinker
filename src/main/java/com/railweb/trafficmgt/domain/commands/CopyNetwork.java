package com.railweb.trafficmgt.domain.commands;

import com.railweb.shared.domain.command.Command;
import com.railweb.trafficmgt.domain.ids.NetworkId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CopyNetwork implements Command{

	private final NetworkId originNetId;
	private final String newName;
}
