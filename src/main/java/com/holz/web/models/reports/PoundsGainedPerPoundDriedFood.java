package com.holz.web.models.reports;

public class PoundsGainedPerPoundDriedFood {
	private String herdsLabels;
	private double pGpD;
	private int groupedHerdId;
	private double driedFoodTotal;
	private double foodTotal;
	public String getHerdsLabels() {
		return herdsLabels;
	}
	public void setHerdsLabels(String herdsLabels) {
		this.herdsLabels = herdsLabels;
	}
	public double getpGpD() {
		return pGpD;
	}
	public void setpGpD(double pGpD) {
		this.pGpD = pGpD;
	}
	public int getGroupedHerdId() {
		return groupedHerdId;
	}
	public void setGroupedHerdId(int groupedHerdId) {
		this.groupedHerdId = groupedHerdId;
	}
	public double getDriedFoodTotal() {
		return driedFoodTotal;
	}
	public void setDriedFoodTotal(double driedFoodTotal) {
		this.driedFoodTotal = driedFoodTotal;
	}
	public double getFoodTotal() {
		return foodTotal;
	}
	public void setFoodTotal(double foodTotal) {
		this.foodTotal = foodTotal;
	}
}
