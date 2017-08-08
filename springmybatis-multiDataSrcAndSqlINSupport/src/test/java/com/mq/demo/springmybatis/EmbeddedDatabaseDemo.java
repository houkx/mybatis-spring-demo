package com.mq.demo.springmybatis;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.derby.jdbc.EmbeddedDriver;

public class EmbeddedDatabaseDemo {

	public static void main(String[] args) {
		EmbeddedDatabaseDemo e = new EmbeddedDatabaseDemo();
		e.testDerby();
	}

	public void testDerby() {
		Connection conn = null;
		PreparedStatement pstmt;
		Statement stmt;
		ResultSet rs = null;
		String createSQL = "create table users (" + "id int not null generated always as"
				+ " identity (start with 1, increment by 1),   "
				+ "name varchar(30) not null,"
				+ "role int,"
				+ "constraint primary_key primary key (id))";

		try {
			Driver derbyEmbeddedDriver = new EmbeddedDriver();
			DriverManager.registerDriver(derbyEmbeddedDriver);
			conn = DriverManager.getConnection("jdbc:derby:testdb1;create=true", new Properties());
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.execute(createSQL);

			pstmt = conn.prepareStatement("insert into users (name,role) values(?,?)");
			pstmt.setString(1, "Hagar the Horrible");
			pstmt.setInt(2, 1);
			pstmt.executeUpdate();

			rs = stmt.executeQuery("select name,role from users");
			while (rs.next()) {
				System.out.printf("name=%s,role=%d\n", rs.getString(1), rs.getInt(2));
			}

//			stmt.execute("drop table person");

			conn.commit();

		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}

		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException ex) {
			if (((ex.getErrorCode() == 50000) && ("XJ015".equals(ex.getSQLState())))) {
				System.out.println("Derby shut down normally");
			} else {
				System.err.println("Derby did not shut down normally");
				System.err.println(ex.getMessage());
			}
		}
	}
}