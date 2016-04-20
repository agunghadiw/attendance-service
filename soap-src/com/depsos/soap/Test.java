/**
 * 
 */
package com.depsos.soap;

import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;

/**
 * @author Mas AH
 * @create on Jun 17, 2015 4:31:41 PM
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			//System.setProperty("javax.xml.soap.MessageFactory", "com.sun.xml.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl"); 
			
			//SOAPService.createSOAPRestartRequest(0);
			
			// Create SOAP Connection
	        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

	        // Send SOAP Message to SOAP Server
	        //String url = "http://localhost:88/iWsService";
	        String url = "http://172.16.2.201:80/iWsService";
	        //SOAPMessage soapResponse = soapConnection.call(SOAPService.createSOAPRestartRequest(282828), url);
	        SOAPMessage soapResponse = soapConnection.call(SOAPService.createSOAPGetAllLog("282828", "ALL"), url);
	        //SOAPMessage soapResponse = soapConnection.call(SOAPService.createSOAPMachineParameterRequest(282828, "DeviceID"), url);

	        // print SOAP Response
	        System.out.print("Response SOAP Message:");
	        soapResponse.writeTo(System.out);
	        
	        
	        /*SOAPEnvelope env = soapResponse.getSOAPPart().getEnvelope();
	        SOAPBody sb = env.getBody();
	        Name name = env.createName("GetOptionResponse");
	        Name name2 = env.createName("Row");
	        Iterator it = sb.getChildElements(name);
        	SOAPElement sbe = (SOAPElement) it.next();
        	Iterator its = sbe.getChildElements(name2);
        	SOAPElement element = (SOAPElement) its.next();
        	Iterator iterator = element.getChildElements();
        	SOAPElement element2 = (SOAPElement) iterator.next();
        	System.out.println("s : "+element2.getNodeName()+"//"+element2.getTextContent());*/
	        
	       
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
	        	while (iterator.hasNext()) {
	        		SOAPElement soapElement = (SOAPElement)iterator.next();
	        		System.out.println(">> "+soapElement.getNodeName()+" // "+soapElement.getTextContent());
	        	}
        	}
        	
            

	        soapConnection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
