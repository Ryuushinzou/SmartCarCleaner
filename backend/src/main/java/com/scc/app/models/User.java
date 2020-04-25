package com.scc.app.models;

//  TODO: replace this with actual User model - https://github.com/Ryuushinzou/SmartCarCleaner/blob/backend/feature/users_api/backend/src/main/java/com/scc/app/model/User.java
public class User {

	private String uid;
	private String name;
	private String email;
	private String password;

	public User(String uid, String name, String email, String password) {
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
