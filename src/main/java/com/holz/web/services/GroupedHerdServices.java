package com.holz.web.services;

import com.holz.web.models.GroupedHerd;
import com.holz.web.models.GroupedHerdUpdate;

public interface GroupedHerdServices {

	void saveOrUpdateGroupedHerd(GroupedHerdUpdate groupedHerdUpdate, int farmId);
	
}
