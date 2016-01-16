package com.holz.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.SupplierDao;
import com.holz.web.models.Supplier;
import com.holz.web.services.SupplierServices;

@Transactional
@Service
public class SupplierServicesImpl implements SupplierServices {

	@Autowired
	private SupplierDao supplierDao;

	@Override
	public List<Supplier> getSuppliers(int farmId) {
		return this.supplierDao.getSuppliers(farmId);
	}

	@Override
	public void saveOrUpdateSupplier(Supplier supplier,int farmId) {
		this.supplierDao.saveOrUpdate(supplier, farmId);
	}


}
