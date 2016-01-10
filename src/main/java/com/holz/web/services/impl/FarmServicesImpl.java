package com.holz.web.services.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.beans.FarmBean;
import com.holz.web.beans.UserBean;
import com.holz.web.daos.FarmDao;
import com.holz.web.entities.FarmEntity;
import com.holz.web.entities.UserEntity;
import com.holz.web.services.FarmServices;

@Transactional
@Service
public class FarmServicesImpl implements FarmServices {

	@Autowired
	private FarmDao farmDao;

	@Override
	public FarmBean getFarmById(int id) {
		return convertFarm(farmDao.getFarmByFarmId(id));        
	}

	@Override
	public FarmBean getFarmByUserName(String userName) {
		return null;//convertFarm(farmDao.getFarmByFarmId(username)); 
	}


	/*
	 * TODO MOVE TO CONVERTERS
	 */
	private FarmBean convertFarm(FarmEntity e) {
		FarmBean b = new FarmBean();
		BeanUtils.copyProperties(b, e, "users");
		b.setUsers(convertUsers(e.getUsers()));
		return b;
	}

	private List<UserBean> convertUsers(Set<UserEntity> entities) {
		List<UserBean> beans = new ArrayList<UserBean>();
		for(UserEntity e : entities){
			UserBean b = new UserBean();
			String[] arr = new String[]{"farm"};
			BeanUtils.copyProperties(b, e,arr);		
			beans.add(b);
		}
		return beans;
	}
}
