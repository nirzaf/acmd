package com.acms.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.acms.jdbc.Owner;
import com.acms.jdbc.Student;
import com.acms.jdbc.UserAccount;
import com.acms.model.OwnerDbUtil;
import com.acms.model.SqliteConUtil;
import com.acms.model.StudentDbUtil;
import com.acms.model.UserAccountDbUtil;

//newly added

@WebServlet("/userAccountControllerServlet")
public class userAccountControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserAccountDbUtil userAccountDbUtil;
	private StudentDbUtil studentDbUtil;
	private OwnerDbUtil ownerDbUtil;

	SqliteConUtil conn = new SqliteConUtil();

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		try {
			userAccountDbUtil = new UserAccountDbUtil(conn);
			studentDbUtil = new StudentDbUtil(conn);
			ownerDbUtil = new OwnerDbUtil(conn);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			System.out.println(theCommand);
			// if the command is missing, then default to login user
			if (theCommand == null) {
				theCommand = "LOGIN";
			}

			// route to the appropriate method
			switch (theCommand) {
			case "LOGIN":
				userLogin(request, response);
				break;

			case "REGISTER":
				registerUser(request, response);
				break;
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			System.out.println(theCommand);
			// if the command is missing, then default to listing users
			if (theCommand == null) {
				theCommand = "LOGINPAGE";
			}

			// route to the appropriate method
			switch (theCommand) {

			case "LIST":
				listUsers(request, response);
				break;
			case "DISABLE":
				disableOrEnableUser(request, response);
				break;
			case "LOGINPAGE":
				loginPage(request, response);
				break;
			case "SIGNUP":
				signUpPage(request, response);
				break;
			case "REGISTER":
				registerUser(request, response);
				break;
			case "LOGOUT":
				signOut(request, response);
				break;
			default:
				loginPage(request, response);
				break;
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	// method for login page controller route
	private void loginPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login-form.jsp");
		dispatcher.forward(request, response);
	}

	// method for sign up page controller route
	private void signUpPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/sign-up-form.jsp");
		dispatcher.forward(request, response);
	}

	// method for sign up page controller route
	private void signOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
			}
		}
		// response.sendRedirect("login-form.jsp");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login-form.jsp");
		dispatcher.forward(request, response);
	}

	// method for authentication controller
	private void userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			Student theStudent = null;
			Owner theOwner = null;
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			// Define Error Message Incase login Failed
			String Message = "Username or Password is Invalid";

			// creating new object of User Account Class
			UserAccount userAccount = new UserAccount();

			// Setting Username and password Recieved From Login JSP file to userAccount
			userAccount.setUsername(username);
			userAccount.setPassword(password);

			// creating object for UserAccountDbUtil. 
			//This class contains main logic of the login part.
			UserAccountDbUtil uaDbUtil = new UserAccountDbUtil(conn);

			boolean validateAuth = uaDbUtil.Auth(userAccount);

			if (validateAuth) { // If validateAuth boolean is true means username and password is correct
				//destroying existing session variables 
				request.getSession().invalidate();
							
				// getting user_id and user_type value of current logged in user 
				int user_id = userAccountDbUtil.getUserId(username, password);
				int user_type = userAccountDbUtil.getUserType(user_id);
				
				//converting user_id and user_type to string 
				String userId = Integer.toString(user_id);
				String userType = Integer.toString(user_type);
								
				//create new session 
				HttpSession newSession = request.getSession(true);
				newSession.setMaxInactiveInterval(1500);
				newSession.setAttribute("username", username);
				newSession.setAttribute("user_id", user_id);
				newSession.setAttribute("user_type", userType);
				
				request.setAttribute("username", username);
				request.setAttribute("user_id", user_id);
				request.setAttribute("user_type", userType);
				Cookie loginCookie = new Cookie("user_id", userId);
				
				// setting cookie to expiry in 60 mins
				loginCookie.setMaxAge(60 * 60);
				response.addCookie(loginCookie);
				request.setAttribute("user_id", user_id);
				if (user_id > 0 && user_type == 1) {

					// get student from database (db util)
					theStudent = studentDbUtil.getStudent(user_id);

					// place student in the request attribute
					request.setAttribute("THE_STUDENT", theStudent);

					// send to jsp page: update-student-form.jsp
					RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
					dispatcher.forward(request, response);
				} else if (user_id > 0 && user_type == 2) {

					// get owner from database (db util)
					theOwner = ownerDbUtil.getOwner(user_id);

					// place owner in the request attribute
					request.setAttribute("THE_OWNER", theOwner);

					// send to jsp page: update-owner-form.jsp
					RequestDispatcher dispatcher = request.getRequestDispatcher("/update-owner-form.jsp");
					dispatcher.forward(request, response);
				} else {
					//then it is the admin account
					RequestDispatcher req = request.getRequestDispatcher("list-users-form.jsp");
					req.forward(request, response);
				}
			} else { // else username and password is incorrect return to the login page
				request.setAttribute("Message", Message);
				RequestDispatcher req = request.getRequestDispatcher("login-form.jsp");
				req.forward(request, response);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	// controller to register new user account
	private void registerUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			int user_type = Integer.parseInt(request.getParameter("account-type"));

			System.out.println("username : " + username + " Password : " + password + "  user_type : " + user_type);
			// create a new user object
			UserAccount theUser = new UserAccount(username, password, user_type);

			// add the user to the database
			userAccountDbUtil.createUser(theUser);

			// get user id for given username and password
			int user_id = userAccountDbUtil.getUserId(username, password);

			System.out.println(user_id);

			request.setAttribute("errMessage", theUser.getUsername());
			request.setAttribute("user_id", user_id);

			// send back to the login page
			loginPage(request, response);

		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	// controller to activate or deactivate the existing user
	private void disableOrEnableUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read user id from form data
		String theUserId = request.getParameter("user_id");

		// disable or enable user from database
		userAccountDbUtil.disableOrEnableUser(theUserId);

		// send them back to "list users" page
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
		// read user info from form data
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int user_type = Integer.parseInt(request.getParameter("user_type"));
		boolean status = false;

		// create a new user object
		UserAccount theUser = new UserAccount(username, password, user_type);

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
