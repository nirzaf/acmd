package com.acms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.acms.jdbc.Property;

public class PropertyDbUtil {

	SqliteConUtil conn = new SqliteConUtil();

	public PropertyDbUtil(SqliteConUtil con) {
		con = this.conn;
	}

	public List<Property> getProperties() throws Exception {

		List<Property> properties = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = conn.getMySQLConnection();

			// create sql statement
			String sql = "select * from `tbl_property` where `isDeleted` = 1 ";

			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				// retrieve data from result set row
				int property_id = myRs.getInt("property_id");
				int property_type = myRs.getInt("property_type");
				int suitable_for = myRs.getInt("suitable_for");
				int is_available = myRs.getInt("is_available");
				int owner = myRs.getInt("owner");
				int rented_by = myRs.getInt("rented_by");
				float charge = myRs.getFloat("charge");
				boolean isDeleted = myRs.getBoolean("isDeleted");
				// create new property object
				Property tempProperty = new Property(property_id,property_type,suitable_for,is_available,owner,rented_by, charge , isDeleted);

				// add it to the list of properties
				properties.add(tempProperty);
			}
			return properties;

		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void addProperty(Property theProperty) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = conn.getMySQLConnection();

			// create sql for insert
			String sql = "insert into tbl_property " + "(property_type, suitable_for, is_available, owner, rented_by, charge)"
					+ "values (?, ?, ?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set the param values for the owner
			myStmt.setInt(1, theProperty.getProperty_type());
			myStmt.setInt(2, theProperty.getSuitable_for());
			myStmt.setInt(3, theProperty.getIs_available());
			myStmt.setInt(4, theProperty.getOwner());
			myStmt.setInt(5, theProperty.getRented_by());
			myStmt.setFloat(6, theProperty.getCharge());

			// execute sql insert
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void updateProperty(Property theProperty) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = conn.getMySQLConnection();

			// create SQL update statement
			String sql = "update tbl_property " + "set property_type=?, suitable_for=?, is_available=? ,owner=? , rented_by=?, charge=? "
					+ "where property_id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, theProperty.getProperty_type());
			myStmt.setInt(2, theProperty.getSuitable_for());
			myStmt.setInt(3, theProperty.getIs_available());
			myStmt.setInt(4, theProperty.getOwner());
			myStmt.setInt(5, theProperty.getRented_by());
			myStmt.setFloat(6, theProperty.getCharge());
			myStmt.setInt(7, theProperty.getProperty_id());

			// execute SQL statement
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public Property getProperty(int property_id) throws Exception {

		Property theProperty = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get connection to database
			myConn = conn.getMySQLConnection();

			// create sql to get selected owner
			String sql = "select * from tbl_property where property_id=?";

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, property_id);

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			if (myRs.next()) {
				int property_type = myRs.getInt("property_type");
				int suitable_for = myRs.getInt("suitable_for");
				int is_available = myRs.getInt("is_available");
				int owner = myRs.getInt("owner");
				int rented_by = myRs.getInt("rented_by");
				float charge = myRs.getFloat("charge");
				boolean isDeleted = myRs.getBoolean("isDeleted");

				// use the property during construction
				theProperty = new Property(property_id,property_type,suitable_for,is_available,owner,rented_by, charge , isDeleted);
			} else {
				throw new Exception("Could not find owner id: " + property_id);
			}

			return theProperty;
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void deleteProperty(int property_id) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get connection to database
			myConn = conn.getMySQLConnection();

			// create sql to delete Property
			String sql = "update `tbl_property` set `isDeleted` = 0 where `property_id` = ?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, property_id);

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