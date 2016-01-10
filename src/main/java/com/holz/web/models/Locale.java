package com.holz.web.models;

import java.util.List;

public class Locale {
	private int localeId;
	private String localeName;
	private List<String> herdIds;
	
	public List<String> getHerdIds() {
		return herdIds;
	}
	public void setHerdIds(List<String> herdIds) {
		this.herdIds = herdIds;
	}
	public String getLocaleName() {
		return localeName;
	}
	public void setLocaleName(String localeName) {
		this.localeName = localeName;
	}
	public int getLocaleId() {
		return localeId;
	}
	public void setLocaleId(int localeId) {
		this.localeId = localeId;
	}
}
