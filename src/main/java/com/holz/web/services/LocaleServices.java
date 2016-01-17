package com.holz.web.services;

import java.util.List;

import com.holz.web.models.Locale;

public interface LocaleServices {

	List<Locale> getLocales(int farmId);
	
	void saveOrUpdateLocale(Locale locale,int farmId);

	void enableDisableUser(Locale locale, int farmId);
	
}
