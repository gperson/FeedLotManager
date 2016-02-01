package com.holz.web.daos;

import com.holz.web.models.Farm;

public interface FarmDao {

	Farm getFarmByUserName(String userName);
	
}
