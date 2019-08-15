package com.acms.model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConn {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			String url = "jdbc:sqlite:C:/Users/Nirzaf/git/acmd/amts.db";
			// String url = "jdbc:sqlite:C:/sqlite/amts.db";
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					/* ignore close errors */
				}
			}
		}
	}
}