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

	@Resource(name="jdbc/ams")
	private DataSource ds;
	ConUtil c = new ConUtil();

	public UserAccountDbUtil(DataSource dataSource) {
		ds = dataSource;
	}

	public boolean Auth(UserAccount theUser) {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		// Keeping user entered values in temporary variables.
		String Username = theUser.getUsername();
		String Password = theUser.getPassword();

		/*
		 * System.out.println("User entered username : " + Username);
		 * System.out.println("User entered password : " + Password);
		 */

		// Temporary Strings to hold username and password fetched from database
		String dbUsername = "";
		String dbPassword = "";

		try {
			// get a connection
			myConn = ds.getConnection();

			// create sql statement
			String sql = "select username, password from tbl_users where status=1";

			// Prepare statement for db connection
			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				dbUsername = myRs.getString("username");
				dbPassword = myRs.getString("password");

				/*
				 * System.out.println("username retrieved from db : " + dbUsername);
				 * System.out.println("password retrieved from db : " + dbPassword);
				 */

				// Validate the username and password by matching with db username and password
				if (dbUsername.equals(Username) && dbPassword.equals(Password)) {
					System.out.println("Username and Password Validated");
					return true;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			c.close(myConn, myStmt, myRs);
		}
		return false;
	}
	
	// Return the list of users
	public List<UserAccount> getUserAccounts() throws Exception {

		List<UserAccount> userAccounts = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = ds.getConnection();

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
			c.close(myConn, myStmt, myRs);
		}
	}

	// create user account
	public void createUser(UserAccount theUser) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = ds.getConnection();

			// create sql for insert
			String sql = "insert into tbl_users " + "(username, password, user_type)" + "values (?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set the parameters
			myStmt.setString(1, theUser.getUsername());
			myStmt.setString(2, theUser.getPassword());
			myStmt.setInt(3, theUser.getUser_type());
			// execute sql insert
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			c.close(myConn, myStmt, null);
		}
	}

	// update user account details
	public void updateUser(UserAccount theUser) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = ds.getConnection();

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
			c.close(myConn, myStmt, null);
		}
	}

	// disable or the user account
	public void disableOrEnableUser(String theUserId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		String sql = null;

		try {
			// convert user id to int
			int userId = Integer.parseInt(theUserId);

			// get connection to database
			myConn = ds.getConnection();

			// create sql to delete/disable user
			sql = "update tbl_users SET status = CASE WHEN status = 0 THEN 1 WHEN status = 1 THEN 0 END Where user_id =?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, userId);

			// execute sql statement
			myStmt.execute();
		} finally {
			// clean up JDBC code
			c.close(myConn, myStmt, null);
		}
	}

//	// activate the user account
//	public void activateUser(String theUserId) throws Exception {
//
//		Connection myConn = null;
//		PreparedStatement myStmt = null;
//
//		try {
//			// convert user id to int
//			int userId = Integer.parseInt(theUserId);
//
//			// get db connection
//			myConn = conn.getMySQLConnection();
//
//			// create SQL update statement
//			String sql = "update tbl_users set status=1 where user_id=?";
//
//			// prepare statement
//			myStmt = myConn.prepareStatement(sql);
//
//			// set params
//			myStmt.setInt(1, userId);
//
//			// execute SQL statement
//			myStmt.execute();
//		} finally {
//			// clean up JDBC objects
//			close(myConn, myStmt, null);
//		}
//	}

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
			myConn = ds.getConnection();

			// create sql to get selected user
			String sql = "select * from tbl_users where user_id=?";

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
			c.close(myConn, myStmt, myRs);
		}
	}

	// get user_id by user name and password
	public int getUserId(String username, String password) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int user_id = 0;

		try {
			// get connection to database
			myConn = ds.getConnection();
			
			// create sql to get selected user
			String sql = "select user_id from tbl_users where username=? AND password=?";

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, username);
			myStmt.setString(2, password);

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			if (myRs.next()) {
				user_id = myRs.getInt("user_id");
			} else {
				throw new Exception("Could not find user id for given username & password");
			}

			return user_id;

		} finally {
			// clean up JDBC objects
			c.close(myConn, myStmt, myRs);
		}
	}

	// get user_type by user id
	public int getUserType(int user_id) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get connection to database
			myConn = ds.getConnection();

			// create sql to get selected user
			String sql = "select user_type from tbl_users where user_id=?";

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, user_id);

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			if (myRs.next()) {
				user_id = myRs.getInt("user_type");
			} else {
				throw new Exception("Could not find user type for given user id");
			}

			return user_id;

		} finally {
			// clean up JDBC objects
			c.close(myConn, myStmt, myRs);
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
			myConn = ds.getConnection();

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
			c.close(myConn, myStmt, null);
		}
	}
}