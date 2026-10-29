package gov.bls.responseRates.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gov.bls.platform.log.*;

import gov.bls.responseRates.service.dbAccess.DbConnection;

public class ActualRequestedPrelimGraphDAO {
	
    final static ILog logger = LogFactory.getLogger();
	
	public ArrayList<ActualRequestedPrelimGraphData> getActualRequestedPrelimGraphData() throws SQLException{
	
		DbConnection dconn = DbConnection.getInstance();    
		ArrayList<ActualRequestedPrelimGraphData> rtnV = new ArrayList<ActualRequestedPrelimGraphData>();		
		ActualRequestedPrelimGraphData data_object = new ActualRequestedPrelimGraphData ();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {    
			 stmt = dconn.getConnection().createStatement();               						
			 rs = stmt.executeQuery("SELECT requested_, period_, SUM(resp_successful), "+ 
											"SUM(resp_usable), SUM(resp_eligible) "+
											"FROM response_amc_req "+
											"WHERE response_type = 'P' "+
											"GROUP BY requested_, period_ " +
											"ORDER BY period_, requested_");
						
			while(rs.next()){		
      
				data_object.setRequested(rs.getString(1));
				data_object.setPeriod( rs.getInt(2) );
				data_object.setRespSuccessful( rs.getInt(3) );
				data_object.setRespUsable( rs.getInt(4) );
				data_object.setRespEligible( rs.getInt(5) );
				
				rtnV.add(data_object);
				data_object = new ActualRequestedPrelimGraphData();
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
