package com.acms.jdbc;

public class Property_Type {
	private int property_id;
	private String property_name;
	private boolean isDeleted;
	
	public Property_Type() {
		super();
	}
	
	public Property_Type(String propery_name) {
		this.property_name = propery_name;
	}

	public Property_Type(int property_id, String propery_name, boolean isDeleted) {
		super();
		this.property_id = property_id;
		this.property_name = propery_name;
		this.isDeleted = isDeleted;
	}

	public int getProperty_id() {
		return property_id;
	}

	public void setProperty_id(int property_id) {
		this.property_id = property_id;
	}

	public String getProperty_name() {
		return property_name;
	}

	public void setProperty_name(String propery_name) {
		this.property_name = propery_name;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
