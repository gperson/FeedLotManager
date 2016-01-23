package com.holz.web.models;

public class Locale {
	private int id;
	private String localeName;
	private boolean enabled;
	private GroupedHerd groupedHerd;
	
	public String getLocaleName() {
		return localeName;
	}
	public void setLocaleName(String localeName) {
		this.localeName = localeName;
	}
	public int getId() {
		return id;
	}
	public void setId(int localeId) {
		this.id = localeId;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public GroupedHerd getGroupedHerd() {
		return groupedHerd;
	}
	public void setGroupedHerd(GroupedHerd groupedHerd) {
		this.groupedHerd = groupedHerd;
	}
}
