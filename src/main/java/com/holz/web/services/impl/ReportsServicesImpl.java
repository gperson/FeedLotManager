package com.holz.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.ReportsDao;
import com.holz.web.models.reports.PoundsGainedPerPoundDriedFood;
import com.holz.web.services.ReportsServices;

@Transactional
@Service
public class ReportsServicesImpl implements ReportsServices {

	@Autowired
	private ReportsDao reportsDao;

	@Override
	public List<PoundsGainedPerPoundDriedFood> getPoundsGainedPerPoundDriedFoods(int farmId) {
		return this.reportsDao.getPoundsGainedPerPoundDriedFoods(farmId);
	}

	

}
