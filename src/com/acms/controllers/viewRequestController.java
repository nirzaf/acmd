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

import com.acms.jdbc.GetRequest;
import com.acms.jdbc.ViewRequest;
import com.acms.model.ViewReqDbUtil;

@WebServlet("/viewRequestController")
public class viewRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ViewReqDbUtil viewReqDbUtil;

	@Resource(name = "jdbc/ams")
	private DataSource ds;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		try {
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
				theCommand = "LIST";
			}

			// route to the appropriate method
			switch (theCommand) {

			case "LIST":
				listRequest(request, response);
				break;

			case "ACCEPT":
				acceptRequest(request, response);
				
			case "VIEW":
				viewRequest(request, response);

			case "MYLIST":
				myRequestList(request, response);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void acceptRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getParameter("request_id")!=null) {
		// read id from form data
		int request_id = Integer.parseInt(request.getParameter("request_id"));

		// update payment
		viewReqDbUtil.viewRequestStatus(request_id);

		// send them back to my Properties page
		listRequest(request, response);
		}
	}

	private void listRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getParameter("owner_id")!=null) {
		int ownerId = Integer.parseInt(request.getParameter("owner_id"));

		// get list from dbUtil
		List<GetRequest> getRequest = viewReqDbUtil.getRequest(ownerId);

		// add the requests to REQ_LIST
		request.setAttribute("REQ_LIST", getRequest);

		// send to the view page (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/request-list.jsp");
		dispatcher.forward(request, response);
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
		myRequestList(request, response);
	}

	public void myRequestList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if(request.getParameter("student_id")!= null) {
		int student_id = Integer.parseInt(request.getParameter("student_id"));

		// get list from dbUtil
		List<GetRequest> getRequest = viewReqDbUtil.getMyRequest(student_id);

		// add the requests to REQ_LIST
		request.setAttribute("MYREQ_LIST", getRequest);

		// send to the view page (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/my-requests.jsp");
		dispatcher.forward(request, response);
		}
	}
}
