package com.acms.jdbc;

public class Property {
	private int property_id;
	private int property_type;
	private int suitable_for;
	private int is_available;
	private int owner;
	private int rented_by;
	private float charge;
	private boolean isDeleted;
	
	public Property() {
		super();
	}

	public Property(int property_id, int property_type, int suitable_for, int is_available, int owner, int rented_by,
			float charge,boolean isDeleted) {
		super();
		this.property_id = property_id;
		this.property_type = property_type;
		this.suitable_for = suitable_for;
		this.is_available = is_available;
		this.owner = owner;
		this.rented_by = rented_by;
		this.charge = charge;
		this.isDeleted = isDeleted;
	}

	public Property(int property_type, int suitable_for, int is_available, int owner, int rented_by, float charge) {
		super();
		this.property_type = property_type;
		this.suitable_for = suitable_for;
		this.is_available = is_available;
		this.owner = owner;
		this.rented_by = rented_by;
		this.charge = charge;
	}

	public int getProperty_id() {
		return property_id;
	}

	public void setProperty_id(int property_id) {
		this.property_id = property_id;
	}

	public int getProperty_type() {
		return property_type;
	}

	public void setProperty_type(int property_type) {
		this.property_type = property_type;
	}

	public int getSuitable_for() {
		return suitable_for;
	}

	public void setSuitable_for(int suitable_for) {
		this.suitable_for = suitable_for;
	}

	public int getIs_available() {
		return is_available;
	}

	public void setIs_available(int is_available) {
		this.is_available = is_available;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getRented_by() {
		return rented_by;
	}

	public void setRented_by(int rented_by) {
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
	
}
