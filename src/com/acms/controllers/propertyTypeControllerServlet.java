package com.acms.controllers;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.acms.jdbc.Property_Type;
import com.acms.model.PropertyTypeDbUtil;
import com.acms.model.ConUtil;

@WebServlet("/propertyTypeControllerServlet")
public class propertyTypeControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name="jdbc/ams")
	private DataSource ds;
	
	private PropertyTypeDbUtil propertyTypeDbUtil;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			propertyTypeDbUtil = new PropertyTypeDbUtil(ds);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");

			// if the command is missing, then default to listing property types
			if (theCommand == null) {
				theCommand = "LIST";
			}

			// route to the appropriate method
			switch (theCommand) {

			case "LIST":
				listPropertyTypes(request, response);
				break;

			case "ADD":
				addPropertyType(request, response);
				break;

			case "LOAD":
				loadPropertyType(request, response);
				break;

			case "UPDATE":
				updateProperty(request, response);
				break;

			case "DELETE":
				deletePropertyType(request, response);
				break;

			default:
				listPropertyTypes(request, response);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void deletePropertyType(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read property type id from form data
		int thePropertyTypeId = Integer.parseInt(request.getParameter("property_type_id"));

		// delete property type from database
		propertyTypeDbUtil.deleteProperty_Type(thePropertyTypeId);

		// send them back to "list Property types" page
		listPropertyTypes(request, response);
	}

	private void updateProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read propertyType info from data
		int type_id = Integer.parseInt(request.getParameter("type_id"));
		String type_name = request.getParameter("type_name");
		boolean isDeleted = Boolean.parseBoolean(request.getParameter("isDeleted"));

		// create a new property object
		Property_Type thePropertyType = new Property_Type(type_id, type_name, isDeleted);

		// perform update on database
		propertyTypeDbUtil.updateProperty_Type(thePropertyType);

		// send them back to the "list properties" page
		listPropertyTypes(request, response);

	}

	private void loadPropertyType(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read property id from form data
		int type_id = Integer.parseInt(request.getParameter("type_id"));

		// get property from database (db util)
		Property_Type theProperty_type = propertyTypeDbUtil.getProperty_Type(type_id);

		// place property in the request attribute
		request.setAttribute("THE_PROPERTY_TYPE", theProperty_type);

		// send to jsp page: update-property-type-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-property-type-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addPropertyType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read property type info from data
		String type_name = request.getParameter("type_name");

		// create a new property type object
		Property_Type theProperty_type = new Property_Type(type_name);

		// add the property to the database
		propertyTypeDbUtil.addProperty_Type(theProperty_type);

		// send back to main page
		listPropertyTypes(request, response);
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