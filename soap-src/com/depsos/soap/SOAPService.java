/**
 * 
 */
package com.depsos.soap;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

/**
 * @author Mas AH
 * @create on Jun 17, 2015 3:56:57 PM
 */
public class SOAPService {
	
	public static SOAPMessage createSOAPDeleteAllLog(String comKey, String jobNumber) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        /*
        	<ClearData>
        		<ArgComKey xsi:type="xsd:integer”>ComKey</ArgComKey>
        		<Arg>
        			<Value xsi:type="xsd:integer”>1</Value>
        		</Arg>
        	</ClearData>
         */
        
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("xsi","http://www.w3.org/2001/XMLSchema-instance");
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("ClearData");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("ArgComKey");
        soapBodyElem1.addTextNode(comKey).setAttribute("xsi:type","xsd:integer");
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("Arg");
        SOAPElement soapBodyElem21 = soapBodyElem2.addChildElement("Value");
        soapBodyElem21.addTextNode(String.valueOf(jobNumber)).setAttribute("xsi:type","xsd:integer");

        //MimeHeaders headers = soapMessage.getMimeHeaders();
        //headers.addHeader("SOAPAction", serverURI  + "VerifyEmail");

        soapMessage.saveChanges();

        /* Print the request message */
        //System.out.print("Request SOAP Message:");
        //soapMessage.writeTo(System.out);
        //System.out.println();

        return soapMessage;
	}
	
	public static SOAPMessage createSOAPGetAllLog(String comKey, String jobNumber) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        /*
        <GetAttLog>
        	<ArgComKey xsi:type="xsd:integer”>ComKey</ArgComKey>
        	<Arg>
        		<PIN xsi:type="xsd:integer”>Job Number</PIN>
        	</Arg>
        </GetAttLog>
         */
        
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("xsi","http://www.w3.org/2001/XMLSchema-instance");
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("GetAttLog");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("ArgComKey");
        soapBodyElem1.addTextNode(comKey).setAttribute("xsi:type","xsd:integer");
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("Arg");
        SOAPElement soapBodyElem21 = soapBodyElem2.addChildElement("PIN");
        soapBodyElem21.addTextNode(String.valueOf(jobNumber)).setAttribute("xsi:type","xsd:integer");

        //MimeHeaders headers = soapMessage.getMimeHeaders();
        //headers.addHeader("SOAPAction", serverURI  + "VerifyEmail");

        soapMessage.saveChanges();

        /* Print the request message */
        //System.out.print("Request SOAP Message:");
        //soapMessage.writeTo(System.out);
        //System.out.println();

        return soapMessage;
	}
	
	public static SOAPMessage createSOAPRestartRequest(String comKey) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
		//MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        /*
        <Restart> 
        	<ArgComKey Xsi:type="xsd:integer"> ComKey </ ArgComKey> 
        </ Restart>
         */

        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("xsi","http://www.w3.org/2001/XMLSchema-instance");
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("Restart");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("ArgComKey");
        soapBodyElem1.addTextNode(comKey).setAttribute("xsi:type","xsd:integer");

        //MimeHeaders headers = soapMessage.getMimeHeaders();
        //headers.addHeader("SOAPAction", url);

        soapMessage.saveChanges();

        /* Print the request message */
        //System.out.print("Request SOAP Message:");
        //soapMessage.writeTo(System.out);
        //System.out.println();

        return soapMessage;
    }
	
	
	public static SOAPMessage createSOAPUserInfoRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        /*
        <GetUserInfo> 
        	<ArgComKey Xsi:type="xsd:integer"> ComKey </ ArgComKey>
        	<Arg> 
        		<PIN Xsi:type="xsd:integer"> Job Number </ PIN> 
        	</ Arg> 
        </ GetUserInfo>
         */
        
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("xsi","http://www.w3.org/2001/XMLSchema-instance");
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("GetUserInfo");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("ArgComKey");
        soapBodyElem1.addTextNode("ComKey").setAttribute("xsi:type","xsd:integer");
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("Arg");
        SOAPElement soapBodyElem21 = soapBodyElem2.addChildElement("PIN");
        soapBodyElem21.addTextNode("Job Number").setAttribute("xsi:type","xsd:integer");

        //MimeHeaders headers = soapMessage.getMimeHeaders();
        //headers.addHeader("SOAPAction", serverURI  + "VerifyEmail");

        soapMessage.saveChanges();

        /* Print the request message */
        //System.out.print("Request SOAP Message:");
        //soapMessage.writeTo(System.out);
        //System.out.println();

        return soapMessage;
    }
	
	public static SOAPMessage createSOAPMachineParameterRequest(String comKey, String optionItemName) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        /*
        <GetOption> 
        	<ArgComKey xsi:type="xsd:integer”>ComKey</ArgComKey>
        	<Arg> 
        		<Name xsi:type="xsd:string”>Option Item Name</Name>
        	</Arg> 
        </GetOption>
         */
        
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("xsi","http://www.w3.org/2001/XMLSchema-instance");
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("GetOption");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("ArgComKey");
        soapBodyElem1.addTextNode(comKey).setAttribute("xsi:type","xsd:integer");
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("Arg");
        SOAPElement soapBodyElem21 = soapBodyElem2.addChildElement("Name");
        soapBodyElem21.addTextNode(optionItemName).setAttribute("xsi:type","xsd:string");

        //MimeHeaders headers = soapMessage.getMimeHeaders();
        //headers.addHeader("SOAPAction", serverURI  + "VerifyEmail");

        soapMessage.saveChanges();

        /* Print the request message */
        //System.out.print("Request SOAP Message:");
        //soapMessage.writeTo(System.out);
        //System.out.println();

        return soapMessage;
    }
	
	

}
