package com.holz.web.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.FarmDao;
import com.holz.web.models.Farm;
import com.holz.web.services.FarmServices;

@Transactional
@Service
public class FarmServicesImpl implements FarmServices {

	@Autowired
	private FarmDao farmDao;

	@Override
	public Farm getFarmByUserName(String userName) {
		return farmDao.getFarmByUserName(userName);
	}

}
