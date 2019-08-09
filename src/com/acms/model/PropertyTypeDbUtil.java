package com.acms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.acms.jdbc.Property;
import com.acms.jdbc.Property_Type;

public class PropertyTypeDbUtil {

	SqliteConUtil conn = new SqliteConUtil();

	public PropertyTypeDbUtil(SqliteConUtil con) {
		con = this.conn;
	}

	public List<Property_Type> getProperty_Types() throws Exception {

		List<Property_Type> types = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = conn.getMySQLConnection();

			// create sql statement
			String sql = "select * from `tbl_property_types` where `isDeleted` = 1 ";

			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				// retrieve data from result set row
				int property_id = myRs.getInt("type_id");
				String property_name = myRs.getString("type_name");
				boolean isDeleted = myRs.getBoolean("isDeleted");
				// create new property type object
				Property_Type tempProperty_Type = new Property_Type(property_id, property_name, isDeleted);

				// add it to the list of property types
				types.add(tempProperty_Type);
			}
			return types;

		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void addPropertyType(Property_Type theProperty_Type) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = conn.getMySQLConnection();

			// create sql for insert
			String sql = "insert into tbl_property_types (type_name) values (?)";

			myStmt = myConn.prepareStatement(sql);

			// set the param values for the property type
			myStmt.setString(1, theProperty_Type.getPropery_name());

			// execute sql insert
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void updateProperty_Type(Property_Type theProperty_Type) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = conn.getMySQLConnection();

			// create SQL update statement
			String sql = "update tbl_property_types" 
					+ " set type_name=? "
					+ " where type_id=? ";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theProperty_Type.getPropery_name());
			myStmt.setInt(2, theProperty_Type.getProperty_id());

			// execute SQL statement
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public Property_Type getProperty(int type_id) throws Exception {

		Property_Type theProperty_Type = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get connection to database
			myConn = conn.getMySQLConnection();

			// create sql to get selected owner
			String sql = "select type_name from tbl_property_types where type_id=?";

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, type_id);

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			if (myRs.next()) {
				String type_name = myRs.getString("type_name");
				boolean isDeleted = myRs.getBoolean("isDeleted");

				// use the property during construction
				theProperty_Type = new Property_Type(type_id ,type_name, isDeleted);
			} else {
				throw new Exception("Could not find owner id: " + type_id);
			}

			return theProperty_Type;
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void deleteProperty_Type(int type_id) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get connection to database
			myConn = conn.getMySQLConnection();

			// create sql to delete Property
			String sql = "update `tbl_property_types` set `isDeleted` = 0 where `type_id` = ?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, type_id);

			// execute sql statement
			myStmt.execute();
		} finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
		}
	}

	// method to close and open connection
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