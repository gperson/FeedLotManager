package com.holz.web.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.beans.FarmBean;
import com.holz.web.daos.FarmDao;
import com.holz.web.entities.FarmEntity;
import com.holz.web.services.FarmServices;

@Transactional
@Service
public class FarmServicesImpl implements FarmServices {
	
	@Autowired
    private FarmDao farmDao;

    @Override
    public FarmBean getFarmById(int id) {
    	FarmEntity e = farmDao.getFarmByFarmId(id);
    	FarmBean b = new FarmBean();
    	b.setFarmName(e.getFarmName());        
    	return b;
    }
}
