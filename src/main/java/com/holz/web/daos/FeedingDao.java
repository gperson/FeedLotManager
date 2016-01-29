package com.holz.web.daos;

import com.holz.web.models.Feeding;

public interface FeedingDao {

	Feeding saveOrUpdate(Feeding feeding, int farmId);

	boolean userHasAccessToFeeding(int farmId, int feedingId);

	Feeding getFeeding(int id);

	void updateFeedingNoLeftovers(int feedingId);

}
