package com.acms.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.acms.jdbc.GetProperty;
import com.acms.jdbc.Property;
import com.acms.jdbc.Property_Type;
import com.acms.jdbc.Student;
import com.acms.jdbc.ViewRequest;
import com.acms.model.GetPropertyDbUtil;
import com.acms.model.PropertyDbUtil;
import com.acms.model.PropertyTypeDbUtil;
import com.acms.model.StudentDbUtil;
import com.acms.model.ViewReqDbUtil;
import com.acms.model.ConUtil;

@WebServlet("/propertyController")
public class propertyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/ams")
	private DataSource ds;

	private GetPropertyDbUtil propertyDbUtil;
	private PropertyTypeDbUtil propertyTypeDbUtil;
	private StudentDbUtil studentDbUtil;
	private PropertyDbUtil dbUtil;
	private ViewReqDbUtil viewReqDbUtil;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		try {
			propertyDbUtil = new GetPropertyDbUtil(ds);
			propertyTypeDbUtil = new PropertyTypeDbUtil(ds);
			studentDbUtil = new StudentDbUtil(ds);
			dbUtil = new PropertyDbUtil(ds);
			viewReqDbUtil = new ViewReqDbUtil(ds);
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

			case "LOAD":
				loadProperty(request, response);
				break;

			case "VIEW":
				viewRequest(request, response);
				break;

			case "MYLIST":
				myProperties(request, response);
				break;

			case "LIST_OF_TYPES":
				listProperty_types(request, response);
				break;

			case "PAY":
				makePayment(request, response);
				break;

			case "ADD":
				addProperty(request, response);
				break;

			case "EDIT":
				updateProperty(request, response);
				break;

			case "UPDATE":
				loadMyProperty(request, response);
				break;

			case "DELETE":
				deleteProperty(request, response);
				break;

			default:
				listProperties(request, response);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void viewRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read id from form data
		int property_id = Integer.parseInt(request.getParameter("property_id"));
		String viewDate = request.getParameter("date_of_view");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String currentDate = dtf.format(now);
		int user_id = Integer.parseInt(request.getParameter("user_id").trim());

		ViewRequest viewRequest = new ViewRequest(user_id, property_id, currentDate, viewDate);

		// add to database
		viewReqDbUtil.addViewRequest(viewRequest);

		// send them back to "property list" page
		listProperties(request, response);
	}

	private void makePayment(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read id from form data
		int property_id = Integer.parseInt(request.getParameter("property_id"));

		// update payment
		dbUtil.updatePayment(property_id);

		// send them back to my Properties page
		myProperties(request, response);
	}

	private void addProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read info from form data
		try {
			int property_type = Integer.parseInt(request.getParameter("property_type").trim());
			String address = request.getParameter("address");
			int suitable_for = Integer.parseInt(request.getParameter("suitable").trim());
			int is_available = Integer.parseInt(request.getParameter("is_available").trim());
			int owner_id = Integer.parseInt(request.getParameter("owner_id").trim());
			int rented_by = Integer.parseInt(request.getParameter("rented_by").trim());
			float charge = Float.parseFloat(request.getParameter("charge").trim());
			boolean isDeleted = true;

			Property theProperty = new Property(property_type, address, suitable_for, is_available, owner_id, rented_by,
					charge, isDeleted);

			dbUtil.addProperty(theProperty);

			myProperties(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read id from form data
		int property_id = Integer.parseInt(request.getParameter("property_id"));

		// update payment
		dbUtil.deleteProperty(property_id);

		// send them back to my Properties page
		myProperties(request, response);
	}

	private void updateProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// read id from form data
			int property_id = Integer.parseInt(request.getParameter("property_id"));
			int property_type = Integer.parseInt(request.getParameter("property_type").trim());
			String address = request.getParameter("address");
			int suitable_for = Integer.parseInt(request.getParameter("suitable_for").trim());
			int is_available = 0 ; 
			int rented_by = Integer.parseInt(request.getParameter("rented_by").trim());
			if(rented_by ==0) is_available = 1; 			
			float charge = Float.parseFloat(request.getParameter("charge").trim());
			
			String input = " Property Id : " + request.getParameter("property_id") +
						   " Property Type : " + request.getParameter("property_type") +
						   " address : "  + request.getParameter("address") +
						   " suitable_for : " + suitable_for +
						   " is_available " + is_available +
						   " rented_by " + request.getParameter("rented_by") +
						   " charge "  + request.getParameter("charge");
			
			System.out.println(input);
			
			
			Property theProperty = new Property(property_id, property_type, address, suitable_for, is_available, rented_by, charge);
			
			dbUtil.updateProperty(theProperty);
			
			// send them back to my Properties page
			myProperties(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read id from form data
		int propertyId = Integer.parseInt(request.getParameter("property_id"));

		// get property from database (db util)
		GetProperty theProperty = dbUtil.getProperty(propertyId);

		// place student in the request attribute
		request.setAttribute("PROPERTY", theProperty);

		// send to jsp page: update-property-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-property-form.jsp");

		dispatcher.forward(request, response);
	}

	private void loadMyProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read id from form data
		int propertyId = Integer.parseInt(request.getParameter("property_id"));

		// get property from database (db util)
		Property theProperty = dbUtil.loadProperty(propertyId);
		GetProperty property = dbUtil.getProperty(propertyId);
		List<Student> student = studentDbUtil.getStudents();

		// place property in the request attribute
		request.setAttribute("MYPROPERTY", theProperty);
		request.setAttribute("PROPERTY", property);
		// add students to the request
		request.setAttribute("STUDENT_LIST", student);

		// send to jsp page: update-property-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-my-property.jsp");

		dispatcher.forward(request, response);
	}

	private void listProperty_types(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Property_Type> types = propertyTypeDbUtil.getProperty_Types();

		// add to the request
		request.setAttribute("TYPES", types);

		// send to the view page (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/add-property.jsp");
		dispatcher.forward(request, response);
	}

	private void myProperties(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get list from dbUtil
		String user_id = request.getParameter("owner_id");
		List<GetProperty> property;

		property = propertyDbUtil.getMyProperties(Integer.parseInt(user_id));

		// add to the request
		request.setAttribute("PROPERTY", property);

		// send to the view page (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/my-properties.jsp");
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
