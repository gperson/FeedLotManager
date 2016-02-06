package com.holz.web.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.GroupedHerdDao;
import com.holz.web.daos.HerdDao;
import com.holz.web.models.GroupedHerdUpdate;
import com.holz.web.models.Herd;
import com.holz.web.services.HerdServices;

@Transactional
@Service
public class HerdServicesImpl implements HerdServices {

	@Autowired
	private HerdDao herdDao;
	
	@Autowired
	private GroupedHerdDao groupedHerdDao;

	@Override
	public List<Herd> getAllActiveHerds(int farmId) {
		return this.herdDao.getAllActiveHerds(farmId);
	}

	@Override
	public void saveOrUpdateHerd(Herd herd,int farmId) {
		if(herd.getId() != 0){
			herd.setGroupedHerd(this.groupedHerdDao.getGroupedHerdForHerd(herd.getId(), farmId));
		}
		this.herdDao.saveOrUpdate(herd, farmId);
	}

	@Override
	public List<Herd> getHerdsForGroupedLocal(int farmId, int groupedLocale) {
		return this.herdDao.getHerdsForGroupedHerd(farmId, groupedLocale);
	}

	@Override
	public GroupedHerdUpdate getOrphanHerds(int farmId) {
		GroupedHerdUpdate groups = new GroupedHerdUpdate();
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<String> orphans = new ArrayList<String>();
		for(Herd h : this.herdDao.getOrphanHerds(farmId)){
			int groupId = h.getGroupedHerd().getId();
			if(groupId != 0){
				if(map.containsKey(groupId)){
					String current = map.get(groupId);
					map.remove(groupId);
					map.put(groupId,current + ", "+h.getId());
				} else {
					map.put(groupId,h.getId()+"");
				}
			}
			else {
				orphans.add(h.getId()+"");
			}
		}
		for (Entry<Integer, String> entry : map.entrySet())
		{
			orphans.add(entry.getValue());
		}
		
		groups.setOrphans(orphans);
		return groups;
	}

	@Override
	public void updateGroupIds(List<Herd> herds, int groupId, int farmId) {
		this.herdDao.updateGroupIds(herds,groupId,farmId);
	}


}
