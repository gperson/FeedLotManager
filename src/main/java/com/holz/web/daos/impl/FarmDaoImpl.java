package com.holz.web.daos.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.holz.web.daos.FarmDao;
import com.holz.web.entities.FarmEntity;

@Repository
public class FarmDaoImpl implements FarmDao {
	
	@Autowired
    private HibernateTemplate hibernateTemplate;
	
	@Override
    public FarmEntity getFarmByFarmId(int id) {
        return (FarmEntity) this.hibernateTemplate.get(FarmEntity.class,id);
    }

}