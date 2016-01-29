package com.holz.web.models;

import java.util.List;

public class Leftovers {
	private List<Feed> feeds;
	private double amount;
	private int feedingId;
	
	public List<Feed> getFeeds() {
		return feeds;
	}
	public void setFeeds(List<Feed> feeds) {
		this.feeds = feeds;
	}
	public int getFeedingId() {
		return feedingId;
	}
	public void setFeedingId(int feedingId) {
		this.feedingId = feedingId;
	}
	public double getAmount() {
		if(this.feeds != null){
			for(Feed f : this.feeds){
				this.amount = this.amount + f.getAmount();
			}
		}
		return amount;
	}
}
