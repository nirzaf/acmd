package com.acms.jdbc;

public class GetRequest {
	private int request_id;
	private String requested_by;
	private String requested_property;
	private String requested_date;
	private String date_of_view;
	private int owner_id;
	private boolean status;
	private boolean isDeleted;
	
	public GetRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetRequest(int request_id, String requested_by, String requested_property, String requested_date,
			String date_of_view, boolean status, boolean isDeleted) {
		super();
		this.request_id = request_id;
		this.requested_by = requested_by;
		this.requested_property = requested_property;
		this.requested_date = requested_date;
		this.date_of_view = date_of_view;
		this.status = status;
		this.isDeleted = isDeleted;
	}
	
	public GetRequest(int request_id, String requested_by, String requested_property, String requested_date,
			String date_of_view, int owner_id, boolean status, boolean isDeleted) {
		super();
		this.request_id = request_id;
		this.requested_by = requested_by;
		this.requested_property = requested_property;
		this.requested_date = requested_date;
		this.date_of_view = date_of_view;
		this.owner_id = owner_id;
		this.status = status;
		this.isDeleted = isDeleted;
	}

	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public String getRequested_by() {
		return requested_by;
	}

	public void setRequested_by(String requested_by) {
		this.requested_by = requested_by;
	}

	public String getRequested_property() {
		return requested_property;
	}

	public void setRequested_property(String requested_property) {
		this.requested_property = requested_property;
	}

	public String getRequested_date() {
		return requested_date;
	}

	public void setRequested_date(String requested_date) {
		this.requested_date = requested_date;
	}

	public String getDate_of_view() {
		return date_of_view;
	}

	public void setDate_of_view(String date_of_view) {
		this.date_of_view = date_of_view;
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

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}	
}
