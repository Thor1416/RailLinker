package com.railweb.shared.domain.i18n;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

public interface LocalizedString extends TranslatedString {

	default Collection<Locale> getLocales() {
		return FluentIterable.from(getLocalizedStrings()).transform(StringWithLocale::getLocale).toList();
	}

	Collection<StringWithLocale> getLocalizedStrings();

	default String getLocalizedString(final Locale locale) {
		StringWithLocale stringWithLocale = this.getLocalizedStringWithLocale(locale);
		return stringWithLocale == null ? null : stringWithLocale.getString();
	}

	default StringWithLocale getLocalizedStringWithLocale(final Locale locale) {
		if (locale != null) {
			Locale languageLocale = getOnlyLanguageLocale(locale);
			for (StringWithLocale localizedString : getLocalizedStrings()) {
				if (languageLocale.equals(localizedString.getLocale())) {
					return localizedString;
				}
			}
		}
		return null;
	}

	static Locale getOnlyLanguageLocale(Locale locale) {
		if(locale.getCountry() == null) {
			return locale;
		}else {
			return Locale.forLanguageTag(locale.getLanguage());
		}
	}


	@Override
	default String translate(Locale locale) {
		String translatedString = this.getDefaultString();
		return translatedString == null ? getDefaultString() : translatedString;
	}

	static class Builder {

		private String defaultString;
		private List<StringWithLocale> strings;

		private Builder() {
		}

		public Builder setDefaultString() {
			this.defaultString = defaultString;
			return this;
		}

		public Builder addStringWithLocale(String string, Locale locale) {
			this.addString(string, locale);
			return this;
		}

		public Builder addStringWithLocale(String string, String locale) {
			this.addString(string, Locale.forLanguageTag(locale));
			return this;
		}

		public Builder addStringWithLocale(StringWithLocale stringWithLocale) {
			this.addString(stringWithLocale.getString(), stringWithLocale.getLocale());
			return this;
		}
		
		public Builder addAllStringWithLocale(Collection<? extends StringWithLocale> collection) {
            for (StringWithLocale item : collection) {
                this.addString(item.getString(), item.getLocale());
            }
            return this;
        }
		private void addString(String string, Locale locale) {
			if(string == null || locale == null){
				throw new IllegalArgumentException("Parameters cannot be null;");	
			}
			if(strings == null) {
				strings= new ArrayList<>();
			}
			strings.add(new StringWithLocale(string,getOnlyLanguageLocale(locale)));
			
		}
		
		public LocalizedString build() {
			if(defaultString == null) {
				throw new IllegalStateException("Default strinng is missing");
			}
			return new LocalizedStringImpl(defaultString, ImmutableList.copyOf(getSortedStrings()));
		}
		
		private Collection<StringWithLocale> getSortedStrings(){
			if(strings == null) {
				return Collections.emptyList();
			}else {
				Collections.sort(strings,(s1,s2)->s1.getLocale().toLanguageTag().compareTo(s2.getLocale().toLanguageTag()));
				return strings;
			}
		}
		
	}
}
