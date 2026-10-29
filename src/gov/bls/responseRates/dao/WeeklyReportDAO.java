package gov.bls.responseRates.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import oracle.jdbc.OracleTypes;

import gov.bls.platform.log.*;
import gov.bls.responseRates.service.dbAccess.DbConnection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;


public class WeeklyReportDAO {
	
    final static ILog logger = LogFactory.getLogger();
	
    public HashMap<Object, Object> getParms( Integer p_period, java.sql.Date p_date_time ) throws SQLException{
    	
		DbConnection dconn = DbConnection.getInstance();    
		HashMap<Object, Object>  rtnV = new HashMap<Object, Object>();
		
		CallableStatement	cs = null;	
        try {    
             cs = dconn.getConnection().prepareCall("{call RESP_WEEKLY_PARMS(?,?,?,?,?,?,?)}");   
            
            cs.setInt(1, p_period);//IN
            cs.setDate(2, p_date_time);//IN
            
            //register the out parms
            cs.registerOutParameter(3,java.sql.Types.TIMESTAMP);
            cs.registerOutParameter(4,java.sql.Types.INTEGER);
            cs.registerOutParameter(5,java.sql.Types.TIMESTAMP);
            cs.registerOutParameter(6,java.sql.Types.INTEGER);
            cs.registerOutParameter(7,java.sql.Types.TIMESTAMP);
            
            cs.execute();
         
            //get the out parms
            rtnV.put("p_mail_out", cs.getDate(3).getTime());
            rtnV.put("p_num_days", cs.getInt(4));
            rtnV.put("p_prev_mail_out", cs.getDate(5).getTime() );
            rtnV.put("p_prev_period", cs.getInt(6) );
            rtnV.put("p_prev_date_time", cs.getDate(7).getTime() );
                          
        }catch (SQLException e) {  
             logger.error(e.getMessage());
             throw e;
        }
	     finally{
		        //closes all the database resources.
		        try{
		          if(cs!=null) cs.close();
	              if(dconn!=null)dconn.closeConnection();
		        }catch(SQLException sqle){
		          System.err.println("INSIDE FINALLY CLOSING:WeeklyReportDAO "+sqle);
		        }
		  }//end finally
               
        return rtnV;          	
    }
    
	public ArrayList<SnapshotReportData> getSnapshotData( Integer p_period, java.sql.Date p_date_time  ) throws SQLException{
		
		DbConnection dconn = DbConnection.getInstance();    
		ArrayList<SnapshotReportData> rtnV = new ArrayList<SnapshotReportData>();
		
		SnapshotReportData data_object = new SnapshotReportData();
		CallableStatement cs = null;	
		try {    
			//CallableStatement cs = dconn.getConnection().prepareCall("{call RESP_RATE_SNAPSHOT(?, ?)}");
			 cs = dconn.getConnection().prepareCall("{call RESP_RATE_SNAPSHOT(?, ?, ?)}");
			cs.setInt(1, p_period);
			cs.setDate(2, p_date_time);	
			//ResultSet rs = cs.executeQuery();               
			
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			
			cs.execute();	
			ResultSet rs = (ResultSet) cs.getObject(3);
			
			
			while(rs.next()){		

				System.out.println("Hello Chris");
				data_object.setModeOfCollection( rs.getString(2) );
				logger.info(rs.getString(2));
				data_object.setRequested( rs.getString(3) );
				logger.info(rs.getString(3));
				data_object.setSuccessful( rs.getInt(4) );
				data_object.setPctSuccessful( rs.getDouble(5) );
				data_object.setUsable( rs.getInt(6) );
				data_object.setPctUsable( rs.getDouble(7) );
				data_object.setEligible(rs.getInt(8));
								
				rtnV.add(data_object);
				
				data_object = new SnapshotReportData();
			}
			
        }catch (SQLException e) {
             logger.error(e.getMessage());
             throw e;
         }
	     finally{
		        //closes all the database resources.
		        try{
		          if(cs!=null) cs.close();
	              if(dconn!=null)dconn.closeConnection();
		        }catch(SQLException sqle){
		          System.err.println("INSIDE FINALLY CLOSING:WeeklyReportDAO "+sqle);
		        }
		  }//end finally
		

        return rtnV;        
	}    
        
}
