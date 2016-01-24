package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.Sale;

public interface SaleDao {

	void saveOrUpdate(Sale Sale, int farmId);

	List<Sale> getSalesForGroupHerd(int farmId, int groupId);

	List<Sale> getAllSales(int farmId);

}
