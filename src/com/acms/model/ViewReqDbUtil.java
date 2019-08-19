package com.acms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import com.acms.jdbc.GetRequest;
import com.acms.jdbc.ViewRequest;

public class ViewReqDbUtil {

	@Resource(name = "jdbc/ams")
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
					+ "(`requested_by`, `requested_property`, `requested_date`, `date_of_view`) " + "VALUES (?,?,?,?)";

			myStmt = myConn.prepareStatement(query);
			try {
				// set the param values for the owner
				myStmt.setInt(1, viewRequest.getRequested_by());
				myStmt.setInt(2, viewRequest.getRequested_property());
				myStmt.setString(3, viewRequest.getRequested_date());
				myStmt.setString(4, viewRequest.getDate_of_view());

				// execute sql insert
				myStmt.execute();

			} catch (Exception ex) {
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
			myConn = ds.getConnection();

			// create sql statement
			String sql = "SELECT tbl_view_request.request_id AS request_id, "
					+ "tbl_student.first_name AS requested_by, "
					+ "tbl_property_types.type_name As requested_property, "
					+ "tbl_view_request.requested_date AS requested_date, "
					+ "tbl_view_request.date_of_view As date_of_view, " + "tbl_property.owner AS owner_id, "
					+ "tbl_view_request.status As status, " + "tbl_view_request.isDeleted As isDeleted "
					+ "FROM tbl_view_request "
					+ "INNER JOIN tbl_student ON tbl_student.student_id = tbl_view_request.requested_by "
					+ "INNER JOIN tbl_property ON tbl_property.property_id = tbl_view_request.requested_property "
					+ "INNER JOIN tbl_property_types ON tbl_property.property_type = tbl_property_types.type_id "
					+ "Where tbl_property.owner='" + ownerId + "' AND tbl_view_request.isDeleted = 1";

			myStmt = myConn.prepareStatement(sql);

			// smyStmt.setInt(1, ownerId);

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
				int owner_id = myRs.getInt("owner_id");
				boolean status = myRs.getBoolean("status");
				boolean isDeleted = myRs.getBoolean("isDeleted");

				// create new request type object
				GetRequest getRequest = new GetRequest(request_id, requested_by, requested_property, requested_date,
						date_of_view, owner_id, status, isDeleted);

				// add it to the list of requests
				request.add(getRequest);
			}
			return request;

		} finally {
			// close JDBC objects
			c.close(myConn, myStmt, myRs);
		}
	}

	public List<GetRequest> getMyRequest(int studentId) throws Exception {

		List<GetRequest> request = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = ds.getConnection();

			// create sql statement
			String sql = "SELECT tbl_view_request.request_id AS request_id, "
					+ "tbl_student.first_name AS requested_by, "
					+ "tbl_property_types.type_name As requested_property, "
					+ "tbl_view_request.requested_date AS requested_date, "
					+ "tbl_view_request.date_of_view As date_of_view, " + "tbl_property.owner AS owner_id, "
					+ "tbl_view_request.status As status, " + "tbl_view_request.isDeleted AS isDeleted "
					+ "FROM tbl_view_request "
					+ "INNER JOIN tbl_student ON tbl_student.student_id = tbl_view_request.requested_by "
					+ "INNER JOIN tbl_property ON tbl_property.property_id = tbl_view_request.requested_property "
					+ "INNER JOIN tbl_property_types ON tbl_property.property_type = tbl_property_types.type_id "
					+ "Where tbl_view_request.requested_by='" + studentId + "' AND tbl_view_request.isDeleted = 1";

			myStmt = myConn.prepareStatement(sql);

			// smyStmt.setInt(1, ownerId);

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
				int owner_id = myRs.getInt("owner_id");
				boolean status = myRs.getBoolean("status");
				boolean isDeleted = myRs.getBoolean("isDeleted");

				// create new request type object
				GetRequest getRequest = new GetRequest(request_id, requested_by, requested_property, requested_date,
						date_of_view, owner_id, status, isDeleted);

				// add it to the list of requests
				request.add(getRequest);
			}
			return request;

		} finally {
			// close JDBC objects
			c.close(myConn, myStmt, myRs);
		}
	}

	// Accept or Reject
	public void viewRequestStatus(int request_id) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		String sql = null;

		try {
			// get connection to database
			myConn = ds.getConnection();

			// create sql to delete/disable user
			sql = "update tbl_view_request SET status = CASE WHEN status = 0 THEN 1 WHEN status = 1 THEN 0 END Where request_id =?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, request_id);

			// execute sql statement
			myStmt.execute();
		} finally {
			// clean up JDBC code
			c.close(myConn, myStmt, null);
		}
	}

	// Accept or Reject
	public void deleteRequest(int request_id) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		String sql = null;

		try {
			// get connection to database
			myConn = ds.getConnection();

			// create sql to delete/disable user
			sql = "update tbl_view_request SET isDeleted = 0 Where request_id =?";

			System.out.println(" You executed the delete function on " + CurrentTime());

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, request_id);

			// execute sql statement
			myStmt.execute();
		} finally {
			// clean up JDBC code
			c.close(myConn, myStmt, null);
		}
	}

	public String CurrentTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

}