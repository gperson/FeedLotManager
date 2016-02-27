package com.holz.web.models.reports;

import java.util.List;

public class FeedHistory {
	private String type;
	private List<Double> amounts;
	public List<Double> getAmounts() {
		return amounts;
	}
	public void setAmounts(List<Double> amounts) {
		this.amounts = amounts;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
