package com.holz.web.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
		Locale l = new Locale();
		l.setId(groupUpdate.getLocaleId());
		group.setLocale(l);

		//Get all herds that are going to be in the group
		List<Integer> ids = new ArrayList<Integer>();
		for(String h : groupUpdate.getCurrent()){
			for(String herdId : h.trim().split(",")){
				ids.add(Integer.parseInt(herdId.replace("\n", "").replace("\t", "").replace("\r", "").replace(" ", "")));
			}
		}
		if(ids.size() > 0){
			group.setHerds(this.herdDao.getHerds(ids,farmId));
		} else {
			group.setHerds(new ArrayList<Herd>());
		}

		//Determine if these herds already belonged to a group
		//If they belonged to a group and merging with a group, take oldest Id
		Date oldest = new Date();
		int oldestGroupId = 0;
		for(Herd h : group.getHerds()){
			if(h.getDateEntered().before(oldest) && h.getGroupedHerd().getId() != 0){
				oldest = h.getDateEntered();
				oldestGroupId = h.getGroupedHerd().getId();
			}
		}
		group.setId(oldestGroupId);
		
		//Get Groups to delete
		List<Integer> delete = new ArrayList<Integer>();
		for(Herd h : group.getHerds()){
			if(h.getGroupedHerd().getId() != oldestGroupId && h.getGroupedHerd().getId() != 0){
				delete.add(h.getGroupedHerd().getId());
			}
			h.getGroupedHerd().setId(oldestGroupId);
		}

		if(group.getId() == 0 && group.getHerds().size() > 0){
			//Create group if needed
			group = this.groupedHerdDao.saveNewGroupedHerd(group);
		} else if(group.getId() != 0 && group.getHerds().size() > 0){		
			//Update locale if it already had group id
			this.groupedHerdDao.updateGroupedHerdLocationForHerd(group);
		}

		if(group.getHerds().size() > 0){
			//Update herds to group id
			this.herdDao.updateGroupIds(group.getHerds(), group.getId(), farmId);
		}

		//Update orphans grouped herds to null location
		ids = new ArrayList<Integer>();
		for(String o : groupUpdate.getOrphans()) {
			for(String herdId : o.split(",")){
				ids.add(Integer.parseInt(herdId.replace("\n", "").replace("\r", "").replace("\t", "").replace(" ", "")));
			}
		}
		List<Herd> newOrphans = this.herdDao.getHerds(ids,farmId);

		GroupedHerd gh = new GroupedHerd();
		gh.setLocale(new Locale());
		gh.getLocale().setId(-1);
		gh.setHerds(new ArrayList<Herd>());
		gh.getHerds().add(null);
		
		HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>(); //Hold groups we already set to null
		for(Herd orphan : newOrphans){
			if(orphan.getGroupedHerd().getId() != 0 && !map.containsKey(orphan.getGroupedHerd().getId())){
				gh.getHerds().set(0, orphan);
				gh.setId(orphan.getGroupedHerd().getId());
				this.groupedHerdDao.updateGroupedHerdLocationForHerd(gh);
				map.put(orphan.getGroupedHerd().getId(), true);
			}
		}

		//Delete group ids where there are no longer herds attached to them		
		for(int id : delete){
			this.groupedHerdDao.deleteGroupedHerd(id);
		}
	}

}
