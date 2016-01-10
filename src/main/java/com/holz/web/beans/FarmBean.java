package com.holz.web.beans;

import java.util.List;

public class FarmBean {

	private List<UserBean> users;
	private String farmName;

	public String getFarmName() {
		return farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	public List<UserBean> getUsers() {
		return users;
	}

	public void setUsers(List<UserBean> users) {
		this.users = users;
	}

}
