package com.holz.web.daos;

import com.holz.web.models.GroupedHerd;

public interface GroupedHerdDao {

	GroupedHerd getGroupedHerdForLocale(int localeId, int farmId);

	GroupedHerd saveOrUpdateGroupedHerd(GroupedHerd group);
	
}
