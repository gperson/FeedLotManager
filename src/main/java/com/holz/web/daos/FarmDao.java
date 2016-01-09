package com.holz.web.daos;

import com.holz.web.entities.FarmEntity;

public interface FarmDao {

	FarmEntity getFarmByFarmId(int id);
	
}
