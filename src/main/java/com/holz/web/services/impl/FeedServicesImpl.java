package com.holz.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.FeedDao;
import com.holz.web.models.Feed;
import com.holz.web.services.FeedServices;

@Transactional
@Service
public class FeedServicesImpl implements FeedServices {

	@Autowired
	private FeedDao feedDao;

	@Override
	public List<Feed> getFeeds(int farmId) {
		return this.feedDao.getFeeds(farmId);
	}

	@Override
	public void saveOrUpdateFeed(Feed feed,int farmId) {
		this.feedDao.saveOrUpdate(feed, farmId);
	}

	@Override
	public void enableDisableFeed(Feed feed, int farmId) {
		this.feedDao.enableDisableFeed(feed,farmId);
	}


}
