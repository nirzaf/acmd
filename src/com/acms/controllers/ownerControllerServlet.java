package com.acms.controllers;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.acms.jdbc.Owner;
import com.acms.model.OwnerDbUtil;
import com.acms.model.SqliteConUtil;

@WebServlet("/ownerControllerServlet")
public class ownerControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private OwnerDbUtil ownerDbUtil;

	//private DataSource dataSource;
	
	SqliteConUtil conn = new SqliteConUtil();

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		try {
			ownerDbUtil = new OwnerDbUtil(conn);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");

			// if the command is missing, then default to listing owners
			if (theCommand == null) {
				theCommand = "LIST";
			}

			// route to the appropriate method
			switch (theCommand) {

			case "LIST":
				listOwners(request, response);
				break;

			case "ADD":
				addOwner(request, response);
				break;

			case "LOAD":
				loadOwner(request, response);
				break;

			case "UPDATE":
				updateOwner(request, response);
				break;

			case "DELETE":
				deleteOwner(request, response);
				break;

			default:
				listOwners(request, response);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void deleteOwner(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read owner id from form data
		String theOwnerId = request.getParameter("owner_id");

		// delete owner from database
		ownerDbUtil.deleteOwner(theOwnerId);

		// send them back to "list owners" page
		listOwners(request, response);
	}

	private void updateOwner(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read owner info from form data
		int owner_id = Integer.parseInt(request.getParameter("owner_id"));
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		boolean isDeleted = Boolean.parseBoolean(request.getParameter("isDeleted"));
		// create a new owner object
		Owner theOwner = new Owner(owner_id, firstName, lastName, address, email, telephone,isDeleted);

		// perform update on database
		ownerDbUtil.updateOwner(theOwner);

		// send them back to the "list owners" page
		listOwners(request, response);

	}

	private void loadOwner(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read owner id from form data
		int theOwnerId = Integer.parseInt(request.getParameter("owner_id"));

		// get owner from database (db util)
		Owner theOwner = ownerDbUtil.getOwner(theOwnerId);

		// place owner in the request attribute
		request.setAttribute("THE_OWNER", theOwner);

		// send to jsp page: update-owner-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-owner-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addOwner(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read owner info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");

		// create a new owner object
		Owner theOwner = new Owner(firstName, lastName, address, email, telephone);

		// add the owner to the database
		ownerDbUtil.addOwner(theOwner);

		// send back to main page (the owner list)
		listOwners(request, response);
	}

	private void listOwners(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get owners list from dbUtil
		List<Owner> owner = ownerDbUtil.getOwners();

		// add owners to the request
		request.setAttribute("OWNER_LIST", owner);

		// send to the view page (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-owners.jsp");
		dispatcher.forward(request, response);
	}
}
