package gov.bls.responseRates.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gov.bls.platform.log.*;

import gov.bls.responseRates.service.dbAccess.DbConnection;

public class ActualTradePrelimGraphDAO {
	
    final static ILog logger = LogFactory.getLogger();
	
	public ArrayList<ActualTradePrelimGraphData> getActualTradePrelimGraphData() throws SQLException{
	
		DbConnection dconn = DbConnection.getInstance();    
		ArrayList<ActualTradePrelimGraphData> rtnV = new ArrayList<ActualTradePrelimGraphData>();		
		ActualTradePrelimGraphData data_object = new ActualTradePrelimGraphData ();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {    
			 stmt = dconn.getConnection().createStatement();               
							
			 rs = stmt.executeQuery("SELECT aggregation_type, period_,"+ 
											"resp_successful, resp_usable, resp_eligible "+
											"FROM response_aggregate "+
											"WHERE period_ >= 200201 "+
											"AND response_type = 'P' " +
											"ORDER BY period_, aggregation_type");
						
			while(rs.next()){		
      
				data_object.setAggType(rs.getString(1));
				data_object.setPeriod( rs.getInt(2) );
				data_object.setRespSuccessful( rs.getInt(3) );
				data_object.setRespUsable( rs.getInt(4) );
				data_object.setRespEligible( rs.getInt(5) );
				
				rtnV.add(data_object);
				data_object = new ActualTradePrelimGraphData();
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
