package com.holz.web.models.reports;

public class SalesOverview {
	private String herdsLabels;
	private int startCount;
	private int endCount;
	private double startWeight;
	private double endWeight;
	private double purchasePrice;
	private double salesAmount;
	public String getHerdsLabels() {
		return herdsLabels;
	}
	public void setHerdsLabels(String herdsLabels) {
		this.herdsLabels = herdsLabels;
	}
	public int getStartCount() {
		return startCount;
	}
	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}
	public int getEndCount() {
		return endCount;
	}
	public void setEndCount(int endCount) {
		this.endCount = endCount;
	}
	public double getStartWeight() {
		return startWeight;
	}
	public void setStartWeight(double startWeight) {
		this.startWeight = startWeight;
	}
	public double getEndWeight() {
		return endWeight;
	}
	public void setEndWeight(double endWeight) {
		this.endWeight = endWeight;
	}
	public double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public double getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(double salesAmount) {
		this.salesAmount = salesAmount;
	}
}
