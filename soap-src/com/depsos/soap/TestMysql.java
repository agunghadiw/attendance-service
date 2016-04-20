package com.depsos.soap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestMysql {

	public static void main(String[] args) {
		
		Connection connection=null;
		PreparedStatement ps=null, ps2=null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/dbattendance_client_1_0", "root", "root");
			
			System.out.println(connection.getNetworkTimeout());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection!=null) {
					connection.close();
					connection=null;
				}
			} catch (Exception e2) {}
		}

	}

}
