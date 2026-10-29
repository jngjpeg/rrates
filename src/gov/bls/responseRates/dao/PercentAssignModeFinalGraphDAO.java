package gov.bls.responseRates.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gov.bls.platform.log.*;

import gov.bls.responseRates.service.dbAccess.DbConnection;

public class PercentAssignModeFinalGraphDAO {
	
    final static ILog logger = LogFactory.getLogger();
	
	public ArrayList<PercentAssignModeFinalGraphData> getPercentAssignModeFinalGraphData() throws SQLException{
	
		DbConnection dconn = DbConnection.getInstance();    
		ArrayList<PercentAssignModeFinalGraphData> rtnV = new ArrayList<PercentAssignModeFinalGraphData>();		
		PercentAssignModeFinalGraphData data_object = new PercentAssignModeFinalGraphData ();
		Statement stmt = null;
		ResultSet rs = null;		
		try {    
			 stmt = dconn.getConnection().createStatement();               
							
			 rs = stmt.executeQuery("SELECT assign_mode_coll, period_, SUM(resp_successful)/SUM(resp_eligible)*100 AS pct_successful, SUM(resp_usable)/SUM(resp_eligible)*100 AS pct_usable " +
											"FROM response_amc_req " +
											"WHERE response_type = 'F' " +
											"GROUP BY assign_mode_coll, period_ " +
											"UNION " +
											"SELECT 'I+W' as assign_mode_coll, period_,  SUM(resp_successful)/SUM(resp_eligible)*100 AS pct_successful, SUM(resp_usable)/SUM(resp_eligible)*100 AS pct_usable " +
											"FROM response_amc_req " +
											"WHERE response_type = 'F' " +
											"AND assign_mode_coll IN ('I', 'W') " +
											"GROUP BY period_ " +
											"UNION  " +
											"SELECT 'M+N+T' as assign_mode_coll, period_,  SUM(resp_successful)/SUM(resp_eligible)*100 AS pct_successful, SUM(resp_usable)/SUM(resp_eligible)*100 AS pct_usable " +
											"FROM response_amc_req " +
											"WHERE response_type = 'F' " +
											"AND assign_mode_coll IN ('M', 'N', 'T') " +
											"GROUP BY period_ " +
											"UNION " +
											"SELECT 'I+W+M+N+T' as assign_mode_coll, period_,  SUM(resp_successful)/SUM(resp_eligible)*100 AS pct_successful, SUM(resp_usable)/SUM(resp_eligible)*100 AS pct_usable " +
											"FROM response_amc_req " +
											"WHERE response_type = 'F' " +
											"AND assign_mode_coll IN ('I', 'W', 'M', 'N', 'T') " +
											"GROUP BY period_ " +
											"ORDER BY assign_mode_coll, period_");
											
			
																
			while(rs.next()){		
      
				data_object.setAssignMode(rs.getString(1));
				data_object.setPeriod( rs.getInt(2) );
				data_object.setPctSuccessful( rs.getDouble(3) );
				data_object.setPctUsable( rs.getDouble(4) );
			
				rtnV.add(data_object);
				data_object = new PercentAssignModeFinalGraphData();
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
