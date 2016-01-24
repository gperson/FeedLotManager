package com.holz.web.models;

import java.util.List;

public class GroupedHerd {
	private int id;
	private List<Herd> herds;
	private List<Feeding> feedings;
	private Locale locale;
	private Sale sale;
	private int count;
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public List<Herd> getHerds() {
		return herds;
	}
	public void setHerds(List<Herd> herds) {
		this.herds = herds;
	}
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	public List<Feeding> getFeedings() {
		return feedings;
	}
	public void setFeedings(List<Feeding> feedings) {
		this.feedings = feedings;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCount() {
		if(this.herds != null){
			for(Herd h : this.herds){
				count = count + h.getQuantity();
			}
		}
		return count;
	}
}
