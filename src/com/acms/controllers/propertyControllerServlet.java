package com.acms.controllers;

import java.io.IOException;
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
import com.acms.model.PropertyDbUtil;
import com.acms.model.PropertyTypeDbUtil;
import com.acms.model.SqliteConUtil;

@WebServlet("/propertyControllerServlet")
public class propertyControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PropertyDbUtil propertyDbUtil;
	private PropertyTypeDbUtil propertyTypeDbUtil;

	//private DataSource dataSource;
	
	SqliteConUtil conn = new SqliteConUtil();

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
				super.init();
		try {
			propertyDbUtil = new PropertyDbUtil(conn);
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

			// if the command is missing, then default to listing propertys
			if (theCommand == null) {
				theCommand = "LIST";
			}

			// route to the appropriate method
			switch (theCommand) {

			case "LIST":
				listProperties(request, response);
				break;

			case "ADD":
				addProperty(request, response);
				break;

			case "LOAD":
				loadProperty(request, response);
				break;

			case "UPDATE":
				updateProperty(request, response);
				break;

			case "DELETE":
				deleteProperty(request, response);
				break;
				
			case "LISTOFTYPES":
				listPropertyTypes(request, response);
				break;

			default:
				listProperties(request, response);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void deleteProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read property id from form data
		int thePropertyId = Integer.parseInt(request.getParameter("property_id"));

		// delete property from database
		propertyDbUtil.deleteProperty(thePropertyId);

		// send them back to "list Properties" page
		listProperties(request, response);
	}

	private void updateProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read property info from form data
		int property_id = Integer.parseInt(request.getParameter("property_id"));
		int property_type = Integer.parseInt(request.getParameter("property_type"));
		String address = request.getParameter("address");
		int suitable_for = Integer.parseInt(request.getParameter("suitable_for"));
		int is_available = Integer.parseInt(request.getParameter("is_available"));
		int owner = Integer.parseInt(request.getParameter("owner"));
		int rented_by = Integer.parseInt(request.getParameter("rented_by"));
		float charge = Float.parseFloat(request.getParameter("charge"));
		boolean isDeleted = Boolean.parseBoolean(request.getParameter("isDeleted"));
		
		// create a new property object
		Property theProperty = new Property(property_id, property_type, address, suitable_for, is_available, owner, rented_by, charge,isDeleted);

		// perform update on database
		propertyDbUtil.updateProperty(theProperty);

		// send them back to the "list properties" page
		listProperties(request, response);

	}

	private void loadProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read property id from form data
		int thePropertyId = Integer.parseInt(request.getParameter("property_id"));

		// get property from database (db util)
		Property theProperty = propertyDbUtil.getProperty(thePropertyId);

		// place property in the request attribute
		request.setAttribute("THE_STUDENT", theProperty);

		// send to jsp page: update-property-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-property-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read property info from form data
		int property_type = Integer.parseInt(request.getParameter("property_type"));
		String address = request.getParameter("address");
		int suitable_for = Integer.parseInt(request.getParameter("suitable_for"));
		int is_available = Integer.parseInt(request.getParameter("is_available"));
		int owner = Integer.parseInt(request.getParameter("owner"));
		int rented_by = Integer.parseInt(request.getParameter("rented_by"));
		float charge = Float.parseFloat(request.getParameter("charge"));


		// create a new property object
		Property theProperty = new Property(property_type, address, suitable_for, is_available, owner, rented_by, charge);
		
		// add the property to the database
		propertyDbUtil.addProperty(theProperty);

		// send back to main page 
		listProperties(request, response);
	}

	private void listProperties(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get property list from dbUtil
		
		String search = request.getParameter("search");
		List<GetProperty> property = propertyDbUtil.getProperties(search);
				
		System.out.println("Your here now : " + search);
		
		//List<Property> property = propertyDbUtil.Properties(search);
		
		// add properties to the request
		request.setAttribute("PROPERTY", property);

		// send to the view page 
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-properties.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listPropertyTypes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get property list from dbUtil
		List<Property_Type> property_types = propertyTypeDbUtil.getProperty_Types();
		
		// add properties to the request
		request.setAttribute("PROPERTY_TYPE_LIST", property_types);

		// send to the view page 
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-property-types.jsp");
		dispatcher.forward(request, response);
	}
}