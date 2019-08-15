package com.acms.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acms.jdbc.GetProperty;
import com.acms.model.GetPropertyDbUtil;
import com.acms.model.SqliteConUtil;

           //getPropertyControllerServlet
public class getPropertyControllerServlet {
	private GetPropertyDbUtil getPropertyDbUtil;
	//private PropertyTypeDbUtil propertyTypeDbUtil;

	//private DataSource dataSource;
	
	SqliteConUtil conn = new SqliteConUtil();

	public void init() throws ServletException {
		try {
			getPropertyDbUtil = new GetPropertyDbUtil(conn);
			//propertyTypeDbUtil = new PropertyTypeDbUtil(conn);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");

			// if the command is missing, then default to listing
			if (theCommand == null) {
				theCommand = "LISTOFPROPERTIES";
			}

			// route to the appropriate method
			switch (theCommand) {

			case "LISTOFPROPERTIES":
				listProperties(request, response);
				break;

			default:
				listProperties(request, response);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}
	
	private void listProperties(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// get property list from dbUtil
		List<GetProperty> property = getPropertyDbUtil.getProperties();
								
		// add properties to the request
		request.setAttribute("LISTOFPROPERTIES", property);

		// send to the view page 
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-properties.jsp");
		dispatcher.forward(request, response);
	}

}
