package com.acms.model;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import java.sql.Connection;
import com.acms.jdbc.GetProperty;

public class GetPropertyDbUtil {

	@Resource(name="jdbc/ams")
	private DataSource ds;
	ConUtil c = new ConUtil();

	public GetPropertyDbUtil(DataSource dataSource) {
		ds = dataSource;
	}

	public List<GetProperty> getProperties(String search, int Available) throws Exception {

		List<GetProperty> properties = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String sql = "";

		try {
			// get a connection
			if (isNullOrEmpty(search)) {
				myConn = ds.getConnection();
				
				if(Available == 0) {
				sql = "SELECT tbl_property.property_id AS property_id, " + "tbl_property_types.type_name AS type_name, "
						+ "tbl_property.address AS address, tbl_property.suitable_for AS suitable_for, "
						+ "CASE (tbl_property.is_available) "
						+ "WHEN '1' THEN 'available' ELSE 'occupied' END AS availability, "
						+ "tbl_owner.first_name AS owner, " 
						+ "CASE tbl_property.rented_by "
						+ "WHEN '0' THEN 'none' ELSE tbl_student.first_name END as rented_by, "
						+ "tbl_property.charge AS rent " 
						+ "FROM tbl_property INNER JOIN tbl_property_types ON "
						+ "tbl_property_types.type_id = tbl_property.property_type "
						+ "INNER JOIN tbl_owner ON  tbl_property.owner = tbl_owner.owner_id "
						+ "LEFT JOIN tbl_student ON tbl_property.rented_by = tbl_student.student_id "
						+ "WHERE tbl_property.isDeleted = 1 AND tbl_property.status = 1";
				}else {
					sql = "SELECT tbl_property.property_id AS property_id, " + "tbl_property_types.type_name AS type_name, "
							+ "tbl_property.address AS address, tbl_property.suitable_for AS suitable_for, "
							+ "CASE (tbl_property.is_available) "
							+ "WHEN '1' THEN 'available' ELSE 'occupied' END AS availability, "
							+ "tbl_owner.first_name AS owner, " 
							+ "CASE tbl_property.rented_by "
							+ "WHEN '0' THEN 'none' ELSE tbl_student.first_name END as rented_by, "
							+ "tbl_property.charge AS rent " 
							+ "FROM tbl_property INNER JOIN tbl_property_types ON "
							+ "tbl_property_types.type_id = tbl_property.property_type "
							+ "INNER JOIN tbl_owner ON  tbl_property.owner = tbl_owner.owner_id "
							+ "LEFT JOIN tbl_student ON tbl_property.rented_by = tbl_student.student_id "
							+ "WHERE tbl_property.isDeleted = 1 AND tbl_property.status = 1 "
							+ "AND tbl_property.is_available =1";
				}
				myStmt = myConn.prepareStatement(sql);

				// execute query
				myRs = myStmt.executeQuery();

				// process result set
				while (myRs.next()) {
					// retrieve data from result set row
					int property_id = myRs.getInt("property_id");
					String type_name = myRs.getString("type_name");
					String address = myRs.getString("address");
					int suitable_for = myRs.getInt("suitable_for");
					String availability = myRs.getString("availability");
					String owner = myRs.getString("owner");
					String rented_by = myRs.getString("rented_by");
					float rent = myRs.getFloat("rent");

					// create new property object
					GetProperty tempProperty = new GetProperty(property_id, type_name, address, suitable_for,
							availability, owner, rented_by, rent);

					// add it to the list of properties
					properties.add(tempProperty);				
				}
			} else {
				myConn = ds.getConnection();
				if(Available == 0) {
				sql = 	"SELECT tbl_property.property_id AS property_id, " 
						+ "tbl_property_types.type_name AS type_name, "
						+ "tbl_property.address AS address, "
						+ "tbl_property.suitable_for AS suitable_for, "
						+ "CASE (tbl_property.is_available) "
						+ "WHEN '1' THEN 'available' ELSE 'occupied' END AS availability, "
						+ "tbl_owner.first_name AS owner, " 
						+ "CASE tbl_property.rented_by "
						+ "WHEN '0' THEN 'none' ELSE tbl_student.first_name END as rented_by, "
						+ "tbl_property.charge AS rent " 
						+ "FROM tbl_property INNER JOIN tbl_property_types ON "
						+ "tbl_property_types.type_id = tbl_property.property_type "
						+ "INNER JOIN tbl_owner ON  tbl_property.owner = tbl_owner.owner_id "
						+ "LEFT JOIN tbl_student ON  tbl_property.rented_by = tbl_student.student_id "
						+ "WHERE tbl_property.isDeleted = 1 AND tbl_property.status = 1 "
						+ "AND tbl_property_types.type_name LIKE ? "
						+ "OR tbl_property.address LIKE ? "
						+ "OR tbl_student.first_name LIKE ? "
						+ "OR tbl_owner.first_name LIKE ? "
						+ "OR tbl_property.suitable_for LIKE ? ";
				}else {
					sql = 	"SELECT tbl_property.property_id AS property_id, " 
							+ "tbl_property_types.type_name AS type_name, "
							+ "tbl_property.address AS address, "
							+ "tbl_property.suitable_for AS suitable_for, "
							+ "CASE (tbl_property.is_available) "
							+ "WHEN '1' THEN 'available' ELSE 'occupied' END AS availability, "
							+ "tbl_owner.first_name AS owner, " 
							+ "CASE tbl_property.rented_by "
							+ "WHEN '0' THEN 'none' ELSE tbl_student.first_name END as rented_by, "
							+ "tbl_property.charge AS rent " 
							+ "FROM tbl_property INNER JOIN tbl_property_types ON "
							+ "tbl_property_types.type_id = tbl_property.property_type "
							+ "INNER JOIN tbl_owner ON  tbl_property.owner = tbl_owner.owner_id "
							+ "LEFT JOIN tbl_student ON  tbl_property.rented_by = tbl_student.student_id "
							+ "WHERE tbl_property.isDeleted = 1 AND tbl_property.status = 1 "
							+ "AND tbl_property.is_available = 1 "
							+ "AND tbl_property_types.type_name LIKE ? "
							+ "OR tbl_property.address LIKE ? "
							+ "OR tbl_student.first_name LIKE ? "
							+ "OR tbl_owner.first_name LIKE ? "
							+ "OR tbl_property.suitable_for LIKE ? ";
				}
				myStmt = myConn.prepareStatement(sql);

				myStmt.setString(1,"%" +search +"%");
				myStmt.setString(2,"%" +search +"%");
				myStmt.setString(3,"%" +search +"%");
				myStmt.setString(4,"%" +search +"%");
				myStmt.setString(5,"%" +search +"%");
				
				// execute query
				myRs = myStmt.executeQuery();

				// process result set
				while (myRs.next()) {
					// retrieve data from result set row
					int property_id = myRs.getInt("property_id");
					String type_name = myRs.getString("type_name");
					String address = myRs.getString("address");
					int suitable_for = myRs.getInt("suitable_for");
					String availability = myRs.getString("availability");
					String owner = myRs.getString("owner");
					String rented_by = myRs.getString("rented_by");
					float rent = myRs.getFloat("rent");

					// create new property object
					GetProperty tempProperty = new GetProperty(property_id, type_name, address, suitable_for,
							availability, owner, rented_by, rent);

					// add it to the list of properties
					properties.add(tempProperty);
				}
			}
			return properties;
		} finally {
			// close JDBC objects
			c.close(myConn, myStmt, myRs);
		}
	}
	
