package com.acms.jdbc;

public class GetProperty {
	private int property_id;
	private String property_type;
	private String address;
	private int suitable_for;
	private String is_available;
	private int owner_id;
	private String owner;
	private String rented_by;
	private float charge;
	private boolean isDeleted;
	private boolean status;

	public GetProperty() {
		super();
	}

	public GetProperty(int property_id, String property_type, String address, int suitable_for, String is_available, String owner,
			String rented_by, float charge) {
		super();
		this.property_id = property_id;
		this.property_type = property_type;
		this.address = address;
		this.suitable_for = suitable_for;
		this.is_available = is_available;
		this.owner = owner;
		this.rented_by = rented_by;
		this.charge = charge;
	}

	
	public GetProperty(int property_id, String property_type, String address, int suitable_for, String is_available,
			int owner_id, String owner, String rented_by, float charge, boolean status) {
		super();
		this.property_id = property_id;
		this.property_type = property_type;
		this.address = address;
		this.suitable_for = suitable_for;
		this.is_available = is_available;
		this.owner_id = owner_id;
		this.owner = owner;
		this.rented_by = rented_by;
		this.charge = charge;
		this.status = status;
	}

	public int getProperty_id() {
		return property_id;
	}

	public void setProperty_id(int property_id) {
		this.property_id = property_id;
	}

	public String getProperty_type() {
		return property_type;
	}

	public void setProperty_type(String property_type) {
		this.property_type = property_type;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSuitable_for() {
		return suitable_for;
	}

	public void setSuitable_for(int suitable_for) {
		this.suitable_for = suitable_for;
	}

	public String getIs_available() {
		return is_available;
	}

	public void setIs_available(String is_available) {
		this.is_available = is_available;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getRented_by() {
		return rented_by;
	}

	public void setRented_by(String rented_by) {
		this.rented_by = rented_by;
	}

	public float getCharge() {
		return charge;
	}

	public void setCharge(float charge) {
		this.charge = charge;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
