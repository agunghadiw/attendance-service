/**
 * 
 */
package com.depsos.scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.depsos.soap.JSONService;
import com.mpe.common.util.CommonUtil;

/**
 * @author Mas AH
 * @create on Jun 23, 2015 10:29:49 AM
 */
public class SendingTask {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	String url;
	String username;
	String password;
	String pathJson;
	String msAccessUrl;
	
	
	
	/**
	 * @param url
	 * @param username
	 * @param password
	 * @param pathJson
	 */
	public SendingTask(String url, String username, String password, String pathJson, String msAccessUrl) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.pathJson = pathJson;
		this.msAccessUrl = msAccessUrl;
	}

	public void sending() throws Exception {
		
		Connection mySqlConnection=null, msAccessConnection=null;
		PreparedStatement ps=null, ps2=null, msAccessPs=null;
		ResultSet rs = null;
		Class.forName("com.mysql.jdbc.Driver");
		Class.forName("org.objectweb.rmijdbc.Driver").newInstance();
		
		try {
			mySqlConnection = DriverManager.getConnection(url, username, password);
			if (msAccessUrl!=null && msAccessUrl.length()>0) msAccessConnection = DriverManager.getConnection(msAccessUrl, "", "");
			
			String sqlInsertAccess = "insert into CHECKINOUT(USERID,CHECKTIME,CHECKTYPE,VERIFYCODE,SENSORID,Memoinfo,WorkCode,sn,UserExtFmt) "
					+ "values(?,?,?,?,?,?,?,?,?) ";
			
			String sqlSelect = ""
					+ "select machine_id, pin, attendance_date_time, verified, status, work_code, attendance_id "
					+ "from attendance where is_sent='F' and sent_counter <= 20 order by attendance_date_time ASC, sent_counter ASC limit 5 "
					+ ""
					+ "";
			
			String sqlUpdate = "update attendance set is_sent = ?, sent_counter=(sent_counter+1) where attendance_id = ? ";
			
			ps = mySqlConnection.prepareStatement(sqlSelect);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				if (msAccessConnection!=null) {
					msAccessPs = msAccessConnection.prepareStatement(sqlInsertAccess);
					msAccessPs.setInt(1, Integer.parseInt(rs.getString(2)));
					msAccessPs.setTimestamp(2, rs.getTimestamp(3));
					msAccessPs.setString(3, "I");
					msAccessPs.setInt(4, 0);
					msAccessPs.setString(5, rs.getString(1));
					msAccessPs.setString(6, "");
					msAccessPs.setInt(7, 0);
					msAccessPs.setString(8, "");
					msAccessPs.setInt(9, 0);
					int i = msAccessPs.executeUpdate();
					//logger.info("i >> "+i);
				}
				
				String s = JSONService.saveAttendance(pathJson, 120000, rs.getString(1), rs.getString(2), CommonUtil.getStringFromTimestamp(rs.getTimestamp(3), "yyyyMMddHHmmss"), rs.getInt(4), rs.getInt(5), rs.getInt(6));
				//logger.info("s >> "+rs.getString(2)+ " // " +s);
				if (s.equalsIgnoreCase("00")) {
					// update
					ps2 = mySqlConnection.prepareStatement(sqlUpdate);
					ps2.setString(1, "T");
					ps2.setLong(2, rs.getLong(7));
					ps2.executeUpdate();
					//
					ps2.close();
					ps2=null;
				} else {
					// update
					ps2 = mySqlConnection.prepareStatement(sqlUpdate);
					ps2.setString(1, "F");
					ps2.setLong(2, rs.getLong(7));
					ps2.executeUpdate();
					//
					ps2.close();
					ps2=null;
				}
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/*try {
				Enumeration<Driver> drivers = DriverManager.getDrivers();
		        while (drivers.hasMoreElements()) {
		            Driver driver = drivers.nextElement();
		            try {
		                DriverManager.deregisterDriver(driver);
		                logger.info(String.format("deregistering jdbc driver: %s", driver));
		            } catch (SQLException e) {
		            	logger.info(String.format("Error deregistering driver %s", driver));
		            }
		        }
			} catch (Exception e2) {}*/
			try {
				if (mySqlConnection!=null) {
					mySqlConnection.close();
					mySqlConnection=null;
				}
			} catch (Exception e2) {}
			try {
				if (ps!=null) {
					ps.close();
					ps=null;
				}
			} catch (Exception e2) {}
			try {
				if (ps2!=null) {
					ps2.close();
					ps2=null;
				}
			} catch (Exception e2) {}
			try {
				if (rs!=null) {
					rs.close();
					rs=null;
				}
			} catch (Exception e2) {}
			/*try {
	            Driver driver = DriverManager.getDriver(url);
	            DriverManager.deregisterDriver(driver);
	        } catch (SQLException ex) {
	            logger.info("Could not deregister driver:".concat(ex.getMessage()));
	        } */
		}
	}

}
