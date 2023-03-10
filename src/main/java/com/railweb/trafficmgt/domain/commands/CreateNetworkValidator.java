package com.railweb.trafficmgt.domain.commands;

import com.railweb.shared.domain.command.CommandFailure;
import com.railweb.shared.domain.command.CommandValidaion;
import com.railweb.trafficmgt.domain.commands.CreateNetwork.CreationType;

import io.vavr.control.Either;

public class CreateNetworkValidator implements CommandValidaion<CreateNetwork> {

	@Override
	public Either<CommandFailure, CreateNetwork> acceptOrReject(CreateNetwork command) {
		if(command.getType()== CreationType.SCRATCH) {
			if(command.getDto().getName() != null) {
				return Either.right(command);
			}
		return Either.left(new CommandFailure(null));
		}
		if(command.getDto().getId() != null) {
			if(command.getDto().getName() != null) {
				return Either.right(command);
			}
		}
		return Either.left(new CommandFailure(null));
	}

}
