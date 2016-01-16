package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.Supplier;

public interface SupplierDao {

	List<Supplier> getSuppliers(int farmId);
	
	void saveOrUpdate(Supplier supplier, int farmId);
	
}
