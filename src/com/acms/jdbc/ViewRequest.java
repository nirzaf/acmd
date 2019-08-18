package com.acms.jdbc;

public class ViewRequest {
	private int request_id;
	private int requested_by;
	private int requested_property;
	private String requested_date;
	private String date_of_view;
	private int status;
	private boolean isDeleted;
	
	public ViewRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ViewRequest(int request_id, int requested_by, int requested_property, String requested_date,
			String date_of_view, int status, boolean isDeleted) {
		super();
		this.request_id = request_id;
		this.requested_by = requested_by;
		this.requested_property = requested_property;
		this.requested_date = requested_date;
		this.date_of_view = date_of_view;
		this.status = status;
		this.isDeleted = isDeleted;
	}
	
	public ViewRequest(int request_id, int requested_by, int requested_property, String requested_date,
			String date_of_view, int status) {
		super();
		this.request_id = request_id;
		this.requested_by = requested_by;
		this.requested_property = requested_property;
		this.requested_date = requested_date;
		this.date_of_view = date_of_view;
		this.status = status;
	}

	public ViewRequest(int requested_by, int requested_property, String requested_date, String date_of_view) {
		super();
		this.requested_by = requested_by;
		this.requested_property = requested_property;
		this.requested_date = requested_date;
		this.date_of_view = date_of_view;
	}

	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public int getRequested_by() {
		return requested_by;
	}

	public void setRequested_by(int requested_by) {
		this.requested_by = requested_by;
	}

	public int getRequested_property() {
		return requested_property;
	}

	public void setRequested_property(int requested_property) {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
