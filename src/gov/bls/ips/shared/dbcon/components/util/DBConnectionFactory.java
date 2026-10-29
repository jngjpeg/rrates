/*******************************************************************************
*
* FILE : DBConnectionFactory.java
*
* DESCRIPTION :
*      This class is a Database Connection factory which provides an API to
*      create/close database connections in the Weblogic environment.
*      It is a singleton
*
* REVISION HISTORY :
*
* Date         Name            Release             Description
* -------  -----------------   ----------     ---------------------------------
* 18Apr2011  Arun Jayavel                            original version
*
*  Copyright 2011 by All IPS-BLS .
* All rights reserved.
******************************************************************************/

// Package assignment
package gov.bls.ips.shared.dbcon.components.util;

// Non BLS-IPS Import declarations
import java.sql.*;
import javax.sql.DataSource;
import java.util.*;
import javax.naming.*;
import org.apache.commons.lang3.StringUtils;


//BLS-IPS Import declarations
import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.logger.components.IPSLogger;
import gov.bls.ips.shared.logger.components.config.IPSLoggerConfig;

// Class declaration
public class DBConnectionFactory{
   private static DBConnectionFactory _DB_CONNECTION_FACTORY = null; 
   private static InitialContext  _INITIAL_CONTEXT = null;
   public static final String  _TRANSACTION_LOOKUP_NAME="java:comp/UserTransaction";
   public static final String _CLASS_NAME = ClassFromStatic.getCurrentClass()
                                            .getName();
   private static String  _RESOURCE_REF= null;//For future implementation
   private static String _TRUE = "TRUE"; 
   private static String _PROPERTY_NAME_RESOURCE_REF = "RESOURCE_REF"; //For future implementation
   private static Hashtable<String,DataSource> _dataSourceHashtable=null;
   
   private  DBConnectionFactory() throws NamingException{
     //IPSLogger.logDebug(_CLASS_NAME,"DBConnectionFactory() BEGIN");
     try{
       // Setup the JNDI context for looking up the datasources
       DBConnectionFactory._INITIAL_CONTEXT= new javax.naming.InitialContext();
       DBConnectionFactory._dataSourceHashtable=new Hashtable<String,DataSource>();
       //setResourceRef();
	 }
	 catch (NamingException  ne){
       IPSLogger.logError(_CLASS_NAME,".DBConnectionFactory() " +
       		"Unable to createContext",ne);
       throw  ne;
     }
     //IPSLogger.logDebug(_CLASS_NAME,"DBConnectionFactory() END");
   }

   /**
	*	Get the Singleton instance
	*/
   public static  DBConnectionFactory  getInstance() throws Exception{
	 //IPSLogger.logDebug(_CLASS_NAME,"getInstance()");
     try{
       // if already initialized return reference to instance
       if (DBConnectionFactory._DB_CONNECTION_FACTORY != null){
         return  DBConnectionFactory._DB_CONNECTION_FACTORY;
       }
       synchronized (DBConnectionFactory._TRUE) {
         if(DBConnectionFactory._DB_CONNECTION_FACTORY==null)
           DBConnectionFactory._DB_CONNECTION_FACTORY = new DBConnectionFactory();
       }
     }
     catch (NamingException  ex){
       IPSLogger.logError(_CLASS_NAME+".getInstance() got an Exception",ex);
       throw  ex;
     }
	 //IPSLogger.logDebug(_CLASS_NAME,"getInstance() END");
     return  DBConnectionFactory._DB_CONNECTION_FACTORY;
   }

   /**
    *Get a DB connection for the specified data source
    *@param dsName 
    *@type String
    *@retrun DBConnection
    */
	public static DBConnection  getConnection(String  dataSourceName) 
	                                     throws Exception{
      java.sql.Connection  sqlConObj = null;
      DataSource  dataSource = null;
      DBConnection  dbConObj = null;
 	  //IPSLogger.logInfo(_CLASS_NAME,"getConnection() BEGIN");
      try{
    	DBConnectionFactory.getInstance();    	
    	dataSource = getDataSource(dataSourceName);
        // Get a JDBC Connection from the data source
    	sqlConObj = dataSource.getConnection();
        // Allocate a DBConnection object
        dbConObj = new  DBConnection(sqlConObj);
      }
      catch (SQLException  se){
        IPSLogger.logFatal(_CLASS_NAME+".getConnection() Unable to get " +
        		"Connection",se);
        throw  se;
      }
      finally{
        dataSource = null;
      }
 	  //IPSLogger.logInfo(_CLASS_NAME,"getConnection() END");
      return  dbConObj;
   }

