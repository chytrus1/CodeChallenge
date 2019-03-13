package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperator {
	public static void getSmth() {
		Connection conn = null;
		String db = "jdbc:hsqldb:file:sampledb/sampledb;ifexists=true";
		String user = "SA";
		String password = "";

		try {
			conn = DriverManager.getConnection(db, user, password);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select FIRSTNAME, LASTNAME from Customer");

			while (rs.next()) {
				System.out.println("Customer Name: " + rs.getString("FIRSTNAME") + " " + rs.getString("LASTNAME"));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public static void insertLogRow(String id, String eventState, String type, String host, long duration, boolean alertFlag) {
		Connection conn = null;

		String db = "jdbc:hsqldb:file:D:/hsqldb/sampledb/sampledb;ifexists=true";
		String user = "SA";
		String password = "";
		
		String insertLogRowQuery = "INSERT INTO ALERT"
				+ "(ID, EVENT_STATE, TYPE, HOST, DURATION, ALERT_FLAG) VALUES"
				+ "(?, ?, ?, ?, ?, ?)";

		System.out.println(id);
		System.out.println(eventState);
		System.out.println(type);
		System.out.println(host);
		System.out.println(duration + "");
		System.out.println(alertFlag + "");
		
		try {
			conn = DriverManager.getConnection(db, user, password);

			PreparedStatement stmt = conn.prepareStatement(insertLogRowQuery);
			stmt.setString(1, id);
			stmt.setString(2, eventState);
			stmt.setString(3, type);
			stmt.setString(4, host);
			stmt.setLong(5, duration);
			stmt.setBoolean(6, alertFlag);
			stmt.executeUpdate(insertLogRowQuery);
			stmt.close();
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
	}
}