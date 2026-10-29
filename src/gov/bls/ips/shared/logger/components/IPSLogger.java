/*******************************************************************************
 *
 * FILE : Logger.java
 *
 * DESCRIPTION :
 *   This class encapsulates logging facilities using Apache Log4J2.Several 
 *   parameters from the IPSLoggerConfig.properties file are used to 
 *   properly use this class. Following are the properties and 
 *   high level description
 *   gov.bls.ips.shared.logger.components.config.IPSLoggerConfig._LOGGER_CONFIG  
 *   Constant for <<ApplicationName>>_LOGGER_CONFIG	--The XML config file for Log4J2
 *   
 *
 * REVISION HISTORY :
 *
 * Date         Name            Release             Description
 * -------  -----------------   ----------     ---------------------------------
 * 19Apr2011  Arun Jayavel                            original version
 * 10Nov2011  Arun Jayavel                    Enhanced for dynamic change of logging level
 * 16Apr2020  Vijay Sathiya                   Log4j2 implementation   
 * 08Apr2025  Nonita Mascarenhas                Updated to log appropriate line numbers  
 *
 *  Copyright 2011 by All IPS-BLS .
 * All rights reserved.
******************************************************************************/
// Package assignment
package gov.bls.ips.shared.logger.components;

//Import non BLS-IPS 
import java.net.URI;
  
import org.apache.logging.log4j.LogManager; 
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.components.util.IPSServiceLocator;
import gov.bls.ips.shared.logger.components.config.IPSLoggerConfig;  
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.spi.ExtendedLogger;

public abstract class IPSLogger { 
	public static String _CLASS_NAME=ClassFromStatic.getCurrentClass().getName();
	private static final String FQCN = IPSLogger.class.getName();
	static {
	    setConfigFile();
	  }
	/**
	   * The Logger is configured for the application, <<ApplicationName>>_LOGGER_CONFIG
	*/	
	public static void setConfigFile(){
	    String configFilename=null;
		 
	    try{
	    	System.err.println("Application Name    :"+IPSServiceLocator.getAPPContextName());
	    	configFilename=IPSServiceLocator.getAPPContextName()+IPSLoggerConfig._LOGGER_CONFIG;	
	    	configure(IPSLoggerConfig._IPS_CONFIG_PROPERTIES.getString(configFilename));
		} 
		catch(Exception ex){
			System.err.println("Exception in IPSLogger.setConfigFile():"+ex.getMessage());
		}
	 }
	
	/**
	   * The Logger can be configured with a Log4j2.xml as file name.
	   * @param configFilename   Logger config file 
	*/	
	public static void configure(String configFilename){
    
		try {

			System.err.println("Log4J ConfigFilename:"+configFilename);
			LoggerContext loggerContext=(LoggerContext) LogManager.getContext(false);
			java.net.URL url=Thread.currentThread().getContextClassLoader().getResource(configFilename);
			String fileNameFromURL=url.getFile();
			loggerContext.setConfigLocation(URI.create(fileNameFromURL));
			loggerContext.reconfigure(); 	
			logDebug(_CLASS_NAME,".configure(), LogConfigFileName:"+fileNameFromURL);
	    } 
    catch( java.util.MissingResourceException mre){
    	System.err.println("Exception in IPSLogger.configure():"+mre.getMessage());
    }
  }
  
  
  /**
   * Logs fatal message.
   * @param categoryName      Logger name
   * @param obj Object to be logged
   */
   public static void logFatal(String categoryName, Object obj) {
     //Logger category = LogManager.getLogger(categoryName);;
     //category.fatal(prepend(obj));
	   ExtendedLogger logger= (ExtendedLogger)LogManager.getLogger(categoryName);
	   LogManager.getLogger(categoryName);
	   logger.logIfEnabled(FQCN, Level.FATAL, null, prepend(obj));
   }
    
  /**
   * Logs fatal message with exception.
   * @param categoryName      Logger name
   * @param obj               Object to be logged
   * @param t Throwable to be logged
   */
  public static void logFatal(String categoryName, Object obj, Throwable t) {
	//Logger category = LogManager.getLogger(categoryName);;
   // category.fatal(prepend(obj), t);
	  ExtendedLogger logger= (ExtendedLogger)LogManager.getLogger(categoryName);
	  LogManager.getLogger(categoryName);
	  logger.logIfEnabled(FQCN, Level.FATAL, null, prepend(obj),t);
  }
    
    
  /**
   * Logs error message.
   * @param categoryName      Logger name
   * @param obj Object to be logged
   */
  public static void logError(String categoryName, Object obj) {
    //Logger category = LogManager.getLogger(categoryName);;
    //category.error(prepend(obj));
	  ExtendedLogger logger= (ExtendedLogger)LogManager.getLogger(categoryName);
	  LogManager.getLogger(categoryName);
	  logger.logIfEnabled(FQCN, Level.ERROR, null, prepend(obj));
  }
    
  /**
   * Logs error message with exception.
   * @param categoryName      Logger name
   * @param obj               Object to be logged
   * @param t Throwable to be logged
   */
  public static void logError(String categoryName, Object obj, 
		                     Throwable t) {
    //Logger category = LogManager.getLogger(categoryName);;
    //category.error(prepend(obj), t);
	  ExtendedLogger logger= (ExtendedLogger)LogManager.getLogger(categoryName);
	  LogManager.getLogger(categoryName);
	  logger.logIfEnabled(FQCN, Level.ERROR, null, prepend(obj), t);
  }
  
    
  /**
   * Logs warning message.
   * @param categoryName      Logger name
   * @param obj Object to be logged
   */
  public static void logWarning(String categoryName, Object obj) {
    //Logger category = LogManager.getLogger(categoryName);;
    //category.warn(prepend(obj));
	  ExtendedLogger logger= (ExtendedLogger)LogManager.getLogger(categoryName);
	  LogManager.getLogger(categoryName);
	  logger.logIfEnabled(FQCN, Level.WARN, null, prepend(obj));
  }
    
