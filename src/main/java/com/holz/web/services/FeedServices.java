package com.holz.web.services;

import java.util.List;

import com.holz.web.models.Feed;

public interface FeedServices {

	List<Feed> getFeeds(int farmId);
	
	void saveOrUpdateFeed(Feed feed,int farmId);

	void enableDisableFeed(Feed feed, int farmId);
	
}
