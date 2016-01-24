package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.Herd;

public interface HerdDao {

	List<Herd> getAllHerds(int farmId);
	
	void saveOrUpdate(Herd herd, int farmId);

	List<Herd> getOrphanHerds(int farmId);

	void updateGroupIds(List<Herd> herds, int groupId, int farmId);

	List<Herd> getHerds(List<Integer> ids, int farmId);

	List<Herd> getHerdsForGroupedHerd(int farmId, int groupedHerdId);
	
}
