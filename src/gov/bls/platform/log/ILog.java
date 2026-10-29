/*=========================================================================
 * Copyright ©  2003 Bureau of Labor Statistics. ALL RIGHTS RESERVED.
 * Filename             ILog.java
 * Classes              ILog
 * @author              Rajkumar.VP (kumar_raj@bls.gov)
 * @version             %I%, %G%
 * @see
 * @since               Jun 05 2003
 * @lastModified        May 09 2004
 * ==========================================================================
 */

/*
 * Logging is intended to capture system failures and assist with troubleshooting
 * problems in production.  Hence it is always on. You should log as
 * exceptions are caught, wrapped and re-thrown, or unexpected responses are
 * received. Logging should not be used to track execution path or throw exceptions.
 */

package gov.bls.platform.log;



public interface ILog {

      /**
       * Record a Info level event.
       * excution is continuing but notify something
       * @param location fully qualified class name and method where the warning was issued.
       * @param message log message.
       */
      public void info(String location, String message);
	  public void info(String message);

      /**
       * Record a warning level event. Execution continues.
       * @param location FQCN + method where the warning was issued.
       * @param message log message.
       */
      public void warn(String location, String message);
	  public void warn(String message);


      /**
       * Record an error level event. Execution aborted.
       * @param location FQCN + method where the error was issued.
       * @param message log message.
       */
      public void error(String location, String message);
	  public void error(String message);

      /**
       * Record a warning level event.  Critical system level event has occurred.
       * Processes should be shutdown.
       * @param location FQCN + method where the warning was issued.
       * @param message log message.
       */
      public void fatal(String location, String message);
	  public void fatal(String message);

      /**
       * log service does not throw exceptions.  If the log service is unable to record the event then
       * the logging is sent to the console. Use this method to test the Log service availability.
       */
      public void test(String theLocation);
	  
	  public void test();
	  
	  
	/**
	* @param message  log message.
	* Date Added 4/21/2022
	*/
	public void debug(String message);

/*-End-*/
}