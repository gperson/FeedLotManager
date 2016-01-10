package com.holz.web.services;

import com.holz.web.models.Farm;
import com.holz.web.models.enums.FarmLoadOption;

public interface FarmServices {

	Farm getFarmByUserName(String userName, FarmLoadOption loadOption);
	
}
