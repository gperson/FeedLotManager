package com.holz.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.FeedDao;
import com.holz.web.models.Leftovers;
import com.holz.web.services.FeedServices;

@Transactional
@Service
public class FeedServicesImpl implements FeedServices {

	@Autowired
	private FeedDao feedDao;

	@Override
	public List<Leftovers> getLeftoverFeeds(int farmId) {
		return this.feedDao.getLeftoverFeeds(farmId);
	}

}
