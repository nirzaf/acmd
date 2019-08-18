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

import com.acms.jdbc.GetRequest;
import com.acms.jdbc.Student;
import com.acms.model.StudentDbUtil;
import com.acms.model.ViewReqDbUtil;

@WebServlet("/viewRequestController")
public class viewRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDbUtil;
	private ViewReqDbUtil viewRequest;
	
	@Resource(name="jdbc/ams")
	private DataSource ds;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		try {
			studentDbUtil = new StudentDbUtil(ds);
			viewRequest = new ViewReqDbUtil(ds);
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

			default:
				listRequest(request, response);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student info from form data
		int student_id = Integer.parseInt(request.getParameter("student_id"));
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		boolean isDeleted = Boolean.parseBoolean(request.getParameter("isDeleted"));
		// create a new student object
		Student theStudent = new Student(student_id, firstName, lastName, address, email, telephone,isDeleted);

		// perform update on database
		studentDbUtil.updateStudent(theStudent);

		// send them back to the student's profile page
		loadProfile(request, response);

	}

	private void loadProfile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student id from form data
		int theStudentId = Integer.parseInt(request.getParameter("student_id"));

		// get student from database (db util)
		Student theStudent = studentDbUtil.getStudent(theStudentId);

		// place student in the request attribute
		request.setAttribute("STUDENT", theStudent);

		// send to student's profile page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/student-profile.jsp");
		dispatcher.forward(request, response);
	}
	
	
	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student id from form data
		int theStudentId = Integer.parseInt(request.getParameter("student_id"));

		// get student from database (db util)
		Student theStudent = studentDbUtil.getStudent(theStudentId);

		// place student in the request attribute
		request.setAttribute("THE_STUDENT", theStudent);

		// send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}

	private void listRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int ownerId = Integer.parseInt(request.getParameter("owner_id"));
		System.out.println(ownerId);
		
		// get list from dbUtil
		List<GetRequest> getRequest = viewRequest.getRequest(ownerId);

		// add the requests to REQ_LIST
		request.setAttribute("REQ_LIST", getRequest);

		// send to the view page (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/request-list.jsp");
		dispatcher.forward(request, response);
	}
}
