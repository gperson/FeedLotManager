package com.holz.web.services;

import java.util.List;

import com.holz.web.models.Feeding;

public interface FeedingServices {

	Feeding saveOrUpdateFeeding(Feeding feeding, int farmId);

	boolean userHasAccessToFeeding(int farmId,int feedingId);

	void saveFeedSelections(Feeding feeding, int farmId);

	List<Feeding> getAllFeedings(int farmId);

	void editedFeeding(Feeding feeding, int farmId);

}
