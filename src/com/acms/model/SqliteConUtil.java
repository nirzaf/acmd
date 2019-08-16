package com.acms.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConUtil {

	public Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		String url = "jdbc:sqlite:" + "C:/Users/mfazrin/eclipse-workspace/acmd/" + "ams.db";
		// String url = "jdbc:sqlite:C:/sqlite/amts.db";
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection(url);
		return conn;
	}
}