package com.railweb.shared.domain.i18n;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

/** String that can be translated 
 */
public interface TranslatedString {

	/**
	 * @return default text
	 */
	String getDefaultString();
	
	default boolean isDefaultStringEmpty() {
		String defaultString = getDefaultString();
		return defaultString == null || defaultString.equals("");
	}
	
	default TranslatedString getNullIfEmpty() {
		return isDefaultStringEmpty() ? null :this;
	}
	/**
     * Optional operation - can return empty list if the information is not available
     *
     * @return collection of locales of this string
     */
	default Collection<Locale> getLocales(){
		return Collections.emptyList();
	}
	
	default String translate() {
		return translate(Locale.getDefault());
	}
	
	String translate(Locale locale);

    /**
     * @param languageTag locale (tag)
     * @return text for given locale of default text if the locale is not present
     */
    default String translateForTag(String languageTag) {
        return translate(languageTag == null ? null : Locale.forLanguageTag(languageTag));
    }
}
