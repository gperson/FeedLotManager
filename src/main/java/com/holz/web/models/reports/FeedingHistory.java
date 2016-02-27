package com.holz.web.models.reports;

import java.util.List;

public class FeedingHistory {
	private List<String> dateLabels;
	private List<FeedHistory> feedAmounts;	
	
	public List<FeedHistory> getFeedAmounts() {
		return feedAmounts;
	}
	public void setFeedAmounts(List<FeedHistory> feedAmounts) {
		this.feedAmounts = feedAmounts;
	}
	public List<String> getDateLabels() {
		return dateLabels;
	}
	public void setDateLabels(List<String> dateLabels) {
		this.dateLabels = dateLabels;
	}
}
