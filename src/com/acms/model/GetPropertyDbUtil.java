package com.acms.model;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import com.acms.jdbc.GetProperty;
import com.mysql.jdbc.PreparedStatement;

public class GetPropertyDbUtil {

	SqliteConUtil conn = new SqliteConUtil();

	public GetPropertyDbUtil(SqliteConUtil con) {
		con = this.conn;
	}

	public List<GetProperty> getProperties(String search) throws Exception {

		List<GetProperty> properties = new ArrayList<>();
		Connection myConn = null;
		java.sql.PreparedStatement myStmnt = null; 
		ResultSet myRs = null;
		String sql = "";

		try {
			// get a connection
			if (isNullOrEmpty(search)) {
				myConn = conn.getMySQLConnection();

				sql = "SELECT tbl_property.property_id AS property_id, " + "tbl_property_types.type_name AS type_name, "
						+ "tbl_property.address AS address, tbl_property.suitable_for AS suitable_for, "
						+ "CASE (tbl_property.is_available) "
						+ "WHEN '1' THEN 'available' ELSE 'Occupied' END AS availability, "
						+ "tbl_owner.first_name AS owner, " + "CASE tbl_property.rented_by "
						+ "WHEN '0' THEN 'none' ELSE tbl_student.first_name END as rented_by, "
						+ "tbl_property.charge AS rent " + "FROM tbl_property INNER JOIN tbl_property_types ON "
						+ "tbl_property_types.type_id = tbl_property.property_type "
						+ "INNER JOIN tbl_owner ON  tbl_property.owner = tbl_owner.owner_id "
						+ "LEFT JOIN tbl_student ON  tbl_property.rented_by = tbl_student.student_id "
						+ "WHERE tbl_property.isDeleted = 1";

				myStmnt = myConn.prepareStatement(sql);

				// execute query
				myRs = myStmnt.executeQuery();

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
				myConn = conn.getMySQLConnection();
				sql = "SELECT tbl_property.property_id AS property_id, " + "tbl_property_types.type_name AS type_name, "
						+ "tbl_property.address AS address, tbl_property.suitable_for AS suitable_for, "
						+ "CASE (tbl_property.is_available) "
						+ "WHEN '1' THEN 'available' ELSE 'Occupied' END AS availability, "
						+ "tbl_owner.first_name AS owner, " + "CASE tbl_property.rented_by "
						+ "WHEN '0' THEN 'none' ELSE tbl_student.first_name END as rented_by, "
						+ "tbl_property.charge AS rent " + "FROM tbl_property INNER JOIN tbl_property_types ON "
						+ "tbl_property_types.type_id = tbl_property.property_type "
						+ "INNER JOIN tbl_owner ON  tbl_property.owner = tbl_owner.owner_id "
						+ "LEFT JOIN tbl_student ON  tbl_property.rented_by = tbl_student.student_id "
						+ "WHERE tbl_property.isDeleted = 1 AND tbl_property_types.type_name LIKE ? OR tbl_property.address LIKE ?";

				myStmnt = myConn.prepareStatement(sql);

				myStmnt.setString(1,"%" +search +"%");
				myStmnt.setString(2,"%" +search +"%");
				
				// execute query
				myRs = myStmnt.executeQuery();

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
			close(myConn, myStmnt, myRs);
		}
	}
	
	public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
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
