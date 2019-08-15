package com.acms.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.acms.jdbc.Property;

public class PropertyDbUtil {

	SqliteConUtil conn = new SqliteConUtil();

	public PropertyDbUtil(SqliteConUtil con) {
		con = this.conn;
	}

	public void addProperty(Property theProperty) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = conn.getMySQLConnection();

			System.out.println(" in Property Db Util property_type : " + theProperty.getProperty_type() 
			+ "  ||address: " + theProperty.getAddress() + "  ||suitable_for : " + theProperty.getSuitable_for() 
			+ "  ||is_available : " + theProperty.getSuitable_for() + "  ||owner_id : " + theProperty.getOwner() 
			+ "  ||rented_by : " + theProperty.getRented_by() + "  ||charge: " + theProperty.getCharge());
			
			String sql = "INSERT INTO tbl_property "
					+ "(property_type, address, suitable_for, is_available, owner, rented_by, charge, isDeleted) " 
					+ " VALUES (?,?,?,?,?,?,?,?)";
						
			myStmt = myConn.prepareStatement(sql);

			// set the param values for the owner
			myStmt.setInt(1, theProperty.getProperty_type());
			myStmt.setString(2, theProperty.getAddress());
			myStmt.setInt(3, theProperty.getSuitable_for());
			myStmt.setInt(4, theProperty.getIs_available());
			myStmt.setInt(5, theProperty.getOwner());
			myStmt.setInt(6, theProperty.getRented_by());
			myStmt.setFloat(7, theProperty.getCharge());
			myStmt.setBoolean(8, theProperty.isDeleted());

			try {
				myStmt.execute();
			} catch (SQLException e) {
			    e.printStackTrace();
			}
			
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
			String sql = "update tbl_property "
					+ "set property_type=?, address=?, suitable_for=?, is_available=? ,owner=? , rented_by=?, charge=? "
					+ "where property_id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, theProperty.getProperty_type());
			myStmt.setString(2, theProperty.getAddress());
			myStmt.setInt(3, theProperty.getSuitable_for());
			myStmt.setInt(4, theProperty.getIs_available());
			myStmt.setInt(5, theProperty.getOwner());
			myStmt.setInt(6, theProperty.getRented_by());
			myStmt.setFloat(7, theProperty.getCharge());
			myStmt.setInt(8, theProperty.getProperty_id());

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
			while (myRs.next()) {
				int property_type = myRs.getInt("property_type");
				String address = myRs.getString("address");
				int suitable_for = myRs.getInt("suitable_for");
				int is_available = myRs.getInt("is_available");
				int owner = myRs.getInt("owner");
				int rented_by = myRs.getInt("rented_by");
				float charge = myRs.getFloat("charge");
				boolean isDeleted = myRs.getBoolean("isDeleted");

				// use the property during construction
				theProperty = new Property(property_id, property_type, address, suitable_for, is_available, owner,
						rented_by, charge, isDeleted);
			} 

			return theProperty;
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public List<Property> Properties(String search) throws Exception {

		List<Property> properties = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String sql = null;

		try {
			// get connection to database
			myConn = conn.getMySQLConnection();

			System.out.println("Your here now : " + search);

			// create sql
			if (!search.isEmpty()) {
				sql = "SELECT * FROM tbl_property WHERE property_type LIKE ? OR address LIKE ?";
				// create prepared statement
				myStmt = myConn.prepareStatement(sql);

				// set params
				myStmt.setString(1, "%" + search + "%");
				myStmt.setString(2, "%" + search + "%");

			} else {
				sql = "SELECT * FROM tbl_property";
				myStmt = myConn.prepareStatement(sql);
			}

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			while (myRs.next()) {
				int property_type = myRs.getInt("property_type");
				String address = myRs.getString("address");
				int suitable_for = myRs.getInt("suitable_for");
				int is_available = myRs.getInt("is_available");
				int owner = myRs.getInt("owner");
				int rented_by = myRs.getInt("rented_by");
				float charge = myRs.getFloat("charge");

				// use the property during construction
				Property theProperty = new Property(property_type, address, suitable_for, is_available, owner,
						rented_by, charge);
				properties.add(theProperty);
			} 
			return properties;
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