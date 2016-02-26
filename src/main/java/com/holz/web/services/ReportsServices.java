package com.holz.web.services;

import java.util.List;

import com.holz.web.models.reports.PoundsGainedPerPoundDriedFood;

public interface ReportsServices {
	
	List<PoundsGainedPerPoundDriedFood> getPoundsGainedPerPoundDriedFoods(int farmId);
	
}
