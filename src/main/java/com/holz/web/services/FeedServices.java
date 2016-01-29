package com.holz.web.services;

import java.util.List;

import com.holz.web.models.Leftovers;

public interface FeedServices {

	List<Leftovers> getLeftoverFeeds(int farmId);
	
}
