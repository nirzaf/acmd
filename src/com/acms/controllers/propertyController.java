package com.acms.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acms.jdbc.GetProperty;
import com.acms.jdbc.Property;
import com.acms.jdbc.Property_Type;
import com.acms.model.GetPropertyDbUtil;
import com.acms.model.PropertyDbUtil;
import com.acms.model.PropertyTypeDbUtil;
import com.acms.model.SqliteConUtil;

@WebServlet("/propertyController")
public class propertyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// private StudentDbUtil studentDbUtil;
	private GetPropertyDbUtil propertyDbUtil;
	private PropertyTypeDbUtil propertyTypeDbUtil;
	private PropertyDbUtil dbUtil;

	// private DataSource dataSource;

	SqliteConUtil conn = new SqliteConUtil();

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		try {
			propertyDbUtil = new GetPropertyDbUtil(conn);
			propertyTypeDbUtil = new PropertyTypeDbUtil(conn);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");

			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST_OF_PROPERTIES";
			}

			// route to the appropriate method
			switch (theCommand) {

			case "LIST_OF_PROPERTIES":
				listProperties(request, response);
				break;

			case "LIST_OF_TYPES":
				listProperty_types(request, response);
				break;

			case "ADD":
				addProperty(request, response);
				break;

			default:
				listProperties(request, response);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	/*
	 * private void deleteStudent(HttpServletRequest request, HttpServletResponse
	 * response) throws Exception {
	 * 
	 * // read student id from form data String theStudentId =
	 * request.getParameter("student_id");
	 * 
	 * // delete student from database studentDbUtil.deleteStudent(theStudentId);
	 * 
	 * // send them back to "list students" page listStudents(request, response); }
	 */

	private void addProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read info from form data
		try {
			
		String address = request.getParameter("address");
		System.out.println(" property_type : " + request.getParameter("property_type") 
		+ "  ||address: " +  address + "  ||suitable_for : " 
		+ request.getParameter("suitable") + "  ||is_available : " 
		+ request.getParameter("is_available") + "  ||owner_id : " 
		+ request.getParameter("owner_id") + "  ||rented_by : " 
		+ request.getParameter("rented_by") + "  ||charge: " 
		+ request.getParameter("charge"));
		
		int property_type = Integer.parseInt(request.getParameter("property_type").trim());
		
		int suitable_for = Integer.parseInt(request.getParameter("suitable").trim());
		int is_available = Integer.parseInt(request.getParameter("is_available").trim());
		int owner_id = Integer.parseInt(request.getParameter("owner_id").trim());
		int rented_by = Integer.parseInt(request.getParameter("rented_by").trim());
		float charge = Float.parseFloat(request.getParameter("charge").trim());
		boolean isDeleted = true;
			
		Property theProperty = new Property(property_type, address, suitable_for, is_available, owner_id, rented_by,
				charge, isDeleted);
		
		System.out.println(" property_type : " + theProperty.getProperty_type() 
		+ "  ||address: " +  theProperty.getAddress() 
		+ "  ||suitable_for : " + theProperty.getSuitable_for() 
		+ "  ||is_available : " + theProperty.getIs_available() 
		+ "  ||owner_id : " + theProperty.getOwner() 
		+ "  ||rented_by : " + theProperty.getRented_by() 
		+ "  ||charge: " + theProperty.getCharge());
		
			dbUtil.addProperty(theProperty);
			
			listProperties(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * private void loadStudent(HttpServletRequest request, HttpServletResponse
	 * response) throws Exception {
	 * 
	 * // read student id from form data int theStudentId =
	 * Integer.parseInt(request.getParameter("student_id"));
	 * 
	 * // get student from database (db util) Student theStudent =
	 * studentDbUtil.getStudent(theStudentId);
	 * 
	 * // place student in the request attribute request.setAttribute("THE_STUDENT",
	 * theStudent);
	 * 
	 * // send to jsp page: update-student-form.jsp RequestDispatcher dispatcher =
	 * request.getRequestDispatcher("/update-student-form.jsp");
	 * dispatcher.forward(request, response); }
	 */

	private void listProperty_types(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Property_Type> types = propertyTypeDbUtil.getProperty_Types();

		// add to the request
		request.setAttribute("TYPES", types);

		// send to the view page (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/add-property.jsp");
		dispatcher.forward(request, response);
	}

	private void listProperties(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get list from dbUtil
		String search = request.getParameter("search");
		List<GetProperty> property;
		if (isNullOrEmpty(search)) {
			property = propertyDbUtil.getProperties("");
		} else {
			property = propertyDbUtil.getProperties(search);
		}

		// add to the request
		request.setAttribute("PROPERTY_LIST", property);

		// send to the view page (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-properties.jsp");
		dispatcher.forward(request, response);
	}

	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return false;
		return true;
	}
}
