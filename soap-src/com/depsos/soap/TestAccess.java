package com.depsos.soap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class TestAccess {

	public static void main(String[] args) {

		Connection msAccessConnection = null;
		PreparedStatement msAccessPs=null;
		try {
			Class.forName("org.objectweb.rmijdbc.Driver").newInstance();
			
			msAccessConnection = DriverManager.getConnection("jdbc:rmi://localhost:1234/jdbc:odbc:TUKIN", "", "");
			
			Date d = new Date();
			
			String sqlInsertAccess = "insert into CHECKINOUT(USERID,CHECKTIME,CHECKTYPE,VERIFYCODE,SENSORID,Memoinfo,WorkCode,sn,UserExtFmt) "
					+ "values(?,?,?,?,?,?,?,?,?) ";
			
			if (msAccessConnection!=null) {
				msAccessPs = msAccessConnection.prepareStatement(sqlInsertAccess);
				msAccessPs.setInt(1, Integer.parseInt("68"));
				msAccessPs.setTimestamp(2, new Timestamp(d.getTime()));
				msAccessPs.setString(3, "I");
				msAccessPs.setInt(4, 0);
				msAccessPs.setString(5, "1");
				msAccessPs.setString(6, "");
				msAccessPs.setInt(7, 0);
				msAccessPs.setString(8, "");
				msAccessPs.setInt(9, 0);
				int i = msAccessPs.executeUpdate();
				System.out.println("i >> "+i);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (msAccessConnection!=null)
				try {
					msAccessConnection.close();
				} catch (SQLException e) {}
		}

	}

}
