package com.railweb.shared.domain.command;

import io.vavr.control.Either;

public interface CommandValidaion<C extends Command> {

	Either<CommandFailure, C> acceptOrReject (C command);
}
