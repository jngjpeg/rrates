package gov.bls.responseRates.dao;

import java.util.ArrayList;

import oracle.jdbc.OracleTypes;

import gov.bls.platform.log.*;
import gov.bls.responseRates.service.dbAccess.DbConnection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EstimationReportDAO {
	
    final static ILog logger = LogFactory.getLogger();
		
	public ArrayList<EstimationReportData> getEstimationData( Integer period ) throws SQLException{
	
		DbConnection dconn = DbConnection.getInstance(); 
		
		ArrayList<EstimationReportData> rtnV = new ArrayList<EstimationReportData>();		
		EstimationReportData data_object = new EstimationReportData();
		CallableStatement cs = null;
		
		try {    
			//CallableStatement cs = dconn.getConnection().prepareCall("{call RESP_RATE_ESTIMATION(?)}");               
			 cs = dconn.getConnection().prepareCall("{call RESP_RATE_ESTIMATION(?,?)}");               

			cs.setInt(1, period);
			//ResultSet rs = cs.executeQuery();               
			
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			
			cs.execute();	
			ResultSet rs = (ResultSet) cs.getObject(2);
			
			while(rs.next()){		
               
				data_object.setDataType( rs.getString(1) );
				data_object.setPeriod( rs.getInt(2) );
				data_object.setUsabilityRatio( rs.getDouble(3) );
				data_object.setUseableRatio( rs.getDouble(4) );
				
				rtnV.add(data_object);
				data_object = new EstimationReportData();
			}
			
        }catch (SQLException e) {
             logger.error("Processing of stored procedure: " + e.getMessage());
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
        return rtnV;        
	}    
        
}
