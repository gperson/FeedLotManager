package com.holz.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.HerdDao;
import com.holz.web.daos.LocaleDao;
import com.holz.web.models.Locale;
import com.holz.web.services.LocaleServices;

@Transactional
@Service
public class LocaleServicesImpl implements LocaleServices {

	@Autowired
	private LocaleDao localeDao;
	
	@Autowired
	private HerdDao herdDao;

	@Override
	public List<Locale> getLocales(int farmId) {
		List<Locale> locales = this.localeDao.getLocales(farmId);
		for(Locale l : locales){
			l.setHerds(this.herdDao.getHerdsForLocale(farmId,l.getId()));
		}
		return locales;
	}

	@Override
	public void saveOrUpdateLocale(Locale locale,int farmId) {
		if(locale.getId() == 0){
			locale.setLivestockCount(0);
		}
		this.localeDao.saveOrUpdate(locale, farmId);
	}

	@Override
	public void enableDisableUser(Locale locale, int farmId) {
		this.localeDao.enableDisableLocale(locale, farmId);
	}
}
