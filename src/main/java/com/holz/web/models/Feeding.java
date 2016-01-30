package com.holz.web.models;

import java.util.Date;
import java.util.List;

public class Feeding {
	private int id;
	private List<Feed> feeds;
	private GroupedHerd groupedHerd;
	private Date feedingTime;
	private Date lastUpdated;
	private int bunkScore;
	private double deliveredAmount;
	private User user;
	private boolean hasLeftovers;
	private List<Leftovers> leftovers;
	
	public Date getFeedingTime() {
		return feedingTime;
	}
	public void setFeedingTime(Date feedingTime) {
		this.feedingTime = feedingTime;
	}

	public int getBunkScore() {
		return bunkScore;
	}

	public void setBunkScore(int bunkScore) {
		this.bunkScore = bunkScore;
	}

	public double getDeliveredAmount() {
		return deliveredAmount;
	}

	public void setDeliveredAmount(double deliveredAmount) {
		this.deliveredAmount = deliveredAmount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isHasLeftovers() {
		return hasLeftovers;
	}

	public void setHasLeftovers(boolean hasLeftovers) {
		this.hasLeftovers = hasLeftovers;
	}
	public List<Feed> getFeeds() {
		return feeds;
	}

	public void setFeeds(List<Feed> feeds) {
		this.feeds = feeds;
	}

	public GroupedHerd getGroupedHerd() {
		return groupedHerd;
	}

	public void setGroupedHerd(GroupedHerd groupedHerd) {
		this.groupedHerd = groupedHerd;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Leftovers> getLeftovers() {
		return leftovers;
	}
	public void setLeftovers(List<Leftovers> leftovers) {
		this.leftovers = leftovers;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
