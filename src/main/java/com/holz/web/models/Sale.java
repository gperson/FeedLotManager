package com.holz.web.models;

import java.util.List;

public class Sale {
	private List<Herd> herds;
	private Packer packer;

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
}
