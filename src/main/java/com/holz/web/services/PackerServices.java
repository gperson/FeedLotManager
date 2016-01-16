package com.holz.web.services;

import java.util.List;

import com.holz.web.models.Packer;

public interface PackerServices {

	List<Packer> getPackers(int farmId);
	
	void saveOrUpdatePacker(Packer packer,int farmId);
	
}
