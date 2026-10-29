package gov.bls.responseRates.dao;

import java.util.ArrayList;
import gov.bls.platform.log.*;
import gov.bls.responseRates.service.dbAccess.DbConnection;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class MonthlyDataDAO {
	
    final static ILog logger = LogFactory.getLogger();
	
	public ArrayList<MonthlyData> runMonthlyData( String prelim_final, Integer period, java.sql.Date p_date_time_arg ) throws SQLException {
	
		DbConnection dconn = DbConnection.getInstance();    
		ArrayList<MonthlyData> rtnV = new ArrayList<MonthlyData>();		
		MonthlyData data_object = new MonthlyData();

		String db_response;
		CallableStatement cs = null;
		   
		try {    
			cs = dconn.getConnection().prepareCall("{call RESP_RATE_MONTHLY(?,?,?)}");               
		    cs.setString(1,prelim_final);
		    cs.setInt(2, period);
		    cs.setDate(3, p_date_time_arg);            
		    cs.execute();               
		    db_response="The stored procedure: RESP_RATE_MONTHLY ran successfully.";

		}catch (SQLException e) {
		    db_response = e.getMessage(); 
		    logger.error(e.getMessage());
		    throw e;
		}				
		            
		finally{
	        //closes all the database resources.
	        try{
	          if(cs!=null) cs.close();
              if(dconn!=null)dconn.closeConnection();
	        }catch(SQLException sqle){
	          System.err.println("INSIDE FINALLY CLOSING: "+sqle);
	        }
	}//end finally
		data_object.setFeedback(db_response);
		rtnV.add(data_object);
						
        return rtnV;        
	}    
        
}
