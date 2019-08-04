package com.acms.jdbc;

public class Student {
	private int student_id;
	private String first_name;
	private String last_name;
	private String address;
	private String email;
	private String telephone;
		
	public Student(String first_name, String last_name, String address, String email, String telephone) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.email = email;
		this.telephone = telephone;
	}

	public Student(int student_id, String first_name, String last_name, String address, String email,
			String telephone) {
		this.student_id = student_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.email = email;
		this.telephone = telephone;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return "Student [student_id=" + student_id + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", address=" + address + ", email=" + email + ", telephone=" + telephone + "]";
	}
}