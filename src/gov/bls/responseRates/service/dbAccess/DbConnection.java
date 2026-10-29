/*
 * DbConnection.java
 *
 * Created on September 19, 2007, 8:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.bls.responseRates.service.dbAccess;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.sql.DataSource;


/*  
 *  Application of the Singleton Pattern - one instance of a class or one value accessible
 *  globally in an application.  Well controlled instantiation of a class.
 *
 *  The following sources were used: 
        1) Part of Chris Elrod's connection pooling version of Dr. Pu's. DbConnectionUtil, (UMUC CMSC 480 Project3).
 *      2) Singleton Link: http://www.javacamp.org/designPattern/index.html
 *      3) Singleton Link: http://www.exciton.cs.rice.edu/JAvaResources/DesignPatterns/singleton.htm
 *      4) "SCJP Exam for J2SE 5 Platform", by Sanghera, pgs. 111-113. for 
 *
 *  By Chris Elrod
 */

//Singleton pattern - class declared final to prevent cloning.
public final class DbConnection {

    private Connection conn;
    
    //Singleton Pattern - clients have no access to the private constructor.
    private DbConnection() {}
    
    //Singleton Pattern - static ensures one instance of DbConnection.
    public static DbConnection getInstance(){
        DbConnection instance = new DbConnection();
        return instance;
    }
    
    //Access the Application Server's pre-pooled connections. --revert to weblogic connection
    public Connection getConnection(){
	
        if (conn==null){
            try {
                JDBCServiceLocator jdbc_service = JDBCServiceLocator.getInstance();
                //InitialContext ctx=new InitialContext();
                /*The development database.*/
                //DataSource ds = jdbc_service.getDataSource("omdev");
                DataSource ds = jdbc_service.getDataSource("jdbc.rRates");
				//DataSource ds = (DataSource) ctx.lookup("jdbc.rRates");
                //DataSource ds = jdbc_service.getDataSource("IPPTEST11G");
                //DataSource ds = jdbc_service.getDataSource("prod");
				
                conn = ds.getConnection();

                }catch (Exception e) { 
				System.out.println(e.toString());
				 
				}         
        }
        return conn;
    }
    
	//Creater new connection for long running reports like snapshot and close the Connection in finally block inside the DAO
    public Connection getNewConnection(){
	
        Connection con=null;
            try {
                JDBCServiceLocator jdbc_service = JDBCServiceLocator.getInstance();
                //InitialContext ctx=new InitialContext();
                /*The development database.*/
                //DataSource ds = jdbc_service.getDataSource("omdev");
                DataSource ds = jdbc_service.getDataSource("jdbc.rRates");
				//DataSource ds = (DataSource) ctx.lookup("jdbc.rRates");
                //DataSource ds = jdbc_service.getDataSource("IPPTEST11G");
                //DataSource ds = jdbc_service.getDataSource("prod");
				
                con = ds.getConnection();

                }catch (Exception e) { 
				System.out.println(e.toString());
				 
				}         
        
        return con;
    }
	
	
    public void closeConnection() {
    	if (conn!=null) {
            try {
                conn.close();
                }catch (SQLException e) {
                    System.out.println(e.toString());}
    	}
    }
    
    /*The finalize method of Object is called during garbage collection.  
      Overriding the method dictates finalize is called is this way (closing the connection) 
      at garbage collection.  The effect is the database connection is closed if not 
      in use during garbage collection.  
      Protected limits access to the package.*/ 
    
    protected void finalize() throws Throwable {
        super.finalize();
    	closeConnection();  
        //logger.error("Database finalize() method called (DbConnection.finalize()");                  
    }
}