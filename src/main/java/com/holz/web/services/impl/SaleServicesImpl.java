package com.holz.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.GroupedHerdDao;
import com.holz.web.daos.HerdDao;
import com.holz.web.daos.SaleDao;
import com.holz.web.models.GroupedHerd;
import com.holz.web.models.Sale;
import com.holz.web.services.SaleServices;

@Transactional
@Service
public class SaleServicesImpl implements SaleServices {

	@Autowired
	private SaleDao saleDao;
	
	@Autowired
	private HerdDao herdDao;
	
	@Autowired
	private GroupedHerdDao groupedHerdDao;

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
	public boolean saveOrUpdateSale(Sale sale,int farmId) {
		GroupedHerd group = this.groupedHerdDao.getGroupedHerd(sale.getGroupedHerd().getId());
		group.setHerds(this.herdDao.getHerdsForGroupedHerd(farmId, group.getId()));
		group.setSales(this.saleDao.getSalesForGroupHerd(farmId, group.getId()));
		
		//Incase we are updating the quantity we need set the new quantity before 'getCount'
		int oldSaleCount = 0;
		if(sale.getId() != 0){
			for(Sale s : group.getSales()){
				if(s.getId() == sale.getId()){
					oldSaleCount = s.getQuantity();
					break;
				}
			}
		}
		
		if((group.getCount()+oldSaleCount) >= sale.getQuantity()){
			this.saleDao.saveOrUpdate(sale, farmId);
			
			//If the last livestock is sold from the location remove herd from local, i.e set to null	
			if(((group.getCount()+oldSaleCount) - sale.getQuantity()) == 0){
				group.getLocale().setId(-1);
				group.setSold(true);
				this.groupedHerdDao.updateGroupedHerdLocationForHerd(group);
				this.groupedHerdDao.updateGroupedHerdSoldStatus(group);
			}
			return true;
		}
		return false;
	}
	
}
