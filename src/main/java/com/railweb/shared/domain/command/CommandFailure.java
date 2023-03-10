package com.railweb.shared.domain.command;

import java.util.Set;

import com.railweb.shared.domain.i18n.I18nCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CommandFailure {

	@Getter
	private final Set<I18nCode> codes;
}
