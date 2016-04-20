package com.depsos.soap;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JSONService {
	
	static Log log = LogFactory.getLog("JSONService");
	
	public static String saveAttendance(String path, int timeout, 
			String machineId, String pin, String attendanceDateTime, int verified,
			int status, int workCode) throws Exception {
		InputStream is = null;
		JsonReader rdr = null;
		OutputStreamWriter out = null;
		URLConnection connection = null;
		try {
			URL url = new URL(path+"/soap/AttendanceMachineJsonAction_save.action");
	        connection = url.openConnection();
	        connection.setDoOutput(true); // Triggers POST
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        connection.setConnectTimeout(timeout);
	        connection.setReadTimeout(timeout);
	        
	        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
	        objectBuilder
	        	.add("machineId", machineId)
	        	.add("pin", pin)
	        	.add("attendanceDateTime", attendanceDateTime)
	        	.add("verified", verified)
	        	.add("status", status)
	        	.add("workCode", workCode)
	        	;
	        JsonObject attendanceObj = objectBuilder.build();
	        
	        out = new OutputStreamWriter(connection.getOutputStream());
	        out.write(attendanceObj.toString());
			out.close();
	        
	        is = connection.getInputStream();
			rdr = Json.createReader(is);
			
			JsonObject obj = rdr.readObject();
			JsonObject obj2 = obj.getJsonObject("responseResult");
			JsonObject obj3 = obj2.getJsonObject("responseCode");
			
			return obj3.getString("code");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (is!=null) {
				is.close(); is=null;
			}
			if (rdr!=null) {
				rdr.close(); rdr=null;
			}
		}
	}
	
	public static String restartMachine(String path, int timeout, String machineId) throws Exception {
		InputStream is = null;
		JsonReader rdr = null;
		OutputStreamWriter out = null;
		URLConnection connection = null;
		try {
			URL url = new URL(path+"/soap/AttendanceMachineJsonAction_restart.action");
	        connection = url.openConnection();
	        connection.setDoOutput(true); // Triggers POST
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        connection.setConnectTimeout(timeout);
	        connection.setReadTimeout(timeout);
	        
	        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
	        objectBuilder
	        	.add("machineId", machineId)
	        	;
	        JsonObject attendanceObj = objectBuilder.build();
	        
	        out = new OutputStreamWriter(connection.getOutputStream());
	        out.write(attendanceObj.toString());
			out.close();
	        
	        is = connection.getInputStream();
			rdr = Json.createReader(is);
			
			JsonObject obj = rdr.readObject();
			JsonObject obj2 = obj.getJsonObject("responseResult");
			JsonObject obj3 = obj2.getJsonObject("responseCode");
			
			return obj3.getString("code");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (is!=null) {
				is.close(); is=null;
			}
			if (rdr!=null) {
				rdr.close(); rdr=null;
			}
		}
	}
	
	public static String ping(String path, int timeout, String machineId) throws Exception {
		InputStream is = null;
		JsonReader rdr = null;
		OutputStreamWriter out = null;
		URLConnection connection = null;
		try {
			URL url = new URL(path+"/soap/AttendanceMachineJsonAction_ping.action");
	        connection = url.openConnection();
	        connection.setDoOutput(true); // Triggers POST
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        connection.setConnectTimeout(timeout);
	        connection.setReadTimeout(timeout);
	        
	        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
	        objectBuilder
	        	.add("machineId", machineId)
	        	;
	        JsonObject attendanceObj = objectBuilder.build();
	        
	        out = new OutputStreamWriter(connection.getOutputStream());
	        out.write(attendanceObj.toString());
			out.close();
	        
	        is = connection.getInputStream();
			rdr = Json.createReader(is);
			
			JsonObject obj = rdr.readObject();
			JsonObject obj2 = obj.getJsonObject("responseResult");
			JsonObject obj3 = obj2.getJsonObject("responseCode");
			
			return obj3.getString("code");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (is!=null) {
				is.close(); is=null;
			}
			if (rdr!=null) {
				rdr.close(); rdr=null;
			}
		}
	}

}
