package com.acms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import com.acms.jdbc.GetRequest;

public class ViewReqDbUtil {

	@Resource(name="jdbc/ams")
	private DataSource ds;
	ConUtil c = new ConUtil();

	public ViewReqDbUtil(DataSource dataSource) {
		ds = dataSource;
	}

	public List<GetRequest> getRequest(int ownerId) throws Exception {

		List<GetRequest> request = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn =  ds.getConnection();

			// create sql statement
			String sql = "SELECT tbl_view_request.request_id AS Id, tbl_student.first_name AS Requested_by, "
					+ "tbl_property_types.type_name As Requested_property, "
					+ "tbl_view_request.requested_date AS Requested_date, "
					+ "tbl_view_request.date_of_view As Date_of_view, "
					+ "tbl_view_request.status FROM tbl_view_request " 
					+ "INNER JOIN tbl_student ON tbl_student.student_id = tbl_view_request.requested_by " 
					+ "INNER JOIN tbl_property_types ON tbl_property_types.type_id = tbl_view_request.requested_property " 
					+ "INNER JOIN tbl_property ON tbl_property.property_id = tbl_view_request.requested_property" 
					+ "Where tbl_view_request.isDeleted = 1 AND tbl_property.owner = ?";

			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, ownerId);

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				// retrieve data from result set row
				int Request_id = myRs.getInt("Id");
				String Requested_by = myRs.getString("Requested_by");
				String Requested_property = myRs.getString("Requested_property");
				String Requested_date = myRs.getString("Requested_date");
				String Date_of_view = myRs.getString("Date_of_view");
				boolean status = myRs.getBoolean("status");
				// create new property type object
				GetRequest getRequest = new GetRequest(Request_id, Requested_by, Requested_property, Requested_date, Date_of_view, status);

				// add it to the list of property types
				request.add(getRequest);
			}
			return request;

		} finally {
			// close JDBC objects
			c.close(myConn, myStmt, myRs);
		}
	}
}