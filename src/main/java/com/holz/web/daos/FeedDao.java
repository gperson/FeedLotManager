package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.Feed;

public interface FeedDao {

	List<Feed> getFeeds(int farmId);
	
	void saveOrUpdate(Feed feed, int farmId);
	
}
