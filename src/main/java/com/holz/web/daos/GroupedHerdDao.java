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

	GroupedHerd getGroupedHerdForFeeding(int feedindId, int farmId);

	void updateGroupedHerdSoldStatus(GroupedHerd group);

	GroupedHerd getGroupedHerd(int groupId);

	GroupedHerd getGroupedHerdForHerd(int herdId, int farmId);
	
}
