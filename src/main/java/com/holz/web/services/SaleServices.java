package com.holz.web.services;

import java.util.List;

import com.holz.web.models.Sale;

public interface SaleServices {

	List<Sale> getAllSales(int farmId);

	boolean saveOrUpdateSale(Sale sale, int farmId);

}
