/*******************************************************************************
 *
 * FILE : DBConnection.java
 *
 * DESCRIPTION :
 *      This class encapsulates a JDBC database connection which provides
 *      convenient APIs for managing prepared and regular SQL statements,
 *      query execution and returning Result Sets.
 *
 *  REVISION HISTORY :
 *
 * Date         Name            Release              Description
 * -------  -----------------   ----------     ---------------------------------
 * 18Apr2011  Arun Jayavel                            original version
 *
 *  Copyright 2011 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
// Package assignment
package gov.bls.ips.shared.dbcon.components.util;
// Import non BLS-IPS 
import java.sql.*;
import java.util.*;

//Import BLS-IPS 
import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.logger.components.IPSLogger;

// Class declaration
public class DBConnection {
  private java.sql.Connection  _sqlConObj;
  private List<java.sql.ResultSet>  _resultSets;
  private List<java.sql.Statement>  _statements;
  private List<java.sql.PreparedStatement>  _preparedStatements;
  private List<java.sql.CallableStatement>  _callableStateMents;
  public static final String _CLASS_NAME = ClassFromStatic.getCurrentClass()
                                           .getName();  
   
  /**
   * Class constructor
   * @param   sqlConObj and JDBC connection
   * @return
   * @throws
   */
  public DBConnection(Connection  sqlConObj) {
	//IPSLogger.logDebug(_CLASS_NAME,".DBConnection() BEGIN");	  
    this._sqlConObj = sqlConObj;
    init();
    //IPSLogger.logDebug(_CLASS_NAME,"DBConnection() END");	  
  }

  /**
   * @param   None
   * @return
   * @throws
   */
  private void init() {
	//IPSLogger.logDebug(_CLASS_NAME,"init() BEGIN");	  
    this._resultSets = null;
    this._statements = null;
    this._preparedStatements = null;
    this._callableStateMents = null;
	//IPSLogger.logDebug(_CLASS_NAME,"init() END");	  
  }

  /**
   * Create a prepared statement from the connection
   * @param   SQL Query
   * @return  PreparedStatement
   * @throws  SQLException
   */
  public java.sql.PreparedStatement  createPreparedStatement(String  sql) 
                                     throws SQLException {
    //IPSLogger.logDebug(_CLASS_NAME,".createPreparedStatement() BEGIN");	  
    java.sql.PreparedStatement  preparedStatement = null;
    try {
      if(this._sqlConObj != null)
        preparedStatement = this._sqlConObj.prepareStatement(sql);
      // Add prepared statement to the list of prepared Statements
      if(this._preparedStatements==null)
    	this._preparedStatements = new LinkedList<java.sql.PreparedStatement>();
      this._preparedStatements.add(preparedStatement);
    }
    catch (SQLException  se) {
      IPSLogger.logError(_CLASS_NAME,".createPreparedStatement() " +
           		      "Unable to create prepared statement",se);
      throw se;
    }
    //IPSLogger.logDebug(_CLASS_NAME,".createPreparedStatement() END");	  
    return  preparedStatement;
  }

  /**
   * Create a callable statement from the connection
   * @param   SQL Query
   * @return  CallableStatement
   * @throws  SQLException
   */
  public java.sql.CallableStatement  createCallableStatement(String  sql) 
                                     throws SQLException {
    //IPSLogger.logDebug(_CLASS_NAME,".createCallableStatement() BEGIN");	  
    java.sql.CallableStatement  callableStatement = null;
    try {
      if(this._sqlConObj != null)
        callableStatement = this._sqlConObj.prepareCall(sql);
      // Add callable statement to the callableStatementlist
      if(this._callableStateMents==null)
        this._callableStateMents = new LinkedList<java.sql.CallableStatement>();
      this._callableStateMents.add(callableStatement);
    }
    catch (SQLException  se) {
      IPSLogger.logError(_CLASS_NAME,".createCallableStatement() " +
     		      "Unable to create callable statement",se);
      throw  se;
    }
    //IPSLogger.logDebug(_CLASS_NAME,".createCallableStatement() END");	  
    return  callableStatement;
  }

  /**
   * Create a regular JDBC statement
   * @param
   * @return  Statement
   * @throws  SQLException
   */
  public java.sql.Statement  createStatement() throws  SQLException {
    //IPSLogger.logDebug(_CLASS_NAME,".createStatement() BEGIN");	  
    java.sql.Statement  statement = null;
    try {
      if (this._sqlConObj != null)
        statement = this._sqlConObj.createStatement();
        // Add the statement to the list of statements
        if(this._statements==null)
          this._statements = new LinkedList<java.sql.Statement>();
        this._statements.add(statement);
      }
    catch (SQLException  se) {
      IPSLogger.logError(_CLASS_NAME,".createStatement() " +
   		              "Unable to create statement",se);
      throw se;
    }
    //IPSLogger.logDebug(_CLASS_NAME,".createStatement() END");	  
    return statement;
  }

  /**
   * Execute a JDBC INSERT, UPDATE, DELETE Prepared Statement
   * @param   PreparedStatement to be executed
   * @return  number of rows updated
   * @throws  SQLException
   */
  public int  executeUpdate(PreparedStatement  preparedStatement)  
                            throws SQLException {
    //IPSLogger.logDebug(_CLASS_NAME,".executeUpdate() BEGIN");
    int  numRows = 0;
    try {
      if (preparedStatement != null)
        numRows = preparedStatement.executeUpdate();
    }
    catch(SQLException  se) {
      IPSLogger.logError(_CLASS_NAME,".executeUpdate() " +
		              "Unable to execute update",se);
      throw  se;
    }
    //IPSLogger.logDebug(_CLASS_NAME,".executeUpdate() END");
    return  numRows;
  }

  /**
   * Execute a JDBC INSERT, UPDATE, DELETE Statement
   * @param   statement to be executed, SQL query to execute
   * @return  number of rows updated
   * @throws  SQLException
   */
  public int  executeUpdate(Statement stmt, String sql) throws SQLException{
    //IPSLogger.logDebug(_CLASS_NAME,".executeUpdate() BEGIN");
    int  numRows = 0;
    try {
      if(stmt != null)
        numRows = stmt.executeUpdate(sql);
    }
    catch (SQLException  se) {
      IPSLogger.logError(_CLASS_NAME,".executeUpdate() " +
	                  "Unable to execute update",se);
      throw se;
    }
    //IPSLogger.logDebug(_CLASS_NAME,".executeUpdate() END");
    return numRows;
  }

  /**
   * Execute a JDBC INSERT, UPDATE, DELETE Statement
   * @param   SQL query
   * @return  number of rows updated
   * @throws  SQLException
   */
  public int  executeUpdate(String  sql) throws SQLException {
	//IPSLogger.logDebug(_CLASS_NAME,".executeUpdate() BEGIN");
    int  numRows = 0;
    try {
      java.sql.Statement statement = createStatement();
      numRows = executeUpdate(statement, sql);
      closeStatement(statement);
    }
    catch (SQLException  se) {
      IPSLogger.logError(_CLASS_NAME,".executeUpdate() " +
                      "Unable to execute update",se);
      throw  se;
    }
    //IPSLogger.logDebug(_CLASS_NAME,".executeUpdate() END");
    return  numRows;
  }

  /**
   * Execute a JDBC Prepared Statement to return a result set
   * @param   Prepared Statement to be executed
   * @return  ResultSet
   * @throws  SQLException
   */
   public ResultSet  executeQuery(PreparedStatement  pstmt) 
                                  throws SQLException {
     //IPSLogger.logDebug(_CLASS_NAME,".executeQuery() BEGIN");
     java.sql.ResultSet  resultSet = null;
     try {
       if (pstmt != null)
         resultSet = pstmt.executeQuery();
       if(this._resultSets==null)
         this._resultSets = new ArrayList<java.sql.ResultSet>();
       this._resultSets.add(resultSet);
     }
     catch (SQLException  se) {
       IPSLogger.logError(_CLASS_NAME,".executeQuery() " +
                      "Unable to execute query",se);
       throw  se;
     }
     //IPSLogger.logDebug(_CLASS_NAME,".executeQuery() END");
     return  resultSet;
  }

  /**
   * Execute a JDBC Statement to return a result set
   * @param   Statement, SQL query
   * @return  ResultSet
   * @throws  SQLException
   */
  public ResultSet  executeQuery(Statement  stmt, String  sql) 
                                  throws SQLException {
    //IPSLogger.logDebug(_CLASS_NAME,".executeQuery() BEGIN");
    java.sql.ResultSet  resultSet = null;
    try {
      if (stmt != null)
        resultSet = stmt.executeQuery(sql);
      // Add result set to the this._resultSets vector
      if(this._resultSets==null)
          this._resultSets = new ArrayList<java.sql.ResultSet>();
      this._resultSets.add(resultSet);
    }
    catch (SQLException  se) {
      IPSLogger.logError(_CLASS_NAME,".executeQuery() " +
                      "Unable to execute query",se);
      throw  se;
    }
    //IPSLogger.logDebug(_CLASS_NAME,".executeQuery() END");
    return  resultSet;
  }

  /**
   *  Execute a SQL query to return a result set. Allocate
   *  a JDBC statement object
   *  @param  SQL string
   *  @return PreparedStatement
   *  @throws SQLException
   */
  public ResultSet  executeQuery(String  sql) throws SQLException {
    java.sql.ResultSet  resultSet = null;
    java.sql.PreparedStatement pStatement=null;
    //IPSLogger.logDebug(_CLASS_NAME,".executeQuery() BEGIN");
    try {
      pStatement = createPreparedStatement(sql);
      resultSet = executeQuery(pStatement);
      if(this._preparedStatements==null)
      	this._preparedStatements = new ArrayList<java.sql.PreparedStatement>();
      this._preparedStatements.add(pStatement);
      if(this._resultSets==null)
          this._resultSets = new ArrayList<java.sql.ResultSet>();
      this._resultSets.add(resultSet);
    }
    catch (SQLException  se) {
      IPSLogger.logError(_CLASS_NAME,".executeQuery() " +
                     "Unable to execute query",se);
      throw  se;
    }
    //IPSLogger.logDebug(_CLASS_NAME,".executeQuery() END");
    return  resultSet;
  }

  /**
   * This method sets connection's auto commit mode.
   * @param autoCommit boolean flag. True will turn the auto-commit 
   *        on else otherwise.
   * @throws SQLException  Propogates the exception that was 
   *         thrown by connection object.
   */
  public void setAutoCommit(boolean  autoCommit) throws SQLException {
	//IPSLogger.logDebug(_CLASS_NAME,".setAutoCommit() BEGIN");
    try {
     if(this._sqlConObj != null)
        this._sqlConObj.setAutoCommit(autoCommit);
    }
    catch(SQLException se ) {
      IPSLogger.logError(_CLASS_NAME,".setAutoCommit() " +
                      "Unable to set commit attribute",se);
      throw  se;
    }
	//IPSLogger.logDebug(_CLASS_NAME,".setAutoCommit() END");
  }

  /**
   * This method sets connection's auto commit mode.
   * @param autoCommit boolean flag. True will turn the auto-commit on else otherwise.
   * @throws SQLException  Propogates the exception that was thrown by connection object.
   */
  public void commit() throws SQLException {
    //IPSLogger.logDebug(_CLASS_NAME,".commit() BEGIN");
    try {
     if(this._sqlConObj != null)
        this._sqlConObj.commit();
    }
    catch(SQLException se ) {
      IPSLogger.logError(_CLASS_NAME,".commit() " +
                "Unable to execute commit",se);
      throw  se;
    }
    //IPSLogger.logDebug(_CLASS_NAME,".commit() END");
  }
  
  /**
   * rollback the transaction
   */ 
  public void rollback() throws SQLException{
	//IPSLogger.logDebug(_CLASS_NAME,".rollback() BEGIN");
    try {
      if(this._sqlConObj != null)
        this._sqlConObj.rollback();
    }
    catch(SQLException se ) {
      IPSLogger.logError(_CLASS_NAME,".rollback() " +
                     "Unable to execute rollback",se);
      throw se;
    }
	//IPSLogger.logDebug(_CLASS_NAME,".rollback() END");
  }

  /**
   *Close the list of statements
   *@param
   *@return
   * @throws Exception 
   */
  private void  closeStatements(){
	//IPSLogger.logDebug(_CLASS_NAME,".closeStatements() BEGIN");
	try{
      for(Statement statement:this._statements)
    	this.closeStatement(statement);    	  
	}
	catch(Exception e){
      IPSLogger.logFatal(_CLASS_NAME+".closeStatements() " +
      		"got an Exception",e);
	}
	finally{
      this._statements.clear();
	  this._statements = null;
	}
	//IPSLogger.logDebug(_CLASS_NAME,".closeStatements() END");
  }
  
  
  /**
   *Close all the result sets
   *@param
   *@return
   * @throws Exception 
   */
  private void  closeResultSets(){
	//IPSLogger.logDebug(_CLASS_NAME,".closeResultSets() BEGIN");
	try{
      for(ResultSet resultSet:this._resultSets)
       this.closeResultSet(resultSet);      
	}
	catch(Exception e){
      IPSLogger.logFatal(_CLASS_NAME+".closeResultSets() " +
      		"got an Exception",e);
	}
	finally{
      this._resultSets.clear();
	  this._resultSets = null;
	}
	//IPSLogger.logDebug(_CLASS_NAME,".closeResultSets() END");
  }
  

  /**
   *Close the prepared statements
   *@param
   *@return
   * @throws Exception 
   */
  private void  closePreparedStatements(){
	//IPSLogger.logDebug(_CLASS_NAME,".closePreparedStatements() BEGIN");
	try{
      for(PreparedStatement prepStatement:this._preparedStatements)
        this.closePreparedStatement(prepStatement);
	}
	catch(Exception e){
      IPSLogger.logFatal(_CLASS_NAME+".closePreparedStatements() " +
      		"got an Exception",e);
	}
	finally{
      this._preparedStatements.clear();
	  this._preparedStatements = null;
	}
	//IPSLogger.logDebug(_CLASS_NAME,".closePreparedStatements() END");
  }
  
  
  /**
   *Close the callable statements
   *@param
   *@return
   * @throws Exception 
   */
  private void  closeCallableStatements(){
	//IPSLogger.logDebug(_CLASS_NAME,".closeCallableStatements() BEGIN");
	try{
      for(CallableStatement callStatement:this._callableStateMents)
       this.closeCallableStatement(callStatement);    	  
	}
	catch(Exception e){
      IPSLogger.logFatal(_CLASS_NAME+".closeCallableStatements() " +
      		"got an Exception",e);
	}
	finally{
      this._callableStateMents.clear();
	  this._callableStateMents = null;
    }
	//IPSLogger.logDebug(_CLASS_NAME,".closeCallableStatements() END");
  }
   
  /**
   * Close the argument result set
   * @param   Result set to close
   * @return
   * @throws
   */
  public void  closeResultSet(ResultSet  rSet) {
    //IPSLogger.logDebug(_CLASS_NAME,".closeResultSet() BEGIN");
    try {
      if (rSet!= null)
    	  rSet.close();
      rSet = null;
    }
    catch (SQLException  se) {
      IPSLogger.logFatal(_CLASS_NAME,".closeResultSet() " +
                "Unable to close result set",se);
    }
    //IPSLogger.logDebug(_CLASS_NAME,".closeResultSet() END");
  }

  /**
   *Close the argument prepared statement
   * @param   Prepared Statement to close
   * @return
   * @throws
   */
  public void  closePreparedStatement(PreparedStatement  pStmt) {
	//IPSLogger.logDebug(_CLASS_NAME,".closePreparedStatement() BEGIN");
    try {
      if (pStmt != null)
        pStmt.close();
      pStmt = null;
    }
    catch (SQLException  se) {
      IPSLogger.logFatal(_CLASS_NAME,".closePreparedStatement() " +
                      "Unable to close prepared statement",se);
    }
    //IPSLogger.logDebug(_CLASS_NAME,".closePreparedStatement() END");
  }
    
  /**
   *Close the argument callable statement
   * @param   Callable Statement to close
   * @return
   * @throws
   */
  public void  closeCallableStatement(CallableStatement  cStmt) {
    //IPSLogger.logDebug(_CLASS_NAME,".closeCallableStatement() BEGIN");
    try {
      if (cStmt != null)
        cStmt.close();
      cStmt = null;
    }
    catch (SQLException  se) {
      IPSLogger.logFatal(_CLASS_NAME,".closeCallableStatement() " +
                      "Unable to close callable statement",se);

    }
	//IPSLogger.logDebug(_CLASS_NAME,".closeCallableStatement() END");
  }

  /**
   *Close the argument statement
   * @param   stmt Statement
   * @return
   * @throws
   */
  public void  closeStatement(Statement  stmt) {
    //IPSLogger.logDebug(_CLASS_NAME,".closeStatement() BEGIN");
    try {
      if (stmt != null)
        stmt.close();
      stmt = null;
    }
    catch (SQLException  se) {
      IPSLogger.logFatal(_CLASS_NAME,".closeStatement() " +
                      "Unable to close statement",se);
    }
	//IPSLogger.logDebug(_CLASS_NAME,".closeStatement() END");
  }

  /**
   * @param   
   * @return
   * @throws
   */
  private void  closeConnection() {
	//IPSLogger.logDebug(_CLASS_NAME,".closeConnection() BEGIN");
    try {
      this._sqlConObj.close();
      this._sqlConObj = null;
    }
    catch (SQLException  se) {
      IPSLogger.logFatal(_CLASS_NAME,".closeConnection() " +
                      "Unable to close statement",se);
    }
    //IPSLogger.logDebug(_CLASS_NAME,".closeConnection() END");
  }

  /**
   * Close the connection
   * @param
   * @return
   * @throws
   */
  public void  close() {
    //IPSLogger.logInfo(_CLASS_NAME,".close() BEGIN");
    try{
      if(this._resultSets!=null)this.closeResultSets();
      if(this._preparedStatements!=null)this.closePreparedStatements();
      if(this._statements!=null)this.closeStatements();
      if(this._callableStateMents!=null)this.closeCallableStatements();        
      if (this._sqlConObj != null)this.closeConnection();
    }
    catch(Exception e){
      IPSLogger.logError(_CLASS_NAME,".close() " +
                "Unable to close db connection resources",e);
    }
	//IPSLogger.logInfo(_CLASS_NAME,".close() END");
  }
  /*
  protected void finalize() throws Throwable {
	 close();
	 super.finalize();
  }
  */
  /**
   * This method returns data base connection.
   * @return java.sql.Connection
   */
  public Connection  getConnection() {
	    
	   return _sqlConObj;
		
	  }
}
