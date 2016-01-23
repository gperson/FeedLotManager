package com.holz.web.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.GroupedHerdDao;
import com.holz.web.daos.HerdDao;
import com.holz.web.models.GroupedHerd;
import com.holz.web.models.GroupedHerdUpdate;
import com.holz.web.models.Herd;
import com.holz.web.models.Locale;
import com.holz.web.services.GroupedHerdServices;

@Transactional
@Service
public class GroupedHerdServicesImpl implements GroupedHerdServices {

	@Autowired
	private GroupedHerdDao groupedHerdDao;
	
	@Autowired
	private HerdDao herdDao;

	@Override
	public void saveOrUpdateGroupedHerd(GroupedHerdUpdate groupUpdate, int farmId) {
		GroupedHerd group = new GroupedHerd();
		group.setId(groupUpdate.getGroupedId());
		Locale l = new Locale();
		l.setId(groupUpdate.getLocaleId());
		group.setLocale(l);
		
		if(group.getId() == 0){
			//Create group if id = 0
			group = this.groupedHerdDao.saveOrUpdateGroupedHerd(group);
		}
		
		//Update adding new herds/group to group
		for(String h : groupUpdate.getCurrent()){
			List<Herd> herds = new ArrayList<Herd>();
			for(String herdId : h.replace(" ", "").split(",")){
				Herd herd = new Herd();
				herd.setId(Integer.parseInt(herdId));
				herds.add(herd);
			}
			group.setHerds(herds);
			this.herdDao.updateGroupIds(group.getHerds(),group.getId(),farmId);
		}
		
		//Update orphans grouped herds to null location
		List<Herd> orphans = new ArrayList<Herd>();
		for(String o : groupUpdate.getOrphans()){
			Herd h = new Herd();
			h.setId(Integer.parseInt(o.split(",")[0]));
			orphans.add(h);
			group.setHerds(orphans);
			group.getLocale().setId(-1);
			this.groupedHerdDao.saveOrUpdateGroupedHerd(group);
			orphans = new ArrayList<Herd>();
		}
	}

}
