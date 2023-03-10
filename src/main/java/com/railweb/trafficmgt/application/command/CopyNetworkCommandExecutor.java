package com.railweb.trafficmgt.application.command;

import java.util.concurrent.CompletionStage;

import com.railweb.shared.domain.command.CommandFailure;
import com.railweb.shared.domain.command.CommandHandler;
import com.railweb.trafficmgt.domain.commands.CopyNetwork;
import com.railweb.trafficmgt.domain.events.NetworkCreated;
import com.railweb.trafficmgt.domain.ids.NetworkId;

import io.vavr.control.Either;

public class CopyNetworkCommandExecutor implements CommandHandler<CopyNetwork,NetworkCreated,NetworkId>{

	@Override
	public CompletionStage<Either<CommandFailure, NetworkCreated>> execute(CopyNetwork command, NetworkId id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandFailure orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

}
