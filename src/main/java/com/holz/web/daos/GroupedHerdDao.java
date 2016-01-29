package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.GroupedHerd;

public interface GroupedHerdDao {

	GroupedHerd getGroupedHerdForLocale(int localeId, int farmId);

	void updateGroupedHerdLocationForHerd(GroupedHerd group);

	GroupedHerd saveNewGroupedHerd(GroupedHerd group);

	void deleteGroupedHerd(int groupHerdId);

	List<GroupedHerd> getGroupedHerds(int farmId);

	boolean userHasAccessToGroupedHerd(String username, int groupedHerdId, int farmId);
	
}
