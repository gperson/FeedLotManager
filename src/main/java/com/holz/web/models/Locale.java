package com.holz.web.models;

import java.util.List;

public class Locale {
	private int id;
	private String localeName;
	private List<Herd> herds;
	private int livestockCount;
	private boolean enabled;
	
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
	public int getLivestockCount() {
		return livestockCount;
	}
	public void setLivestockCount(int livestockCount) {
		this.livestockCount = livestockCount;
	}
	public List<Herd> getHerds() {
		return herds;
	}
	public void setHerds(List<Herd> herds) {
		this.herds = herds;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
