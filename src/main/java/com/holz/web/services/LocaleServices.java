package com.holz.web.services;

import java.util.List;

import com.holz.web.models.Locale;

public interface LocaleServices {

	List<Locale> getLocalesAndGroupedHerd(int farmId);
	
	void saveOrUpdateLocale(Locale locale,int farmId);

	boolean hasAccessToLocale(int farmId, Locale locale);

	void enableDisableLocale(Locale locale, int farmId);
	
}
