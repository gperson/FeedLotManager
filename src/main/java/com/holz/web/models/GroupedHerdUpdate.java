package com.holz.web.models;

import java.util.List;

public class GroupedHerdUpdate {
	private int groupedId;
	private int localeId;
	private List<String> current;
	private List<String> orphans;
	public List<String> getOrphans() {
		return orphans;
	}
	public void setOrphans(List<String> orphans) {
		this.orphans = orphans;
	}
	public List<String> getCurrent() {
		return current;
	}
	public void setCurrent(List<String> current) {
		this.current = current;
	}
	public int getGroupedId() {
		return groupedId;
	}
	public void setGroupedId(int groupedId) {
		this.groupedId = groupedId;
	}
	public int getLocaleId() {
		return localeId;
	}
	public void setLocaleId(int localeId) {
		this.localeId = localeId;
	}
}
