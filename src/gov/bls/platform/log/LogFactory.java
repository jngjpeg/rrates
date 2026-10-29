/*=========================================================================
 * Copyright ©  2003 Bureau of Labor Statistics. ALL RIGHTS RESERVED.
 * Filename             LogFactory.java
 * Classes              LogFactory
 * @author              Rajkumar.VP (kumar_raj@bls.gov)
 * @version             %I%, %G%
 * @see
 * @since               Jun 05 2003
 * @lastModified        May 09 2004
 * ==========================================================================
 */
package gov.bls.platform.log;



public class LogFactory {

      private static ILog _logger;

      public static ILog getLogger() {
            try {
                  if (_logger == null) _logger = new Log4jLogger();
            }
            catch (Throwable e) { System.out.println(e.getMessage()); }
       return _logger;
      }

/*-End-*/
}