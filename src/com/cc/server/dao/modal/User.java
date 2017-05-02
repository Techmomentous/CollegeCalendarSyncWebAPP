
package com.cc.server.dao.modal;

import java.io.Serializable;

public class User implements Serializable {

	Long			id;

	private String	name;
	private String	email;
	private String	phone;
	private String	password;
	private String	userType;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	private boolean	isUpdated;

	public User(String name, String email, String password, String userType) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.userType = userType;
	}

	public User(Long id, String name, String email, String phone, String userType) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.userType = userType;
	}

	public User() {
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public boolean isUpdated() {
		return isUpdated;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullname=" + name + ", email=" + email + ", password=" + password + ", userType=" + userType + "]";
	}

}
