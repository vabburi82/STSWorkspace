package com.beingjavaguys.actions;  
  
import java.sql.*;
import java.sql.DriverManager;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.Action;  
  
public class DataBaseAction implements Action {
 private int id;  
 private String firstName;  
 private String lastName;  
 private String email;  
 private String phone;
 
  
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
  
 public String execute() {  
  
  return "SUCCESS";  
  
 }  
 public String getDetail(){  
	
	 try{
	 
	 Class.forName("com.mysql.jdbc.Driver");
	 System.out.println("driver found");
	 Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb","root","root");

	 // save data
	 Statement st=con.createStatement();
	 String maxid_sql = "SELECT MAX(id) FROM student";
	 ResultSet rs = st.executeQuery(maxid_sql);
	 rs.next();
	 int max_id = rs.getInt(1);
	 String insert_sql = "INSERT INTO `empdb`.`student`"
	 		+ "(`id`,"
	 		+ "`firstname`,"
	 		+ "`lastname`,"
	 		+ "`email`,"
	 		+ "`phone`)"
	 		+ "VALUES" 		+ "("
	 		+ (max_id+1)	+ ",'"
	 		+ getFirstName() 	+"','"
	 		+ getLastName()		+"','"
	 		+ email 		+"',"
	 		+ phone 		+ ") ";
	 int save_count = st.executeUpdate(insert_sql);
	 System.out.println("saved = "+ save_count);
	 ServletActionContext.getRequest().setAttribute("saveSuccess", save_count>0?"true":"false");
	 
	 }catch(Exception e){
		 System.out.println(e);
	 }
	
	
	
  return SUCCESS;  
 }
  
}  