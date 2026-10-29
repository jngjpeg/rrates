package gov.bls.responseRates.dao;

import java.util.ArrayList;
import java.util.Date;
import gov.bls.platform.log.*;
import gov.bls.responseRates.service.dbAccess.DbConnection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import oracle.jdbc.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class SnapshotReportDAO {
	
    final static ILog logger = LogFactory.getLogger();
   
    //Throws propagates the exception up to the action.
    public java.sql.Date getMailoutDate( Integer period ) throws SQLException, Exception{
    	logger.info("getMailoutDate Begin.");
		DbConnection dconn = DbConnection.getInstance();    
		java.sql.Date rtnV = null;		
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		try {    
			 con=dconn.getNewConnection();
			 stmt = con.createStatement();       
			
			 rs = stmt.executeQuery( "SELECT event_date " +
											  "FROM est_schedule " + 
											  "WHERE event_code = 6 "+
											  "AND period_ = " + period );      
			
			/*If there is not event date for the above query it will throw an invalid
			  cursor position exception.*/
			System.err.println("getMailoutDate query:  SELECT event_date FROM est_schedule WHERE event_code = 6 AND period_ = "+period);
			rs.next();
			rtnV = rs.getDate(1);			
		logger.info("getMailoutDate End.");
        }catch (SQLException e) {
             logger.error(e.getMessage());
             throw e;
        }catch (Exception e){
            logger.error(e.getMessage());
        	throw e;
        }
		finally{
	        //closes all the database resources.
	        try{
	          
				  if(rs!=null) 
					rs.close();
		          if(stmt!=null) 
					stmt.close();
	              if(con!=null)
					con.close();
	        }catch(SQLException sqle){
	          System.err.println("INSIDE FINALLY CLOSING: "+sqle);
	        }
	    }//end finally
        
        return rtnV;  
    }
       
    public Integer getWorkDays( java.sql.Date p_start_date, java.sql.Date p_end_date ) throws SQLException, Exception{
    	logger.info("getWorkDays Begin.");
		DbConnection dconn = DbConnection.getInstance();    
		Integer rtnV;
		CallableStatement cs = null;
		Connection con=null;
		
		try {    
			 con=dconn.getNewConnection();
			 cs = con.prepareCall("{? = call F_COUNT_WORKDAYS(?,?)}"); 
	
			cs.registerOutParameter(1,java.sql.Types.INTEGER);
			
			cs.setDate(2, p_start_date);
			cs.setDate(3, p_end_date);
			
			cs.execute();  
	
			rtnV = cs.getInt(1);
		logger.info("getWorkDays End.");
        }catch (SQLException e) {
             logger.error(e.getMessage());
             throw e;
        }catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
       }
		  finally{
		        //closes all the database resources.
		        try{ 
		          if(cs!=null) 
					cs.close();
	              if(con!=null)
					con.close();
		        }catch(SQLException sqle){
		          System.err.println("INSIDE FINALLY CLOSING:"+sqle);
		        }
		  }//end finally        
        return rtnV;
    }
    
	public ArrayList<SnapshotReportData> getSnapshotData( Integer period, java.sql.Date date_time  ) throws SQLException, Exception{
	    logger.info("getSnapshotData Begin.");
		DbConnection dconn = DbConnection.getInstance();    
		ArrayList<SnapshotReportData> rtnV = new ArrayList<SnapshotReportData>();
	
		SnapshotReportData data_object = new SnapshotReportData();
		Connection con=null;
		CallableStatement cs = null;		
		ResultSet rs	=null;	
		try {    
			System.out.println("In try block. Code Change 5.");
			con=dconn.getNewConnection();
			//CallableStatement cs = dconn.getConnection().prepareCall("{call RESP_RATE_SNAPSHOT(?, ?)}");            
			 cs = con.prepareCall("{call RESP_RATE_SNAPSHOT(?, ?, ?)}");
			//CallableStatement cs = dconn.getConnection().prepareCall("BEGIN RESP_RATE_SNAPSHOT(?, ?, ?); END;");
			cs.setInt(1, period);
			cs.setDate(2, date_time);
			/*
			cs.setInt(1, 201203);
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date setDate = new Date(0);
			long dateTime =0;
			java.sql.Date sqlDate = new java.sql.Date(0);
			setDate = (Date)df.parse("2/4/2012");
			dateTime = setDate.getTime();
			sqlDate = new java.sql.Date(dateTime);
			
			cs.setDate(2, sqlDate);*/
			//added code
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			
			//ResultSet rs = cs.executeQuery(); 
			logger.info("getSnapshotData exec procedure RESP_RATE_SNAPSHOT.");
			cs.execute();	
			rs = (ResultSet) cs.getObject(3);
			//ResultSet rs = ((OracleCallableStatement)cs).getCursor(3);
			logger.info("getSnapshotData exec procedure ResultSet loop start.");
			while(rs.next()){		

				data_object.setModeOfCollection( rs.getString(2) );
				data_object.setRequested( rs.getString(3) );
				data_object.setSuccessful( rs.getInt(4) );
				data_object.setPctSuccessful( rs.getDouble(5) );
				data_object.setUsable( rs.getInt(6) );
				data_object.setPctUsable( rs.getDouble(7) );
				data_object.setEligible(rs.getInt(8));
								
				rtnV.add(data_object);
				
				data_object = new SnapshotReportData();
			}
			logger.info("getSnapshotData End.");
        }catch (SQLException e) {
        	 logger.error("Code changes working.");
             logger.error(e.getMessage());
             throw e;
         }catch (Exception e) {
             logger.error(e.getMessage());
             throw e;
         }
		  finally{
		        //closes all the database resources.
		        try{
				  if(rs!=null) 
					rs.close();
		          if(cs!=null) 
					cs.close();
	              if(con!=null)
					con.close();
		        }catch(SQLException sqle){
		          System.err.println("INSIDE FINALLY CLOSING: "+sqle);
		        }
		  }//end finally        
		return rtnV;        
	}    
        
}
