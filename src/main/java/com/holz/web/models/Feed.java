package com.holz.web.models;

public class Feed {
	private int id;
	private FeedType feedType;
	private double amount;
	private double ratio;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public FeedType getFeedType() {
		return feedType;
	}
	public void setFeedType(FeedType feedType) {
		this.feedType = feedType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
}
