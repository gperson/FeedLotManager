package com.holz.web.services;

import java.util.List;

import com.holz.web.models.GroupedHerd;
import com.holz.web.models.GroupedHerdUpdate;

public interface GroupedHerdServices {

	void saveOrUpdateGroupedHerd(GroupedHerdUpdate groupedHerdUpdate, int farmId);

	List<GroupedHerd> getGroupedHerds(int farmId);

	boolean userHasAccessToGroupedHerd(String username, int groupedHerdId, int farmId);

	GroupedHerd getGroupedHerd(int farmId, int groupedHerdId);
	
}
