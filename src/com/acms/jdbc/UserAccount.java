package com.acms.jdbc;

public class UserAccount {
	private int user_id;
	private String username;
	private String password;
	private int user_type;
	private boolean status;
	
	public UserAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserAccount(int user_id, String username, String password, int user_type, boolean status) {
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.user_type = user_type;
		this.status = status;
	}
		
	public UserAccount(String username, String password, int user_type, boolean status) {
		super();
		this.username = username;
		this.password = password;
		this.user_type = user_type;
		this.status = status;
	}

	public UserAccount(String username, String password, int user_type){
		this.username = username;
		this.password = password;
		this.user_type = user_type;
	}
		
	public UserAccount(int user_id, String password) {
		super();
		this.user_id = user_id;
		this.password = password;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}	
}
