package com.acms.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.acms.jdbc.Student;
import com.acms.model.SqliteConUtil;
import com.acms.model.StudentDbUtil;

@WebServlet("/studentControllerServlet")
public class studentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDbUtil;

	//private DataSource dataSource;
	
	SqliteConUtil conn = new SqliteConUtil();

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		try {
			studentDbUtil = new StudentDbUtil(conn);
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
				listStudents(request, response);
				break;

			case "ADD":
				addStudent(request, response);
				break;

			case "LOAD":
				loadStudent(request, response);
				break;

			case "UPDATE":
				updateStudent(request, response);
				break;

			case "DELETE":
				deleteStudent(request, response);
				break;

			default:
				listStudents(request, response);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student id from form data
		String theStudentId = request.getParameter("student_id");

		// delete student from database
		studentDbUtil.deleteStudent(theStudentId);

		// send them back to "list students" page
		listStudents(request, response);
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

		// send them back to the "list students" page
		listStudents(request, response);

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

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");

		// create a new student object
		Student theStudent = new Student(firstName, lastName, address, email, telephone);

		// add the student to the database
		studentDbUtil.addStudent(theStudent);

		// send back to main page (the student list)
		listStudents(request, response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get students list from dbUtil
		List<Student> student = studentDbUtil.getStudents();

		// add students to the request
		request.setAttribute("STUDENT_LIST", student);

		// send to the view page (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}
}
