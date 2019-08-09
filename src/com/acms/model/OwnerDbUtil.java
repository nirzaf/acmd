package com.acms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.acms.jdbc.Owner;

public class OwnerDbUtil {

	SqliteConUtil conn = new SqliteConUtil();

	public OwnerDbUtil(SqliteConUtil con) {
		con = this.conn;
	}

	public List<Owner> getOwners() throws Exception {

		List<Owner> owners = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = conn.getMySQLConnection();

			// create sql statement
			String sql = "select * from `tbl_owner` where `isDeleted` = 1 order by `first_name` ";

			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				// retrieve data from result set row
				int id = myRs.getInt("owner_id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String address = myRs.getString("address");
				String email = myRs.getString("email");
				String telephone = myRs.getString("telephone");
				boolean isDeleted = myRs.getBoolean("isDeleted");
				// create new owner object
				Owner tempOwner = new Owner(id, firstName, lastName, address, email, telephone, isDeleted);

				// add it to the list of owners
				owners.add(tempOwner);
			}
			return owners;

		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void addOwner(Owner theOwner) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = conn.getMySQLConnection();

			// create sql for insert
			String sql = "insert into tbl_owner " + "(first_name, last_name, address, email, telephone)"
					+ "values (?, ?, ?, ?,?)";

			myStmt = myConn.prepareStatement(sql);

			// set the param values for the owner
			myStmt.setString(1, theOwner.getFirst_name());
			myStmt.setString(2, theOwner.getLast_name());
			myStmt.setString(3, theOwner.getAddress());
			myStmt.setString(4, theOwner.getEmail());
			myStmt.setString(5, theOwner.getTelephone());

			// execute sql insert
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void updateOwner(Owner theOwner) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = conn.getMySQLConnection();

			// create SQL update statement
			String sql = "update tbl_owner " + "set first_name=?, last_name=?, address=? ,email=? , telephone=? "
					+ "where owner_id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theOwner.getFirst_name());
			myStmt.setString(2, theOwner.getLast_name());
			myStmt.setString(3, theOwner.getAddress());
			myStmt.setString(4, theOwner.getEmail());
			myStmt.setString(5, theOwner.getTelephone());
			myStmt.setInt(6, theOwner.getOwner_id());

			// execute SQL statement
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public Owner getOwner(int ownerId) throws Exception {

		Owner theOwner = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get connection to database
			myConn = conn.getMySQLConnection();

			// create sql to get selected owner
			String sql = "select * from tbl_owner where owner_id=?";

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, ownerId);

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			if (myRs.next()) {
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String address = myRs.getString("address");
				String email = myRs.getString("email");
				String telephone = myRs.getString("telephone");
				boolean isDeleted = myRs.getBoolean("isDeleted");

				// use the ownerId during construction
				theOwner = new Owner(ownerId, firstName, lastName, address, email, telephone, isDeleted);
			} else {
				throw new Exception("Could not find owner id: " + ownerId);
			}

			return theOwner;
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void deleteOwner(String theOwnerId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// convert owner id to int
			int ownerId = Integer.parseInt(theOwnerId);

			// get connection to database
			myConn = conn.getMySQLConnection();

			// create sql to delete Owner
			String sql = "update `tbl_owner` set `isDeleted` = 0 where `owner_id` = ?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, ownerId);

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
				myConn.close(); 
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}