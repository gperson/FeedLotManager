package com.holz.web.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "FARM",uniqueConstraints = {
		@UniqueConstraint(columnNames = "farmId")})
public class FarmEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int farmId;
	private String farmName;

	public FarmEntity() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "farmId", unique = true, nullable = false)
	public int getfarmId() {
		return this.farmId;
	}

	public void setfarmId(int farmId) {
		this.farmId = farmId;
	}

	@Column(name = "farmName", unique = false, nullable = false, length = 50)
	public String getFarmName() {
		return this.farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	@Override
	public String toString() {
		return "Farm [farmName=" + farmName + ", farmId=" + farmId + "]";
	}
}