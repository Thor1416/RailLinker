package com.railweb.shared.domain.command;

import java.util.concurrent.CompletionStage;

import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.events.DomainEvent;

import io.vavr.control.Either;

public interface CommandHandler<C extends Command, E extends DomainEvent, ID extends DomainObjectId<?>> {

	CompletionStage<Either<CommandFailure,E>> execute(C command, ID id);

	CommandFailure orElseThrow(Object object);

}
