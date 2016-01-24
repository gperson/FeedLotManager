package com.holz.web.daos;

import com.holz.web.models.GroupedHerd;

public interface GroupedHerdDao {

	GroupedHerd getGroupedHerdForLocale(int localeId, int farmId);

	void updateGroupedHerdLocationForHerd(GroupedHerd group);

	GroupedHerd saveNewGroupedHerd(GroupedHerd group);

	void deleteGroupedHerd(int groupHerdId);
	
}
