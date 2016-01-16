package com.holz.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.PackerDao;
import com.holz.web.models.Packer;
import com.holz.web.services.PackerServices;

@Transactional
@Service
public class PackerServicesImpl implements PackerServices {

	@Autowired
	private PackerDao packerDao;

	@Override
	public List<Packer> getPackers(int farmId) {
		return this.packerDao.getPackers(farmId);
	}

	@Override
	public void saveOrUpdatePacker(Packer packer,int farmId) {
		this.packerDao.saveOrUpdate(packer, farmId);
	}


}
