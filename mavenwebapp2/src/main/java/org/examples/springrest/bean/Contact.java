package org.examples.springrest.bean;

import java.time.LocalDate;

public class Contact{
 
 int id;
 String name; 
 String email; 
 Long phone; 
 LocalDate dob;
 
 
 
public Contact(int id, String name, String email, Long phone, LocalDate dob) {
	this(id, name);
	this.email = email;
	this.phone = phone;
	this.dob = dob;
}
public Contact(Integer id, String name) {
	super();
	this.id = id;
	this.name = name;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
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
public Long getPhone() {
	return phone;
}
public void setPhone(Long phone) {
	this.phone = phone;
}
public LocalDate getDob() {
	return dob;
}
public void setDob(LocalDate dob) {
	this.dob = dob;
} 
 
  
}
