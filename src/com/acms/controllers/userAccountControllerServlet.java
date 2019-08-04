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

import com.acms.jdbc.Student;
import com.acms.jdbc.UserAccount;
import com.acms.model.StudentDbUtil;
import com.acms.model.UserAccountDbUtil;

/**
 * Servlet implementation class studentControllerServlet
 */
@WebServlet("/userAccountControllerServlet")
public class userAccountControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserAccountDbUtil userAccountDbUtil;

	@Resource(name = "jdbc/ams")

	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		try {
			userAccountDbUtil = new UserAccountDbUtil(dataSource);
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
				listUsers(request, response);
				break;

			case "ADD":
				createUser(request, response);
				break;

			case "LOAD":
				loadUser(request, response);
				break;

			case "UPDATE":
				updateUser(request, response);
				break;

			case "DELETE":
				deleteUser(request, response);
				break;

			default:
				listUsers(request, response);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student id from form data
		String theUserId = request.getParameter("user_id");

		// delete student from database
		userAccountDbUtil.deleteUser(theUserId);

		// send them back to "list students" page
		listUsers(request, response);
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read user info from form data
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int user_type = Integer.parseInt(request.getParameter("user_type"));
		boolean status = Boolean.getBoolean(request.getParameter("status"));

		// create a new user object
		UserAccount theUser = new UserAccount(user_id, username, password, user_type, status);

		// perform update on database
		userAccountDbUtil.updateUser(theUser);

		// send them back to the "list users" page
		listUsers(request, response);

	}

	private void loadUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read user id from form data
		String theUserId = request.getParameter("user_id");

		// get user from database (db util)
		UserAccount theUser = userAccountDbUtil.getUser(theUserId);

		// place user in the request attribute
		request.setAttribute("THE_USER", theUser);

		// send to jsp page: update-user-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-user-form.jsp");

		dispatcher.forward(request, response);
	}

	private void createUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from form data
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int user_type = Integer.parseInt(request.getParameter("user_type"));
		boolean status = false;

		// create a new user object
		UserAccount theUser = new UserAccount(username, password, user_type, status);

		// add the user to the database
		userAccountDbUtil.createUser(theUser);

		// send back to main page (the users list)
		listUsers(request, response);
	}

	// List of Users
	private void listUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get students list from dbUtil

		List<UserAccount> user = userAccountDbUtil.getUserAccounts();

		// add users to the request
		request.setAttribute("USER_LIST", user);

		// send to the view page (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-users.jsp");
		dispatcher.forward(request, response);
	}
}
