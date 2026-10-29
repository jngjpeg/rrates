package gov.bls.platform.log;

import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.logger.components.IPSLogger;

public class Log4jLogger implements ILog {
	
	public static String _CLASS_NAME=ClassFromStatic.getCurrentClass().getName();

	/**
	 * Record a Info level event. excution is continuing but notify something
	 * 
	 * @param location fully qualified class name and method where the warning was
	 *                 issued.
	 * @param message  log message.
	 */
	public void info(String location, String message) {
		IPSLogger.logInfo(_CLASS_NAME, buildLogMessage("INFO", location, message));
	}
	
		/**
	 * Record a Info level event. excution is continuing but notify something
	 * 
	 * @param location fully qualified class name and method where the warning was
	 *                 issued.
	 * @param message  log message.
	 */
	public void info(String message) {
		IPSLogger.logInfo(_CLASS_NAME, buildLogMessage("INFO", message));
	}

	/**
	 * Record a warning level event. Something unexpected happened, but request
	 * excution is continuing.
	 * 
	 * @param location fully qualified class name and method where the warning was
	 *                 issued.
	 * @param message  log message.
	 */
	public void warn(String location, String message) {
		IPSLogger.logWarning(_CLASS_NAME, buildLogMessage("WARN", location, message));
	}
	
	/**
	 * Record a warning level event. Something unexpected happened, but request
	 * excution is continuing.
	 * 
	 * @param location fully qualified class name and method where the warning was
	 *                 issued.
	 * @param message  log message.
	 */
	public void warn(String message) {
		IPSLogger.logWarning(_CLASS_NAME, buildLogMessage("WARN", message));
	}

	/**
	 * Record an error level event. Request execution was aborted.
	 * 
	 * @param location fully qualified class name and method where the warning was
	 *                 issued.
	 * @param message  log message.
	 */
	public void error(String location, String message) {
		IPSLogger.logError(_CLASS_NAME, buildLogMessage("ERROR", location, message));
	}
	
	/**
	 * Record an error level event. Request execution was aborted.
	 * 
	 * @param location fully qualified class name and method where the warning was
	 *                 issued.
	 * @param message  log message.
	 */
	public void error(String message) {
		IPSLogger.logError(_CLASS_NAME, buildLogMessage("ERROR", message));
	}	

	/**
	 * Record a warning level event. Critical system level event has occurred.
	 * Processes should be shutdown.
	 * 
	 * @param location fully qualified class name and method where the warning was
	 *                 issued.
	 * @param message  log message.
	 */
	public void fatal(String location, String message) {
		IPSLogger.logFatal(_CLASS_NAME, buildLogMessage("FATAL", location, message));
	}

	/**
	 * Record a warning level event. Critical system level event has occurred.
	 * Processes should be shutdown.
	 * 
	 * @param location fully qualified class name and method where the warning was
	 *                 issued.
	 * @param message  log message.
	 */
	public void fatal(String message) {
		IPSLogger.logFatal(_CLASS_NAME, buildLogMessage("FATAL", message));
	}
	
	
	public void test(String theLocation){
		IPSLogger.logInfo(_CLASS_NAME, buildLogMessage("INFO", theLocation, "Test log service availability"));
	}
	
	public void test(){
		IPSLogger.logInfo(_CLASS_NAME, buildLogMessage("INFO","Test log service availability"));
	}
	
	/**
	* @param message  log message.
	*/
	public void debug(String message){
		IPSLogger.logDebug(_CLASS_NAME, buildLogMessage("DEBUG",  message));
	}
	
	

	/**
	 * Build a properly formatted log message with Level location and Message
	 */
	private String buildLogMessage(String theLevel, String theLocation, String theMessage) {

		StringBuffer logMessage = new StringBuffer();
		logMessage.append("level=\"" + theLevel + "\"");
		logMessage.append(" location=\"" + theLocation + "\">");
		logMessage.append(theMessage);

		return logMessage.toString();
	}
	
	/**
	 * Build a properly formatted log message with Level location and Message
	 */
	private String buildLogMessage(String theLevel,String theMessage) {

		StringBuffer logMessage = new StringBuffer();
		logMessage.append("level=\"" + theLevel + "\">");
		logMessage.append(theMessage);

		return logMessage.toString();
	}	

	/*-End-*/
}