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
import com.acms.model.GetPropertyDbUtil;
import com.acms.model.SqliteConUtil;

@WebServlet("/getPropertyControllerServlet")
public class getPropertyControllerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private GetPropertyDbUtil getPropertyDbUtil;
	
	SqliteConUtil conn = new SqliteConUtil();
	
	public void init() throws ServletException {
		super.init();
		// TODO Auto-generated constructor stub
		try {
			getPropertyDbUtil = new GetPropertyDbUtil(conn);
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
				
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}
	
}
