package com.holz.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.HerdDao;
import com.holz.web.daos.SaleDao;
import com.holz.web.models.Sale;
import com.holz.web.services.SaleServices;

@Transactional
@Service
public class SaleServicesImpl implements SaleServices {

	@Autowired
	private SaleDao saleDao;
	
	@Autowired
	private HerdDao herdDao;

	@Override
	public List<Sale> getAllSales(int farmId) {
		List<Sale> sales = this.saleDao.getAllSales(farmId);
		for(Sale s : sales){
			this.herdDao.getHerdsForGroupedHerd(farmId, s.getGroupedHerd().getId());
			s.getGroupedHerd().setHerds(this.herdDao.getHerdsForGroupedHerd(farmId, s.getGroupedHerd().getId()));
		}
		return sales;
	}

	@Override
	public void saveOrUpdateSale(Sale sale,int farmId) {
		this.saleDao.saveOrUpdate(sale, farmId);
	}
	
}
