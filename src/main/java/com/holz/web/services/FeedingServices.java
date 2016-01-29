package com.holz.web.services;

import com.holz.web.models.Feeding;

public interface FeedingServices {

	Feeding saveOrUpdateFeeding(Feeding feeding, int farmId);

	boolean userHasAccessToFeeding(int farmId,int feedingId);

	void saveFeedSelections(Feeding feeding, int farmId);

}
