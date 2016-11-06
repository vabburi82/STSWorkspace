package com.beingjavaguys.actions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class DataBaseAction implements Action {
	private RegistrationData regData = new RegistrationData();

	public int getId() {
		return regData.id;
	}

	public void setId(int id) {
		this.regData.id = id;
	}

	public String getFirstName() {
		return regData.firstName;
	}

	public void setFirstName(String firstName) {
		this.regData.firstName = firstName;
	}

	public String getLastName() {
		return regData.lastName;
	}

	public void setLastName(String lastName) {
		this.regData.lastName = lastName;
	}

	public String getEmail() {
		return regData.email;
	}

	public void setEmail(String email) {
		this.regData.email = email;
	}

	public String getPhone() {
		return regData.phone;
	}

	public void setPhone(String phone) {
		this.regData.phone = phone;
	}

	public String execute() {

		try {
			viewAllRegistrations();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return "SUCCESS";

	}

	public String getDetail() {

		try {
			// save data
			saveNewRegistration();

			viewAllRegistrations();

		} catch (Exception e) {
			System.out.println(e);
		}

		return SUCCESS;
	}

	private void viewAllRegistrations() throws ClassNotFoundException, SQLException {
		String SELECT_ALL_SQL = "SELECT `student`.`id`, `student`.`firstname`, `student`.`lastname`, `student`.`email`, `student`.`phone`FROM `empdb`.`student` ";
		Connection con = openConnection();
		Statement st = con.createStatement();
		ResultSet rs1 = st.executeQuery(SELECT_ALL_SQL);

		ArrayList<RegistrationData> all_registerations = new ArrayList<RegistrationData>();
		while (rs1.next()) {
			RegistrationData registerUser = new RegistrationData();
			
			registerUser.setId(rs1.getInt("id"));
			registerUser.setFirstName(rs1.getString("firstname"));
			registerUser.setLastName(rs1.getString("lastname"));
			registerUser.setEmail(rs1.getString("email"));
			registerUser.setPhone(rs1.getString("phone"));
			
			all_registerations.add(registerUser);
			System.out.println(registerUser);
		}
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
				.get(ServletActionContext.HTTP_REQUEST);
		
		request.setAttribute("all_registerations", all_registerations);
		
	

	}

	public String saveNewRegistration() {
		int max_id;
		try {
			max_id = getMaxRegisterId();
			int save_count = saveRegistration(max_id);
			ServletActionContext.getRequest().setAttribute("saveSuccess", save_count > 0 ? "true" : "false");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private int saveRegistration(int max_id) throws Exception {
		Connection con = openConnection();
		Statement st = con.createStatement();
		String insert_sql = "INSERT INTO `empdb`.`student`" + "(`id`," + "`firstname`," + "`lastname`," + "`email`,"
				+ "`phone`)" + "VALUES" + "(" + (max_id + 1) + ",'" + getFirstName() + "','" + getLastName() + "','"
				+ regData.email + "'," + regData.phone + ") ";

		int save_count = st.executeUpdate(insert_sql);
		return save_count;
	}

	private int getMaxRegisterId() throws SQLException, Exception {
		Connection con = openConnection();
		String maxid_sql = "SELECT MAX(id) FROM student";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(maxid_sql);
		rs.next();
		return rs.getInt(1);
	}

	private Connection openConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("driver found");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb", "root", "root");
		return con;
	}

}