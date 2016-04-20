/**
 * 
 */
package com.depsos.scheduler;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

import com.depsos.soap.JSONService;
import com.depsos.soap.SOAPService;

/**
 * @author Mas AH
 * @create on Jun 23, 2015 10:30:01 AM
 */
public class OtherInfoTask {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	String url;
	String username;
	String password;
	String pathJson;
	//String machineID;
	String machineKey;
	String machineURL;
	
	/**
	 * @param url
	 * @param username
	 * @param password
	 * @param pathJson
	 * @param machineKey
	 * @param machineURL
	 */
	public OtherInfoTask(String url, String username, String password,
			String pathJson, String machineKey, String machineURL) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
		this.pathJson = pathJson;
		this.machineKey = machineKey;
		this.machineURL = machineURL;
	}
	
	public void ping(String machineId) {
		SOAPConnection soapConnection = null;
		try {
			// check machineID
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	        soapConnection = soapConnectionFactory.createConnection();
	        soapConnection.call(SOAPService.createSOAPMachineParameterRequest(machineKey, "DeviceID"), machineURL);
	        
			// send message
			String s = JSONService.ping(pathJson, 120000, machineId);
			if (s!=null && s.equalsIgnoreCase("00")) {
				logger.info(" <<< PING SUCCESS >>> ");
			} else {
				logger.info(" <<< PING PROBLEM >>> ");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (soapConnection!=null)soapConnection.close();
			} catch (SOAPException e) {}
		}
	}
	
	public void restart(String machineId) {
		SOAPConnection soapConnection = null;
		try {
			
			// check need restart?
			String s = JSONService.restartMachine(pathJson, 120000, machineId);
			if (s!=null && s.equalsIgnoreCase("00")) {
				// restart machine
				SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		        soapConnection = soapConnectionFactory.createConnection();
		        soapConnection.call(SOAPService.createSOAPRestartRequest(machineKey), machineURL);
		        
		        logger.info(" <<< RESTART MACHINE ID "+machineId+" >>> ");
			}
	        
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (soapConnection!=null)soapConnection.close();
			} catch (SOAPException e) {}
		}
	}

}
