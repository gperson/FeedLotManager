package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.Locale;

public interface LocaleDao {

	List<Locale> getLocales(int farmId);
	
	void saveOrUpdate(Locale locale, int farmId);

	void enableDisableLocale(Locale locale, int farmId);
	
}