	public List<GetProperty> getMyProperties(int owner_id) throws Exception {

		List<GetProperty> properties = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
				myConn = ds.getConnection();
				
				String sql = "SELECT tbl_property.property_id AS property_id, " 
						+ "tbl_property_types.type_name AS type_name, "
						+ "tbl_property.address AS address, tbl_property.suitable_for AS suitable_for, "
						+ "CASE (tbl_property.is_available) WHEN '1' THEN 'available' ELSE 'occupied' END AS availability, "
						+ "tbl_property.owner AS owner_id, "
						+ "tbl_owner.first_name AS owner, " 
						+ "CASE tbl_property.rented_by WHEN '0' THEN 'none' ELSE tbl_student.first_name END AS rented_by, "
						+ "tbl_property.charge AS rent, " 
						+ "tbl_property.status AS status "
						+ "FROM tbl_property "
						+ "INNER JOIN tbl_property_types ON tbl_property_types.type_id = tbl_property.property_type "
						+ "INNER JOIN tbl_owner ON  tbl_property.owner = tbl_owner.owner_id "
						+ "LEFT JOIN tbl_student ON  tbl_property.rented_by = tbl_student.student_id "
						+ "WHERE tbl_property.isDeleted = 1 AND tbl_property.owner = ?";
				
				myStmt = myConn.prepareStatement(sql);

				myStmt.setInt(1, owner_id);
				
				// execute query
				myRs = myStmt.executeQuery();

				// process result set
				while (myRs.next()) {
					// retrieve data from result set row
					int property_id = myRs.getInt("property_id");
					String type_name = myRs.getString("type_name");
					String address = myRs.getString("address");
					int suitable_for = myRs.getInt("suitable_for");
					String availability = myRs.getString("availability");
					int ownerId = myRs.getInt("owner_id");
					String owner = myRs.getString("owner");
					String rented_by = myRs.getString("rented_by");
					float rent = myRs.getFloat("rent");
					boolean status = myRs.getBoolean("status");

					// create new property object
					GetProperty tempProperty = new GetProperty(property_id, type_name, address, suitable_for,
							availability, ownerId, owner, rented_by, rent, status);

					// add it to the list of properties
					properties.add(tempProperty);
				}
			return properties;
		} finally {
			// close JDBC objects
			c.close(myConn, myStmt, myRs);
		}
	}
	
	
	public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }
}
