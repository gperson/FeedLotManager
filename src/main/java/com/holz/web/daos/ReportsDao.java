package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.reports.PoundsGainedPerPoundDriedFood;

public interface ReportsDao {

	List<PoundsGainedPerPoundDriedFood> getPoundsGainedPerPoundDriedFoods(int farmId);
	
}
