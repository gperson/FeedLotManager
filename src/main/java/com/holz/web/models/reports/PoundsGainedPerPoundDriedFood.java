package com.holz.web.models.reports;

public class PoundsGainedPerPoundDriedFood {
	private String herdsLabels;
	private double pGpD;
	private int groupedHerdId;
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
}
