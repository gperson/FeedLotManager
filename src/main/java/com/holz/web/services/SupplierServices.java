package com.holz.web.services;

import java.util.List;
import com.holz.web.models.Supplier;

public interface SupplierServices {

	List<Supplier> getSuppliers(int farmId);
	
	void saveOrUpdateSupplier(Supplier supplier,int farmId);
	
}
