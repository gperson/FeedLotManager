package com.holz.web.models;

import java.util.List;

public class Feeding {
	private List<FeedType> feeds;
	private GroupedHerd groupedHerd;

	public List<FeedType> getFeedTypes() {
		return feeds;
	}

	public void setFeedTypes(List<FeedType> feeds) {
		this.feeds = feeds;
	}

	public GroupedHerd getGroupedHerd() {
		return groupedHerd;
	}

	public void setGroupedHerd(GroupedHerd groupedHerd) {
		this.groupedHerd = groupedHerd;
	}
}
