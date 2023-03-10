package com.railweb.shared.domain.i18n;

import java.util.List;
import java.util.Locale;

import com.google.common.collect.Iterables;
import com.railweb.shared.domain.base.AbstractEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LocalizedStringImpl extends AbstractEntity<LocalizedStringId> implements LocalizedString {
	
	private String defaultString;
	private List<StringWithLocale> localizedStrings;
	
	LocalizedStringImpl(String defaultString, List<StringWithLocale> localizedStrings){
		this.defaultString = defaultString;
		this.localizedStrings = localizedStrings;
	}

	@Override
	public StringWithLocale getLocalizedStringWithLocale(Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

    public String toCompleteString() {
        return String.format("i18n:%s[%s]", defaultString, String.join(",", Iterables.transform(localizedStrings,
                item -> String.format("%s=%s", item.getLocale().toLanguageTag(), item.getString()))));
    }

}
