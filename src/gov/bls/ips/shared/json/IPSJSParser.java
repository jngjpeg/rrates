/*******************************************************************************
 *
 * FILE : IPSJSParser.java
 *
 * DESCRIPTION :
 *   This class encapsulates logging facilities using Apache Jackson.
 *   
 * REVISION HISTORY :
 *
 * Date         Name            Release             Description
 * -------  -----------------   ----------     ---------------------------------
 * 2Feb2015    Arun Jayavel                            original version
 * 07/07/2015  Arun Jayavel		Sprint 15       Changed the IPSValue from 
 *                                                   admin to shared
 * 10/01/2015  Selva Sathiah	Sprint 18       Added method readSSOValue.
 * 
 *  Copyright 2015 by All IPS-BLS .
 * All rights reserved.
******************************************************************************/
// Package assignment

package gov.bls.ips.shared.json;
import java.io.Reader;
import java.net.URL;

import javax.servlet.ServletOutputStream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.framework.api.util.IPSValue;
import gov.bls.ips.shared.logger.components.IPSLogger;

public class IPSJSParser{
	
  public static String _CLASS_NAME=ClassFromStatic.getCurrentClass().getName();

  public static IPSValue readValue(Reader reader, IPSValue valueType)  {
	 ObjectMapper mapper;	
	 IPSValue ipsValeuObj=null;
	  try{
		  logJacksonLibversion();
		  mapper = new ObjectMapper();
		  mapper.configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		  ipsValeuObj= (IPSValue)mapper.readValue(reader,valueType.getClass());  
	  }
	  catch(Exception e){
		  IPSLogger.logError(_CLASS_NAME+".readValue() Exception  ",e);
	  }
	  finally{
		mapper=null;  
	  }
	  return ipsValeuObj;
  }
  public static void writeValue(ServletOutputStream outputStream, Object valueObj)  {
	 ObjectMapper mapper;	
	 try{
	   logJacksonLibversion();
	   mapper = new ObjectMapper();
	   mapper.writeValue(outputStream,valueObj);  
	 }
	 catch(Exception e){
		 IPSLogger.logError(_CLASS_NAME+".writeValue() Exception  ",e);
	 }
	 finally{
       mapper=null;  
	 }

  }
  
  public static String writeValueAsString(Object valueObj)  {
	 ObjectMapper mapper;	
	 String objVal=null;
	 try{
	   logJacksonLibversion();
	   mapper = new ObjectMapper();
	   objVal= mapper.writeValueAsString(valueObj); 
	 }
	 catch(Exception e){
	 }
	 finally{
       mapper=null;  
	 }

	 return objVal;
  }
  
  public static IPSValue readValue(String jsonInput, IPSValue valueType,boolean ignoreCamelCase)  {
	 ObjectMapper mapper;	
	 IPSValue ipsValeuObj=null;
	  try{
		  logJacksonLibversion();
		  mapper = new ObjectMapper();
		  mapper.configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		  if(ignoreCamelCase==true){
			  mapper.setPropertyNamingStrategy(
					    PropertyNamingStrategies.UPPER_CAMEL_CASE);
		  }			
		  ipsValeuObj= (IPSValue)mapper.readValue(jsonInput,valueType.getClass());  
	  }
	  catch(Exception e){
		  e.printStackTrace();
	  }
	  finally{
		mapper=null;  
	  }
	  return ipsValeuObj;
  }


    private static void logJacksonLibversion() {
	  try {
		  Class<?> jacksonClass = ObjectMapper.class;
		  URL location = jacksonClass.getProtectionDomain().getCodeSource().getLocation();
		  Package jacksonPackage = jacksonClass.getPackage();
		  System.err.println("Jackson version    : " + jacksonPackage.getImplementationVersion());
		  System.err.println("Jackson JAR location : " + location);
		  IPSLogger.logDebug(_CLASS_NAME, "Jackson version: " +
				   com.fasterxml.jackson.databind.ObjectMapper.class.getPackage().getImplementationVersion());
		  IPSLogger.logDebug(_CLASS_NAME, "Jackson location: " +
				   com.fasterxml.jackson.databind.ObjectMapper.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	  }catch(Exception e) {
		  e.printStackTrace();
	  }	  
  }
}
