package com.holz.web.models;

import java.sql.Date;

public class Herd {
	
	private int herdId;
	private int quantity;
	private double weight;
	private double cost;
	private String tagNumber;
	private Date estimatedSaleDate;
	private Date implantDate;
	private Date optiflexDate;
	private Date dateEntered;
	
	public int getHerdId() {
		return herdId;
	}
	public void setHerdId(int herdId) {
		this.herdId = herdId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getTagNumber() {
		return tagNumber;
	}
	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}
	public Date getEstimatedSaleDate() {
		return estimatedSaleDate;
	}
	public void setEstimatedSaleDate(Date estimatedSaleDate) {
		this.estimatedSaleDate = estimatedSaleDate;
	}
	public Date getImplantDate() {
		return implantDate;
	}
	public void setImplantDate(Date implantDate) {
		this.implantDate = implantDate;
	}
	public Date getOptiflexDate() {
		return optiflexDate;
	}
	public void setOptiflexDate(Date optiflexDate) {
		this.optiflexDate = optiflexDate;
	}
	public Date getDateEntered() {
		return dateEntered;
	}
	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}
}