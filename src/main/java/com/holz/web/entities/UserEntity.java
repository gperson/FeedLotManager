package com.holz.web.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.holz.web.entities.FarmEntity;

@Entity
@Table(name = "USERS",uniqueConstraints = {
		@UniqueConstraint(columnNames = "userId")})
public class UserEntity implements java.io.Serializable {
	

	private static final long serialVersionUID = 1L;
	private int userId;						
	private String username;						
	private String firstName;				
	private String lastName;						
	private String email;					
	private String password;						
	private FarmEntity farm;
	private boolean enabled;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "userId", unique = true, nullable = false)
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Column(name = "username", unique = false, nullable = false, length = 50)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "firstName", unique = false, nullable = false, length = 25)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "lastName", unique = false, nullable = false, length = 25)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "email", unique = false, nullable = false, length = 50)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "password", unique = false, nullable = false, length = 100)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "farmId", nullable = false)
	public FarmEntity getFarm() {
		return farm;
	}
	public void setFarm(FarmEntity farm) {
		this.farm = farm;
	}
	
	@Column(name = "enabled", unique = false, nullable = false)
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
