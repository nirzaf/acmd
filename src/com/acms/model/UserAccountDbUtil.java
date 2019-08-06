package com.acms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import com.acms.jdbc.UserAccount;

public class UserAccountDbUtil {

	// Define datasource/connection pool for Resource Injection
	@Resource(name = "jdbc/ams")
	private DataSource dataSource;

	public UserAccountDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public UserAccount findUser(String username, String password) throws Exception {
		UserAccount theUser = null;
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {

			// get a connection
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "select * from tbl_users where username=?, password=?";

			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			if (myRs.next()) {
				// retrieve data from result set row
				int User_id = myRs.getInt("user_id");
				String Username = username;
				String Password = password;
				int User_type = myRs.getInt("user_type");
				boolean Status = myRs.getBoolean("status");

				// create new user account object
				theUser = new UserAccount(User_id, Username, Password, User_type, Status);
			} else {
				throw new Exception("Username or Password is Invalid! ");
			}
			return theUser;
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	// Return the list of users
	public List<UserAccount> getUserAccounts() throws Exception {

		List<UserAccount> userAccounts = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "select * from tbl_users";

			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				// retrieve data from result set row
				int user_id = myRs.getInt("user_id");
				String username = myRs.getString("username");
				String password = myRs.getString("password");
				int user_type = myRs.getInt("user_type");
				boolean status = myRs.getBoolean("status");

				// create new user account object
				UserAccount tempUserAccount = new UserAccount(user_id, username, password, user_type, status);

				// add it to the list of user accounts
				userAccounts.add(tempUserAccount);
			}

			return userAccounts;
		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	// create user account
	public void createUser(UserAccount theUser) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();

			// create sql for insert
			String sql = "insert into tbl_users " + "(username, password, user_type)" + "values (?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set the parameters
			myStmt.setString(2, theUser.getUsername());
			myStmt.setString(3, theUser.getPassword());
			myStmt.setInt(4, theUser.getUser_type());

			// execute sql insert
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	// update user account details
	public void updateUser(UserAccount theUser) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();

			// create SQL update statement
			String sql = "update tbl_users " + "set username=?, password=?, user_type=? " + "where user_id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theUser.getUsername());
			myStmt.setString(2, theUser.getPassword());
			myStmt.setInt(3, theUser.getUser_type());
			myStmt.setInt(4, theUser.getUser_id());

			// execute SQL statement
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	// activate the user account
	public void activateUser(String theUserId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// convert user id to int
			int userId = Integer.parseInt(theUserId);

			// get db connection
			myConn = dataSource.getConnection();

			// create SQL update statement
			String sql = "update tbl_users set status=1 where user_id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, userId);

			// execute SQL statement
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	// get user details by user id
	public UserAccount getUser(String userId) throws Exception {

		UserAccount theUser = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int user_id;

		try {
			// convert user id to int
			user_id = Integer.parseInt(userId);

			// get connection to database
			myConn = dataSource.getConnection();

			// create sql to get selected user
			String sql = "select * from tbl_user where user_id=?";

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, user_id);

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			if (myRs.next()) {
				String username = myRs.getString("username");
				String password = myRs.getString("password");
				int user_type = myRs.getInt("user_type");
				boolean status = myRs.getBoolean("status");

				// use the studentId during construction
				theUser = new UserAccount(username, password, user_type, status);
			} else {
				throw new Exception("Could not find student id: " + user_id);
			}

			return theUser;
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	// disable the user account
	public void deleteUser(String theUserId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// convert user id to int
			int userId = Integer.parseInt(theUserId);

			// get connection to database
			myConn = dataSource.getConnection();

			// create sql to delete/disable user
			String sql = "update tbl_users status=0 where user_id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, userId);

			// execute sql statement
			myStmt.execute();
		} finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
		}
	}

	// method to close and open connection pool
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close(); // doesn't really close it ... just puts back in connection pool
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}