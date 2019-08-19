package com.acms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.acms.jdbc.GetProperty;
import com.acms.jdbc.Property;
import com.acms.jdbc.ViewRequest;

public class PropertyDbUtil {

	@Resource(name="jdbc/ams")
	private DataSource ds;
	ConUtil c = new ConUtil();

	public PropertyDbUtil(DataSource dataSource) {
		ds = dataSource;
	}

	public void addProperty(Property theProperty) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = ds.getConnection();
	
			String query = " INSERT INTO `tbl_property`"
					+ "(`property_type`, `address`, `suitable_for`, `is_available`, `owner`, `rented_by`, `charge`, `isDeleted`) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(query);
			try {
			// set the param values for the owner
			myStmt.setInt(1, theProperty.getProperty_type());
			myStmt.setString(2, theProperty.getAddress());
			myStmt.setInt(3, theProperty.getSuitable_for());
			myStmt.setInt(4, theProperty.getIs_available());
			myStmt.setInt(5, theProperty.getOwner());
			myStmt.setInt(6, theProperty.getRented_by());
			myStmt.setFloat(7, theProperty.getCharge());
			myStmt.setBoolean(8, theProperty.isDeleted());

			// execute sql insert
			myStmt.execute();
			
			}catch(Exception ex){
				ex.printStackTrace();
			}

		} finally {
			// clean up JDBC objects
			c.close(myConn, myStmt, null);
		}
	}

	public void updateProperty(Property theProperty) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn =  ds.getConnection();

			// create SQL update statement
			String sql = "update tbl_property "
					+ "set property_type=?, address=?, suitable_for=?, is_available=? , rented_by=?, charge=? "
					+ "where property_id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, theProperty.getProperty_type());
			myStmt.setString(2, theProperty.getAddress());
			myStmt.setInt(3, theProperty.getSuitable_for());
			myStmt.setInt(4, theProperty.getIs_available());
			myStmt.setInt(5, theProperty.getRented_by());
			myStmt.setFloat(6, theProperty.getCharge());
			myStmt.setInt(7, theProperty.getProperty_id());

			// execute SQL statement
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			c.close(myConn, myStmt, null);
		}
	}
	
	public void updatePayment(int property_id) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn =  ds.getConnection();

			// create SQL update statement
			String sql = "update tbl_property set status=1 where property_id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, property_id);

			// execute SQL statement
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			c.close(myConn, myStmt, null);
		}
	}
	
	public GetProperty getProperty(int property_id) throws Exception {

		GetProperty property = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get connection to database
			myConn = ds.getConnection();

			// create sql to get selected owner
			String sql = "SELECT tbl_property.property_id AS property_id, " 
					+ "tbl_property_types.type_name AS type_name, "
					+ "tbl_property.address AS address, tbl_property.suitable_for AS suitable_for, "
					+ "CASE (tbl_property.is_available) "
					+ "WHEN '1' THEN 'available' ELSE 'occupied' END AS availability, "
					+ "tbl_owner.first_name AS owner, " 
					+ "CASE tbl_property.rented_by "
					+ "WHEN '0' THEN 'none' ELSE tbl_student.first_name END as rented_by, "
					+ "tbl_property.charge AS rent " 
					+ "FROM tbl_property "
					+ "INNER JOIN tbl_property_types ON tbl_property_types.type_id = tbl_property.property_type "
					+ "INNER JOIN tbl_owner ON  tbl_property.owner = tbl_owner.owner_id "
					+ "LEFT JOIN tbl_student ON  tbl_property.rented_by = tbl_student.student_id "
					+ "WHERE tbl_property.property_id =? AND tbl_property.isDeleted = 1";

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, property_id);

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			while (myRs.next()) {
				String type_name = myRs.getString("type_name");
				String address = myRs.getString("address");
				int suitable_for = myRs.getInt("suitable_for");
				String availability = myRs.getString("availability");
				String owner = myRs.getString("owner");
				String rented_by = myRs.getString("rented_by");
				float rent = myRs.getFloat("rent");
				
				// use the property during construction
				property= new GetProperty(property_id, type_name, address, suitable_for,
						availability, owner, rented_by, rent);		
			}

			return property;
		} finally {
			// clean up JDBC objects
			c.close(myConn, myStmt, myRs);
		}
	}

	
	public Property loadProperty(int property_id) throws Exception {

		Property property = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get connection to database
			myConn = ds.getConnection();

			// create sql to get selected owner
			String sql = "SELECT * FROM tbl_property WHERE property_id =? AND isDeleted = 1";

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
				int availability = myRs.getInt("is_available");
				int rented_by = myRs.getInt("rented_by");
				float rent = myRs.getFloat("charge");
				
				// use the property during construction
				property= new Property(property_id, property_type, address, suitable_for, availability, rented_by, rent);		
			}

			return property;
		} finally {
			c.close(myConn, myStmt, myRs);
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
			myConn =  ds.getConnection();

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
			c.close(myConn, myStmt, myRs);
		}
	}

	public void deleteProperty(int property_id) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get connection to database
			myConn = ds.getConnection();

			// create sql to delete Property
			String sql = "update `tbl_property` set `isDeleted` = 0 where `property_id` = ?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, property_id);

			// execute sql statement
			myStmt.executeUpdate();
		} finally {
			// clean up JDBC code
			c.close(myConn, myStmt, null);
		}
	}
}