package com.acms.model;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConUtil {

	   public String getDirectory() throws IOException {
		   File currentDirFile = new File(".");
		   String helper = currentDirFile.getAbsolutePath();
		   String currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());		   
			String filePath = System.getProperty("user.dir");
			System.out.println("Current relative path is: " + filePath);
			return helper;
	   }
	
		public Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		String url = "jdbc:sqlite:" + "C:/Users/Nirzaf/git/amts.db";
		// String url = "jdbc:sqlite:C:/sqlite/amts.db";		
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection(url);
		return conn;
	}
}