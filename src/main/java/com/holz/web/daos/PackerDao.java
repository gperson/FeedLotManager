package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.Packer;

public interface PackerDao {

	List<Packer> getPackers(int farmId);
	
	void saveOrUpdate(Packer packer, int farmId);
	
}
