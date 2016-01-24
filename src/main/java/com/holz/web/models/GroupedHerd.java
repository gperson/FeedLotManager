package com.holz.web.models;

import java.util.List;

public class GroupedHerd {
	private int id;
	private List<Herd> herds;
	private List<Feeding> feedings;
	private Locale locale;
	private List<Sale> sales;
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
		if(this.sales != null){
			for(Sale s : sales){
				count = count - s.getQuantity();
			}
		}
		return count;
	}
	public List<Sale> getSales() {
		return sales;
	}
	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
}
