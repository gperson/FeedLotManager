package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.Feed;
import com.holz.web.models.Leftovers;

public interface FeedDao {

	void saveFeedSelections(List<Feed> feeds, int feedingId);

	void updateFeedAmounts(List<Feed> feeds, int farmId);

	List<Leftovers> getLeftoverFeeds(int farmId);

	List<Feed> getFeedsForFeeding(int feedingId);
}
