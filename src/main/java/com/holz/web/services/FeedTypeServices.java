package com.holz.web.services;

import java.util.List;

import com.holz.web.models.FeedType;

public interface FeedTypeServices {

	List<FeedType> getFeedTypes(int farmId);
	
	void saveOrUpdateFeedType(FeedType feed,int farmId);

	void enableDisableFeedType(FeedType feed, int farmId);

	List<FeedType> getEnabledFeedTypes(int farmId);
	
}
