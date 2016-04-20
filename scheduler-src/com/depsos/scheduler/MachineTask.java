package com.depsos.scheduler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.depsos.soap.SOAPService;
import com.depsos.soap.SingleConnection;
import com.mpe.common.util.CommonUtil;

public class MachineTask {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	String url;
	String username;
	String password;
	String machineID;
	String machineKey;
	String machineURL;
	
	
	/**
	 * @param url
	 * @param username
	 * @param password
	 */
	public MachineTask(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public Object[] getMachineIdKeyUrl() throws Exception {
		Object[] obj = null;
		PreparedStatement ps=null, ps2=null;
		ResultSet rs=null;
		try {
			
			SingleConnection singleConnection = new SingleConnection();
			Connection connection = singleConnection.getConnection(this.url, this.username, this.password);
			
			String sql1 = "select machine_id, machine_key, machine_url from attendance_machine where is_locked = 'F' order by last_access ASC limit 1 ";
			String sql2 = "update attendance_machine set is_locked = ?, last_access = ? where machine_id = ? ";
			
			ps = connection.prepareStatement(sql1);
			rs = ps.executeQuery();
			if (rs.next()) {
				
				obj = new Object[3];
				obj[0] = rs.getString(1);
				obj[1] = rs.getString(2);
				obj[2] = rs.getString(3);
				//
				ps2 = connection.prepareStatement(sql2);
				ps2.setString(1, "T");
				ps2.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
				ps2.setString(3, (String)obj[0]);
				ps2.executeUpdate();
				//
				ps2.close();ps2=null;
			}
			ps.close(); ps=null;
			rs.close(); rs=null;
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			
		}
		return obj;
	}
	
	public void finishFlagMachineIdKeyUrl() throws Exception {
		PreparedStatement ps=null, ps2=null;
		try {
			
			SingleConnection singleConnection = new SingleConnection();
			Connection connection = singleConnection.getConnection(this.url, this.username, this.password);
			
			String sql2 = "update attendance_machine set is_locked = ?, last_access = ? where machine_id = ? ";
			
			
			ps2 = connection.prepareStatement(sql2);
			ps2.setString(1, "F");
			ps2.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
			ps2.setString(3, this.machineID);
			ps2.executeUpdate();
			//
			ps2.close();ps2=null;
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			
		}
	}
	
	public void demo(String machineID, String machineKey, String machineURL) throws Exception {
		try {
			
			this.machineID = machineID;
			this.machineKey = machineKey;
			this.machineURL = machineURL;
			
			Thread.sleep(20 * 1000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void saveAllData(String machineID, String machineKey, String machineURL) {
		this.machineID = machineID;
		this.machineKey = machineKey;
		this.machineURL = machineURL;
		//
		SOAPConnection soapConnection = null;
		Connection connection=null;
		PreparedStatement ps=null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			
			List<Object[]> objects = new LinkedList<Object[]>();
			
			// get data
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	        soapConnection = soapConnectionFactory.createConnection();
	        SOAPMessage soapResponse = soapConnection.call(SOAPService.createSOAPGetAllLog(machineKey, "ALL"), machineURL);
			
	        SOAPEnvelope env = soapResponse.getSOAPPart().getEnvelope();
	        SOAPBody sb = env.getBody();
	        Name name = env.createName("GetAttLogResponse");
	        Name name2 = env.createName("Row");
	        Iterator it = sb.getChildElements(name);
        	SOAPElement sbe = (SOAPElement) it.next();
        	Iterator its = sbe.getChildElements(name2);
        	while (its.hasNext()) {
	        	SOAPElement element = (SOAPElement) its.next();
	        	Iterator iterator = element.getChildElements();
	        	//
	        	Object[] object = new Object[5];
	        	//
	        	while (iterator.hasNext()) {
	        		SOAPElement soapElement = (SOAPElement)iterator.next();
	        		// <Row><PIN>52</PIN><DateTime>2015-06-18 21:57:48</DateTime><Verified>0</Verified><Status>0</Status><WorkCode>0</WorkCode></Row>
	        		//System.out.println(">> "+soapElement.getNodeName()+" // "+soapElement.getTextContent());
	        		if (soapElement.getNodeName().equalsIgnoreCase("PIN")) object[0] = soapElement.getTextContent();
	        		if (soapElement.getNodeName().equalsIgnoreCase("DateTime")) object[1] = soapElement.getTextContent();
	        		if (soapElement.getNodeName().equalsIgnoreCase("Verified")) object[2] = soapElement.getTextContent();
	        		if (soapElement.getNodeName().equalsIgnoreCase("Status")) object[3] = soapElement.getTextContent();
	        		if (soapElement.getNodeName().equalsIgnoreCase("WorkCode")) object[4] = soapElement.getTextContent();
	        	}
	        	objects.add(object);
        	}
        	
        	// delete all-log
        	soapConnection.call(SOAPService.createSOAPDeleteAllLog(machineKey, "3"), machineURL);
        	
        	// save data into DB
        	if (objects!=null && objects.size()>0) {
        		
        		String sqlInsert = ""
    					+ "insert into attendance (machine_id, pin, attendance_date_time, verified, status, work_code, is_sent, sent_counter) "
    					+ "values (?,?,?,?,?,?,?,?) "
    					+ "";
        		
        		for (Object[] object2 : objects) {
        			
        			ps = connection.prepareStatement(sqlInsert);
        			ps.setString(1, machineID);
        			ps.setString(2, (String)object2[0]);
        			// 2015-06-18 21:57:48
        			ps.setTimestamp(3, CommonUtil.getTimestampFromString((String)object2[1], "yyyy-MM-dd HH:mm:ss"));
        			ps.setInt(4, Integer.parseInt((String)object2[2]));
        			ps.setInt(5, Integer.parseInt((String)object2[3]));
        			ps.setInt(6, Integer.parseInt((String)object2[4]));
        			ps.setString(7, "F");
        			ps.setInt(8, 0);
        			ps.executeUpdate();
        			//
        			ps.close();ps=null;
        			
        		}
        		
        	}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (soapConnection!=null)soapConnection.close();
			} catch (SOAPException e) {}
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
			/*try {
	            Driver driver = DriverManager.getDriver(url);
	            DriverManager.deregisterDriver(driver);
	        } catch (SQLException ex) {
	            logger.info("Could not deregister driver:".concat(ex.getMessage()));
	        } */
		}
		
	}
	
	public String checkMachineId(String machineKey, String machineURL) {
		this.machineKey = machineKey;
		this.machineURL = machineURL;
		//
		SOAPConnection soapConnection = null;
		try {
			
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	        soapConnection = soapConnectionFactory.createConnection();
	        SOAPMessage soapResponse = soapConnection.call(SOAPService.createSOAPMachineParameterRequest(machineKey, "DeviceID"), machineURL);
	        
	        SOAPEnvelope env = soapResponse.getSOAPPart().getEnvelope();
	        SOAPBody sb = env.getBody();
	        Name name = env.createName("GetOptionResponse");
	        Name name2 = env.createName("Row");
	        Iterator<SOAPElement> it = sb.getChildElements(name);
        	SOAPElement sbe = (SOAPElement) it.next();
        	Iterator<SOAPElement> its = sbe.getChildElements(name2);
        	SOAPElement element = (SOAPElement) its.next();
        	Iterator<SOAPElement> iterator = element.getChildElements();
        	SOAPElement element2 = (SOAPElement) iterator.next();
        	if (element2.getNodeName().equalsIgnoreCase("Value")) return element2.getTextContent();
	        
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (soapConnection!=null)soapConnection.close();
			} catch (SOAPException e) {}
		}
		return null;
	}

}
