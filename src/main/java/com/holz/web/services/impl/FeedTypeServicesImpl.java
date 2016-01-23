package com.holz.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.FeedTypeDao;
import com.holz.web.models.FeedType;
import com.holz.web.services.FeedTypeServices;

@Transactional
@Service
public class FeedTypeServicesImpl implements FeedTypeServices {

	@Autowired
	private FeedTypeDao feedDao;

	@Override
	public List<FeedType> getFeedTypes(int farmId) {
		return this.feedDao.getFeedTypes(farmId);
	}

	@Override
	public void saveOrUpdateFeedType(FeedType feed,int farmId) {
		this.feedDao.saveOrUpdate(feed, farmId);
	}

	@Override
	public void enableDisableFeedType(FeedType feed, int farmId) {
		this.feedDao.enableDisableFeedType(feed,farmId);
	}


}
