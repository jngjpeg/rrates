package gov.bls.responseRates.dao;

import java.util.ArrayList;
import gov.bls.platform.log.*;
import gov.bls.responseRates.service.dbAccess.DbConnection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;


public class MonthlyPrelimReportDAO {
	
    final static ILog logger = LogFactory.getLogger();
	
	public ArrayList<MonthlyPrelimReportData> getMonthlyPrelimReportData( String agg_type ) throws SQLException{
	
		DbConnection dconn = DbConnection.getInstance();    
		ArrayList<MonthlyPrelimReportData> rtnV = new ArrayList<MonthlyPrelimReportData>();		
		MonthlyPrelimReportData data_object = new MonthlyPrelimReportData();
		Statement stmt = null;
		ResultSet rs = null;		
		try {    
			 stmt = dconn.getConnection().createStatement();               
				
			 rs = stmt.executeQuery("SELECT period_,resp_successful,resp_usable, resp_eligible," +
											"ROUND(pct_successful,2), ROUND(pct_usable,2), ROUND(st_pct_successful,2),"+ 
											"ROUND(lt_pct_successful,2),ROUND(st_pct_usable,2), ROUND(lt_pct_usable,2) "+
											"FROM RESPONSE_AGGREGATE "+
											"WHERE response_type = 'P' "+
											"AND aggregation_type = '"+agg_type+"' "+ 
											"ORDER BY period_ DESC" );             
			
			while(rs.next()){		
      
				data_object.setPeriod( rs.getInt(1) );
				data_object.setRespSuccessful( rs.getInt(2) );
				data_object.setRespEligible( rs.getInt(3) );
				data_object.setRespUsable( rs.getInt(4) );
				data_object.setPctSuccessful( rs.getDouble(5) );
				data_object.setPctUsable( rs.getDouble(6) );
				data_object.setStPctSuccessful( rs.getDouble(7));
				data_object.setLtPctSuccessful( rs.getDouble(8));
				data_object.setStPctUsable( rs.getDouble(9));
				data_object.setLtPctUsable( rs.getDouble(10));
				
				rtnV.add(data_object);
				data_object = new MonthlyPrelimReportData();
			}
			
        }catch (SQLException e) {
             logger.error(e.getMessage());
             throw e;
         }
		finally{
	        //closes all the database resources.
	        try{
	          if(rs!=null) rs.close();
	          if(stmt!=null) stmt.close();
              if(dconn!=null)dconn.closeConnection();
	        }catch(SQLException sqle){
	          System.err.println("INSIDE FINALLY CLOSING: "+sqle);
	        }
	}//end finally
        return rtnV;        
	}    
        
}
