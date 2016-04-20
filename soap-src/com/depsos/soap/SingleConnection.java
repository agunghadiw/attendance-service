package com.depsos.soap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingleConnection {
	
	static {
	    try {
	    	Class.forName("com.mysql.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	    	throw new IllegalArgumentException("MySQL db driver isnot on classpath");
	    }
	}
	
	public static Connection getConnection(String url, String user, String password) throws SQLException {
	    return DriverManager.getConnection(url, user, password);
	}

}
