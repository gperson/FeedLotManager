package com.holz.web.daos;

import com.holz.web.models.Farm;
import com.holz.web.models.enums.FarmLoadOption;

public interface FarmDao {

	Farm getFarmByUserName(String userName, FarmLoadOption loadOption);
	
}
