package com.holz.web.services;

import java.util.List;

import com.holz.web.models.Herd;

public interface HerdServices {

	List<Herd> getHerds(int farmId);
	
	void saveOrUpdateHerd(Herd herd,int farmId);
	
}
