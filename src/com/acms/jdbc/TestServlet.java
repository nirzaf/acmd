package com.acms.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//Define datasource/connection pool for Resource Injection
	
	@Resource(name="jdbc/amr")
	private DataSource dataSource;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Step 1: setting up the print writer 
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		//Step 2: Get a connection to a database
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
		//Step 3: Create SQL Statements 
			
			String sql="select * from tbl_users";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
		//Step 4: Execute SQL Statements 
		
		//Step 5: Process the result set
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
