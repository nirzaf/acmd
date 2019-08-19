package com.acms.jdbc;

public class UserCookies {
	private String user_id;
	private String user_type;
	private String username;

	public UserCookies(String user_id, String user_type, String username) {
		super();
		this.user_id = user_id;
		this.user_type = user_type;
		this.username = username;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
