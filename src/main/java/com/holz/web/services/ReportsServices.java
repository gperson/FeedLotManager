package com.holz.web.services;

import java.util.List;

import com.holz.web.models.reports.FeedingHistory;
import com.holz.web.models.reports.PoundsGainedPerPoundDriedFood;
import com.holz.web.models.reports.SalesOverview;

public interface ReportsServices {
	
	List<PoundsGainedPerPoundDriedFood> getPoundsGainedPerPoundDriedFoods(int farmId);

	FeedingHistory getFeedingHistory(int farmId, int groupId);

	List<SalesOverview> getSalesOverview(int farmId);
	
}
