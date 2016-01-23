package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.FeedType;

public interface FeedTypeDao {

	List<FeedType> getFeedTypes(int farmId);
	
	void saveOrUpdate(FeedType feed, int farmId);

	void enableDisableFeedType(FeedType feed, int farmId);
	
}
