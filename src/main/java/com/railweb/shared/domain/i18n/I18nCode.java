package com.railweb.shared.domain.i18n;

public class I18nCode {

	private final String code;
	private final Object[] args;
	
	public I18nCode(String code, Object... args) {
        this.code = code;
        this.args = args;
    }
}
