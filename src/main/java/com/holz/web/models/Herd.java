package com.holz.web.models;

import java.util.Date;

import com.holz.web.models.enums.Sex;

public class Herd {
	
	private int id;
	private int quantity;
	private double weight;
	private double cost;
	private String tagNumber;
	private Date estimatedSaleDate;
	private Date implantDate;
	private Date optiflexDate;
	private Date dateEntered;
	private Supplier supplier;
	private GroupedHerd groupedHerd;
	private boolean isSold;
	private Sex sex;
	private String herdLabel;
	private int farmId;
	private int deadQuantity;
	
	public int getId() {
		return id;
	}
	public void setId(int herdId) {
		this.id = herdId;
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
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public GroupedHerd getGroupedHerd() {
		return groupedHerd;
	}
	public void setGroupedHerd(GroupedHerd groupedHerd) {
		this.groupedHerd = groupedHerd;
	}
	public boolean isSold() {
		return isSold;
	}
	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public String getHerdLabel() {
		return herdLabel;
	}
	public void setHerdLabel(String herdLabel) {
		this.herdLabel = herdLabel;
	}
	public int getFarmId() {
		return farmId;
	}
	public void setFarmId(int farmId) {
		this.farmId = farmId;
	}
	public int getDeadQuantity() {
		return deadQuantity;
	}
	public void setDeadQuantity(int deadQuantity) {
		this.deadQuantity = deadQuantity;
	}
}
