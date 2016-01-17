package com.holz.web.models;

public class Feed {
	private int id;
	private String feedType;
	private double driedMatterPercentage;
	private boolean enabled;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFeedType() {
		return feedType;
	}
	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}
	public double getDriedMatterPercentage() {
		return driedMatterPercentage;
	}
	public void setDriedMatterPercentage(double driedMatterPercentage) {
		this.driedMatterPercentage = driedMatterPercentage;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
