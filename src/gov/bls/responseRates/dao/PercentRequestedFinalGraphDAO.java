package gov.bls.responseRates.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gov.bls.platform.log.*;

import gov.bls.responseRates.service.dbAccess.DbConnection;

public class PercentRequestedFinalGraphDAO {
	
    final static ILog logger = LogFactory.getLogger();
	
	public ArrayList<PercentRequestedFinalGraphData> getPercentRequestedFinalGraphData() throws SQLException{
	
		DbConnection dconn = DbConnection.getInstance();    
		ArrayList<PercentRequestedFinalGraphData> rtnV = new ArrayList<PercentRequestedFinalGraphData>();		
		PercentRequestedFinalGraphData data_object = new PercentRequestedFinalGraphData ();
		Statement stmt = null;
		ResultSet rs = null;				
		try {    
			 stmt = dconn.getConnection().createStatement();               
							
			 rs = stmt.executeQuery("SELECT requested_, period_, SUM(resp_successful)/SUM(resp_eligible)*100 AS pct_successful, SUM(resp_usable)/SUM(resp_eligible)*100 AS pct_usable " +
					"FROM response_amc_req " +
					"WHERE response_type = 'F' " +
					"GROUP BY requested_, period_ " +
					"ORDER BY period_, requested_");
					
			while(rs.next()){		
      
				data_object.setRequested(rs.getString(1));
				data_object.setPeriod( rs.getInt(2) );
				data_object.setPctSuccessful( rs.getDouble(3) );
				data_object.setPctUsable( rs.getDouble(4) );
				
				rtnV.add(data_object);
				data_object = new PercentRequestedFinalGraphData();
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
