package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.reports.PoundsGainedPerPoundDriedFood;
import com.holz.web.models.reports.SalesOverview;

public interface ReportsDao {

	List<PoundsGainedPerPoundDriedFood> getPoundsGainedPerPoundDriedFoods(int farmId);

	List<SalesOverview> getSalesOverview(int farmId);
	
}
