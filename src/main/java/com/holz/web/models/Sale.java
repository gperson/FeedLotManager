package com.holz.web.models;

import java.util.Date;
import java.util.List;

public class Sale {
	private int id;
	private List<Herd> herds;
	private Packer packer;
	private double salePrice;
	private double saleWeight;
	private int quantity;
	private double dressingPercent;
	private Date saleDate;
	private double shrinkPercent;
	private GroupedHerd groupedHerd;

	public List<Herd> getHerds() {
		return herds;
	}

	public void setHerds(List<Herd> herds) {
		this.herds = herds;
	}

	public Packer getPacker() {
		return packer;
	}

	public void setPacker(Packer packer) {
		this.packer = packer;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public double getSaleWeight() {
		return saleWeight;
	}

	public void setSaleWeight(double saleWeight) {
		this.saleWeight = saleWeight;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getDressingPercent() {
		return dressingPercent;
	}

	public void setDressingPercent(double dressingPercent) {
		this.dressingPercent = dressingPercent;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public double getShrinkPercent() {
		return shrinkPercent;
	}

	public void setShrinkPercent(double shrinkPercent) {
		this.shrinkPercent = shrinkPercent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GroupedHerd getGroupedHerd() {
		return groupedHerd;
	}

	public void setGroupedHerd(GroupedHerd groupedHerd) {
		this.groupedHerd = groupedHerd;
	}
}
