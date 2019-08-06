package com.acms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.acms.jdbc.Student;

public class StudentDbUtil {

	// Define datasource/connection pool for Resource Injection
	@Resource(name = "jdbc/b0aG7Ixbhn")
	private DataSource dataSource;

	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public List<Student> getStudents() throws Exception {

		List<Student> students = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "select * from `tbl_student` where `isDeleted` = 1 order by `first_name` ";

			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				// retrieve data from result set row
				int id = myRs.getInt("student_id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String address = myRs.getString("address");
				String email = myRs.getString("email");
				String telephone = myRs.getString("telephone");
                boolean isDeleted = myRs.getBoolean("isDeleted");
				// create new student object
				Student tempStudent = new Student(id, firstName, lastName, address, email, telephone,isDeleted);

				// add it to the list of students
				students.add(tempStudent);
			}

			return students;
		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void addStudent(Student theStudent) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();

			// create sql for insert
			String sql = "insert into tbl_student " + "(first_name, last_name, address, email, telephone)"
					+ "values (?, ?, ?, ?,?)";

			myStmt = myConn.prepareStatement(sql);

			// set the param values for the student
			myStmt.setString(1, theStudent.getFirst_name());
			myStmt.setString(2, theStudent.getLast_name());
			myStmt.setString(3, theStudent.getAddress());
			myStmt.setString(4, theStudent.getEmail());
			myStmt.setString(5, theStudent.getTelephone());

			// execute sql insert
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void updateStudent(Student theStudent) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();

			// create SQL update statement
			String sql = "update tbl_student " + "set first_name=?, last_name=?, address=? ,email=? , telephone=? "
					+ "where student_id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theStudent.getFirst_name());
			myStmt.setString(2, theStudent.getLast_name());
			myStmt.setString(3, theStudent.getAddress());
			myStmt.setString(4, theStudent.getEmail());
			myStmt.setString(5, theStudent.getTelephone());
			myStmt.setInt(6, theStudent.getStudent_id());

			// execute SQL statement
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public Student getStudent(String studentId) throws Exception {

		Student theStudent = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int student_id;

		try {
			// convert student id to int
			student_id = Integer.parseInt(studentId);

			// get connection to database
			myConn = dataSource.getConnection();

			// create sql to get selected student
			String sql = "select * from tbl_student where student_id=?";

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, student_id);

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

				// use the studentId during construction
				theStudent = new Student(student_id, firstName, lastName, address, email, telephone, isDeleted);
			} else {
				throw new Exception("Could not find student id: " + student_id);
			}

			return theStudent;
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void deleteStudent(String theStudentId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// convert student id to int
			int studentId = Integer.parseInt(theStudentId);

			// get connection to database
			myConn = dataSource.getConnection();

			// create sql to delete student
			String sql = "update `tbl_student` set `isDeleted` = 0 where `student_id` = ?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, studentId);

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
				myConn.close(); // doesn't really close it ... just puts back in connection pool
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}