  /**
   * Logs warning message with exception.
   * @param categoryName      Logger name
   * @param obj               Object to be logged
   * @param t Throwable to be logged
   */
  public static void logWarning(String categoryName, Object obj, Throwable t){
    //Logger category = LogManager.getLogger(categoryName);;
    //category.warn(prepend(obj), t);
	  ExtendedLogger logger= (ExtendedLogger)LogManager.getLogger(categoryName);
	  LogManager.getLogger(categoryName);
	  logger.logIfEnabled(FQCN, Level.WARN, null, prepend(obj), t);
  }
    
     
  /**
   * Logs coarse-grained informational message.
   * @param categoryName      Logger name
   * @param obj Object to be logged
   */
  public static void logInfo(String categoryName, Object obj) {
    //Logger category = LogManager.getLogger(categoryName);;
    //category.info(prepend(obj));
	  ExtendedLogger logger= (ExtendedLogger)LogManager.getLogger(categoryName);
	  LogManager.getLogger(categoryName);
	  logger.logIfEnabled(FQCN, Level.INFO, null, prepend(obj));
  }
    
  /**
   * Logs coarse-grained informational message with exception.
   * @param categoryName      Logger name
   * @param obj               Object to be logged
   * @param t Throwable to be logged
   */
  public static void logInfo(String categoryName, Object obj, Throwable t) {
    //Logger category = LogManager.getLogger(categoryName);;
    //category.info(prepend(obj), t);
	  ExtendedLogger logger= (ExtendedLogger)LogManager.getLogger(categoryName);
	  LogManager.getLogger(categoryName);
	  logger.logIfEnabled(FQCN, Level.INFO, null, prepend(obj), t);
  }
    
    
  /**
   * Logs fine-grained debug message.
   * @param categoryName      Logger name
   * @param obj Object to be logged
   */
  public static void logDebug(String categoryName, Object obj) {
   // Logger category = LogManager.getLogger(categoryName);
   // category.debug(prepend(obj));	 
	 ExtendedLogger logger= (ExtendedLogger)LogManager.getLogger(categoryName);
	 LogManager.getLogger(categoryName);
	 logger.logIfEnabled(FQCN, Level.DEBUG, null, prepend(obj));	
  }
    
  /**
   * Logs fine-grained trace message.
   * @param categoryName      Logger name
   * @param obj Object to be logged
   */
  public static void logTrace(String categoryName, Object obj) {
   // Logger category = LogManager.getLogger(categoryName);
   // category.debug(prepend(obj));	 
	 ExtendedLogger logger= (ExtendedLogger)LogManager.getLogger(categoryName);
	 LogManager.getLogger(categoryName);
	 logger.logIfEnabled(FQCN, Level.TRACE, null, prepend(obj));	
  }
  
  /**
   * Logs fine-grained trace message with exception.
   * @param categoryName      Logger name
   * @param obj               Object to be logged
   * @param t Throwable to be logged
   */
  public static void logTrace(String categoryName, Object obj, Throwable t) {
    ExtendedLogger logger= (ExtendedLogger)LogManager.getLogger(categoryName);
	LogManager.getLogger(categoryName);
	logger.logIfEnabled(FQCN, Level.TRACE, null, prepend(obj), t);
  }
  
  /**
   * Logs fine-grained debug message with exception.
   * @param categoryName      Logger name
   * @param obj               Object to be logged
   * @param t Throwable to be logged
   */
  public static void logDebug(String categoryName, Object obj, Throwable t) {
   //Logger category = LogManager.getLogger(categoryName);;
   //category.debug(prepend(obj), t);
    ExtendedLogger logger= (ExtendedLogger)LogManager.getLogger(categoryName);
	LogManager.getLogger(categoryName);
	logger.logIfEnabled(FQCN, Level.DEBUG, null, prepend(obj), t);
  }
  
  /**
   * Check if Info logger level is enabled for the specified category.
   * @param categoryName      Logger name
   */  
  public static boolean isInfoEnabled(String categoryName) {
    Logger logger = LogManager.getLogger(categoryName);
    return logger.isInfoEnabled();
  }  
  
  /**
   * Check if Debug logger level is enabled for the specified category.
   * @param categoryName      Logger name
   */    
  public static boolean isDebugEnabled(String categoryName) {
    Logger logger = LogManager.getLogger(categoryName);
    return logger.isDebugEnabled();
  }  
  
  /**
   * Check if Warn logger level is enabled for the specified category.
   * @param categoryName      Logger name
   */    
  public static boolean isWarnEnabled(String categoryName) {
    Logger logger = LogManager.getLogger(categoryName);
    return logger.isWarnEnabled();
  }   
  
  /**
   * Check if Trace logger level is enabled for the specified category.
   * @param categoryName      Logger name
   */   
  public static boolean isTraceEnabled(String categoryName) {
	    Logger logger = LogManager.getLogger(categoryName);
	    return logger.isTraceEnabled();
	} 
        
  /**
   * Prepends to object to be logged
   * @param obj               Object to be logged
   * @return                  String prepending object to be logged with key 
   *                          
   */
  private static String prepend(Object obj){
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append((obj==null ? "null" : obj.toString().replace('\n', '|')));
    // Return string.
    return strBuilder.toString();
  }
}
