package com.holz.web.models;

import java.util.List;

public class GroupedHerdUpdate {
	private int localeId;
	private List<String> current;
	private List<String> orphanLabels;
	private List<String> orphanIds;
	public List<String> getOrphanLabels() {
		return orphanLabels;
	}
	public void setOrphanLabels(List<String> orphans) {
		this.orphanLabels = orphans;
	}
	public List<String> getCurrent() {
		return current;
	}
	public void setCurrent(List<String> current) {
		this.current = current;
	}
	public int getLocaleId() {
		return localeId;
	}
	public void setLocaleId(int localeId) {
		this.localeId = localeId;
	}
	public List<String> getOrphanIds() {
		return orphanIds;
	}
	public void setOrphanIds(List<String> orphanIds) {
		this.orphanIds = orphanIds;
	}
}