   /**
    *Get a DB connection for the specified data source
    *@param dsName 
    *@type String
    *@retrun DBConnection
    */
   private static DataSource  getDataSource(String  dataSourceName) throws Exception{
	 //IPSLogger.logDebug(_CLASS_NAME,"getDataSource() BEGIN");
	 DataSource dataSource=null;
	 try{
	   if(StringUtils.isEmpty(DBConnectionFactory._RESOURCE_REF)==false)
	      dataSourceName=DBConnectionFactory._RESOURCE_REF+dataSourceName;
       IPSLogger.logInfo(_CLASS_NAME+"getDataSource() dataSourceName=",
    		 dataSourceName);
       // Lookup the given data source using the jndi context
       dataSource = (DataSource)DBConnectionFactory._dataSourceHashtable.
                      get(dataSourceName);
       if(dataSource==null){
    	 synchronized (DBConnectionFactory._TRUE) {    	   
	      dataSource=(DataSource) DBConnectionFactory._INITIAL_CONTEXT
	                    .lookup(dataSourceName);
	      DBConnectionFactory._dataSourceHashtable.
	                         put(dataSourceName, dataSource);
    	 }
       }
	 }
	 catch (NamingException  ne){
	   IPSLogger.logFatal(_CLASS_NAME+".getDataSource() Got " +
	   		     "NamingException Invalid data source",ne);
	   throw  ne;
	 }
	 catch (Exception  e){
	   IPSLogger.logFatal(_CLASS_NAME+".getDataSource() Got an Exception " +
	   		             "Invalid data source",e);
	   throw  e;
     }
	 //IPSLogger.logDebug(_CLASS_NAME,"getDataSource() END");
     return dataSource;	   
   }
	
   /**
    *Close the DB connection
    */
   public void  closeConnection(DBConnection  dbConObj){
     //IPSLogger.logDebug(_CLASS_NAME,"closeConnection() BEGIN");
     if(dbConObj!=null)
        dbConObj.close();
     //IPSLogger.logDebug(_CLASS_NAME,"closeConnection() END");
   }
   
   /**
    *setting the resource Ref 
    *For future implementation (after the research remove or implement)
    */
   public static void  setResourceRef(){
     //IPSLogger.logDebug(_CLASS_NAME,"setResourceRef() BEGIN");
     try{
       DBConnectionFactory._RESOURCE_REF=IPSLoggerConfig._RESOURCE_REF;
     }
     catch(Exception e){
       IPSLogger.logError(_CLASS_NAME+".setResourceRef() got an error getting " +
               		     "the resourceRef setting to the default value",e);
       DBConnectionFactory._RESOURCE_REF=null;
     }
     //IPSLogger.logDebug(_CLASS_NAME,"setResourceRef() END");
   }
   
   /**
    *setting the resource Ref
    */
   public static javax.transaction.UserTransaction lookupUserTx(){
     //IPSLogger.logDebug(_CLASS_NAME,"lookupUserTx() BEGIN");
     javax.transaction.UserTransaction userTx = null;
     try{
       DBConnectionFactory.getInstance();
       if(DBConnectionFactory._INITIAL_CONTEXT!=null)
    	 userTx = (javax.transaction.UserTransaction)DBConnectionFactory.
    	           _INITIAL_CONTEXT.lookup(DBConnectionFactory.
    	           _TRANSACTION_LOOKUP_NAME);
     }
     catch (Exception e) {
       IPSLogger.logError(_CLASS_NAME+".lookupUserTx() got an error getting " +
                       "Exception caught during lookup of UserTransaction",e);
     }     
     //IPSLogger.logDebug(_CLASS_NAME,"lookupUserTx() END");
     return userTx;
   }
   
}
