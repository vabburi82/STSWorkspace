package com.beingjavaguys.actions;

public class RegistrationData {
	public int id;
	public String firstName;
	public String lastName;
	public String email;
	public String phone;

	public RegistrationData() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "RegistrationData [id=" + id + ",\n firstName=" + firstName + ",\n lastName=" + lastName + ",\n email=" + email
				+ ",\n phone=" + phone + "]";
	}
}