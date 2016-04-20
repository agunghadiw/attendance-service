package com.depsos.scheduler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import org.apache.log4j.Logger;

public class ArchiveTask {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	String url;
	String username;
	String password;
	int maxArchiveDay;
	
	public ArchiveTask(String url, String username, String password,
			int maxArchiveDay) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
		this.maxArchiveDay = maxArchiveDay;
	}

	public void remove() throws Exception {
		Connection connection=null;
		PreparedStatement ps=null, ps2=null;
		ResultSet rs = null;
		int i = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -maxArchiveDay);
			
			String sqlSelect = ""
					+ "select attendance_id, machine_id, pin, attendance_date_time, verified, status, work_code, is_sent, sent_counter "
					+ "from attendance where date(attendance_date_time) <= ? and is_sent='T' "
					+ ""
					+ "";
			
			String sqlInsert = ""
					+ "insert into attendance_history (attendance_id, machine_id, pin, attendance_date_time, verified, status, work_code, is_sent, sent_counter) "
					+ "values (?,?,?,?,?,?,?,?,?) "
					+ "";
			
			String sqlDelete = "delete from attendance where attendance_id = ? ";
			
			ps = connection.prepareStatement(sqlSelect);
			ps.setDate(1, new Date(calendar.getTime().getTime()));
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				// insert
				ps2 = connection.prepareStatement(sqlInsert);
				ps2.setLong(1, rs.getLong(1));
				ps2.setString(2, rs.getString(2));
				ps2.setString(3, rs.getString(3));
				ps2.setTimestamp(4, rs.getTimestamp(4));
				ps2.setInt(5, rs.getInt(5));
				ps2.setInt(6, rs.getInt(6));
				ps2.setInt(7, rs.getInt(7));
				ps2.setString(8, rs.getString(8));
				ps2.setInt(9, rs.getInt(9));
				ps2.executeUpdate();
				//
				ps2.close();
				ps2=null;
				
				// delete
				ps2 = connection.prepareStatement(sqlDelete);
				ps2.setLong(1, rs.getLong(1));
				ps2.executeUpdate();
				//
				ps2.close();
				ps2=null;
				
				i++;
			}
			
			
			logger.info(" <<<<  ARCHIVE "+i+" DATA  >>>>");
			
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
				if (connection!=null) {
					connection.close();
					connection=null;
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
	        }*/ 
		}
		
		
	}

}
