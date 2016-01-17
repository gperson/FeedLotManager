package com.holz.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.HerdDao;
import com.holz.web.models.Herd;
import com.holz.web.services.HerdServices;

@Transactional
@Service
public class HerdServicesImpl implements HerdServices {

	@Autowired
	private HerdDao herdDao;

	@Override
	public List<Herd> getHerds(int farmId) {
		return this.herdDao.getHerds(farmId);
	}

	@Override
	public void saveOrUpdateHerd(Herd herd,int farmId) {
		this.herdDao.saveOrUpdate(herd, farmId);
	}


}
