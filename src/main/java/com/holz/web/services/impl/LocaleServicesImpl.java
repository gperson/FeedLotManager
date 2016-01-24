package com.holz.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.GroupedHerdDao;
import com.holz.web.daos.HerdDao;
import com.holz.web.daos.LocaleDao;
import com.holz.web.daos.SaleDao;
import com.holz.web.models.GroupedHerd;
import com.holz.web.models.Locale;
import com.holz.web.services.LocaleServices;

@Transactional
@Service
public class LocaleServicesImpl implements LocaleServices {

	@Autowired
	private LocaleDao localeDao;
	
	@Autowired
	private HerdDao herdDao;
	
	@Autowired
	private SaleDao saleDao;
	
	@Autowired
	private GroupedHerdDao groupedHerdDao;

	@Override
	public List<Locale> getLocalesAndGroupedHerd(int farmId) {
		List<Locale> locales = this.localeDao.getLocales(farmId);
		for(Locale l : locales){
			GroupedHerd group = this.groupedHerdDao.getGroupedHerdForLocale(l.getId(), farmId);			
			if(group != null){
				group.setHerds(this.herdDao.getHerdsForGroupedHerd(farmId, group.getId()));
				group.setSales(this.saleDao.getSalesForGroupHerd(farmId, group.getId()));
			}
			l.setGroupedHerd(group);
		}
		return locales;
	}

	@Override
	public void saveOrUpdateLocale(Locale locale,int farmId) {
		this.localeDao.saveOrUpdate(locale, farmId);
	}

	@Override
	public void enableDisableUser(Locale locale, int farmId) {
		this.localeDao.enableDisableLocale(locale, farmId);
	}

	@Override
	public boolean hasAccessToLocale(int farmId, Locale locales) {
		return this.localeDao.hasAccessToLocale(farmId,locales);
	}

}
