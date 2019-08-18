package com.acms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import com.acms.jdbc.GetRequest;
import com.acms.jdbc.ViewRequest;

public class ViewReqDbUtil {

	@Resource(name="jdbc/ams")
	private DataSource ds;
	ConUtil c = new ConUtil();

	public ViewReqDbUtil(DataSource dataSource) {
		ds = dataSource;
	}

	public void addViewRequest(ViewRequest viewRequest) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = ds.getConnection();
	
			String query = "INSERT INTO `tbl_view_request`"
							+ "(`requested_by`, `requested_property`, `requested_date`, `date_of_view`) "
							+ "VALUES (?,?,?,?)";

			myStmt = myConn.prepareStatement(query);
			try {
			// set the param values for the owner
			myStmt.setInt(1, viewRequest.getRequested_by());
			myStmt.setInt(2, viewRequest.getRequested_property());
			myStmt.setString(3, viewRequest.getRequested_date());
			myStmt.setString(4, viewRequest.getDate_of_view());

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
	
	public List<GetRequest> getRequest(int ownerId) throws Exception {

		List<GetRequest> request = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn =  ds.getConnection();

			// create sql statement
			String sql = "SELECT tbl_view_request.request_id AS request_id, "
					+ "tbl_student.first_name AS requested_by, "
					+ "tbl_property_types.type_name As requested_property, "
					+ "tbl_view_request.requested_date AS requested_date, "
					+ "tbl_view_request.date_of_view As date_of_view, "
					+ "tbl_view_request.status As status "
					+ "FROM tbl_view_request " 
					+ "INNER JOIN tbl_student ON tbl_student.student_id = tbl_view_request.requested_by " 
					+ "INNER JOIN tbl_property ON tbl_property.property_id = tbl_view_request.requested_property " 
					+ "INNER JOIN tbl_property_types ON tbl_property.property_type = tbl_property_types.type_id " 
					+ "Where tbl_property.owner='"+ ownerId +"' AND tbl_view_request.isDeleted = 1";

			myStmt = myConn.prepareStatement(sql);
			
			//smyStmt.setInt(1, ownerId);

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				// retrieve data from result set row
				int request_id = myRs.getInt("request_id");
				String requested_by = myRs.getString("requested_by");
				String requested_property = myRs.getString("requested_property");
				String requested_date = myRs.getString("requested_date");
				String date_of_view = myRs.getString("date_of_view");
				boolean status = myRs.getBoolean("status");
				// create new property type object
				GetRequest getRequest = new GetRequest(request_id, requested_by, requested_property, requested_date, date_of_view, status);

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