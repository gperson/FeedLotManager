package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.Herd;

public interface HerdDao {

	List<Herd> getHerds(int farmId);
	
	void saveOrUpdate(Herd herd, int farmId);
	
}
