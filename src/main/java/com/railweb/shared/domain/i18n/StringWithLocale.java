package com.railweb.shared.domain.i18n;

import java.util.Locale;

import javax.persistence.Basic;
import javax.persistence.Entity;

import com.railweb.shared.domain.base.AbstractEntity;

import lombok.Data;

@Data
@Entity
public class StringWithLocale extends AbstractEntity<StringWithLocaleId>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6975795076343633981L;
	public StringWithLocale(String string2, Locale onlyLanguageLocale) {
		// TODO Auto-generated constructor stub
	}
	
	@Basic(optional=false)
	private Locale locale;
	private String string;
}
