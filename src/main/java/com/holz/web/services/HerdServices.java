package com.holz.web.services;

import java.util.List;

import com.holz.web.models.GroupedHerdUpdate;
import com.holz.web.models.Herd;

public interface HerdServices {

	List<Herd> getAllHerds(int farmId);
	
	List<Herd> getHerdsForGroupedLocal(int farmId, int groupedLocale);

	GroupedHerdUpdate getOrphanHerds(int farmId);
	
	void saveOrUpdateHerd(Herd herd,int farmId);

	void updateGroupIds(List<Herd> herds, int groupId, int farmId);
	
}
