package gov.bls.ips.shared.email.components.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import gov.bls.ips.shared.dbcon.components.util.DBConnection;
import gov.bls.ips.shared.dbcon.components.util.DBConnectionFactory;
import gov.bls.ips.shared.email.components.util.constants.EmailConstantsIF;
import gov.bls.ips.shared.email.components.valueobjects.*;

public class EmailDAO {

	private static EmailDAO obj = null;
    private static Object monitor = new Object();
    private String dataSourceName; 

    private EmailDAO(){}

    public static EmailDAO getObject(String dataSourceName){
        if(obj == null){
            synchronized(monitor){
                if(obj == null){
                    obj = new EmailDAO();
                    obj.dataSourceName = dataSourceName;
                }
            }
        }
        return obj;
    }
    
    public String getDataSourceName() {
    	return dataSourceName;
    }
    
    public EmailDetailsVO getEmailDetails(String subsystemCode, String featureCode) throws Exception {
    		
    		DBConnection cObj = null;
    		PreparedStatement pstmt = null;		
    		ResultSet rs = null;
    		StringBuffer sqlBuf = new StringBuffer();
    		
    		EmailDetailsVO emailDetailsVO = null;
    		
    		try {
    			
    			cObj = DBConnectionFactory.getConnection(getDataSourceName());
    			sqlBuf.append("select a.EMAIL_GROUP_CODE, a.EMAIL_SENDER, a.SUBJECT_TEXT, a.BODY_TEXT, ");
    			sqlBuf.append("a.CONTENT_TYPE ");
    			sqlBuf.append("from EMAIL_DETAILS a ");
    			sqlBuf.append("where a.SUBSYSTEM_CODE = '" + subsystemCode + "' ");
    			sqlBuf.append("and a.FEATURE_CODE = '" + featureCode + "' ");
    			//sqlBuf.append("and a.EMAIL_GROUP_CODE = b.EMAIL_GROUP_CODE");
    			
    			String strSql = sqlBuf.toString();
    			
    			pstmt = cObj.createPreparedStatement(strSql);
    			rs = cObj.executeQuery(pstmt);
    			emailDetailsVO = new EmailDetailsVO();
    			
    			while (rs.next()) {
    				if (rs.getString("EMAIL_GROUP_CODE") != null) {
    					emailDetailsVO.setRecipientGroupCode(rs.getString("EMAIL_GROUP_CODE"));
    				}
    				if (rs.getString("EMAIL_SENDER") != null) {
    					emailDetailsVO.setSenderEmailAddress(rs.getString("EMAIL_SENDER"));
    				}
    				if (rs.getString("SUBJECT_TEXT") != null) {
    					emailDetailsVO.setSubject(rs.getString("SUBJECT_TEXT"));
    				}
    				if (rs.getClob("BODY_TEXT") != null) {
    					emailDetailsVO.setBodyText(rs.getClob("BODY_TEXT").toString());
    				}
    				if (rs.getString("CONTENT_TYPE") != null) {
    					emailDetailsVO.setContentType(rs.getString("CONTENT_TYPE"));
    				}
    			}
    			pstmt.close();
    			
    			if (emailDetailsVO.getRecipientGroupCode()!=null) {
    				sqlBuf = new StringBuffer();
    				sqlBuf.append("select USER_EMAIL_ADDR from EMAIL_USER_GROUP ");        			
        			sqlBuf.append("where EMAIL_GROUP_CODE = '" + emailDetailsVO.getRecipientGroupCode() + "' ");
        			
        			strSql = sqlBuf.toString();
        			
        			pstmt = cObj.createPreparedStatement(strSql);
        			rs = cObj.executeQuery(pstmt);
        			
        			StringBuffer recipientUserEmailBuf = new StringBuffer();
        			
        			while (rs.next()) {
        				if (rs.getString("USER_EMAIL_ADDR") != null) {
        					if (recipientUserEmailBuf.length()>0) {
        						recipientUserEmailBuf.append(",");
        					}
        					recipientUserEmailBuf.append(rs.getString("USER_EMAIL_ADDR"));
        				}
        			}
        			
        			if (recipientUserEmailBuf.length()>0) {
        				emailDetailsVO.setRecipientEmailAddresses(recipientUserEmailBuf.toString());
        			}
    			}
    			
    		 }/* catch(SQLException se){
    	 		 throw new ApplicationException(se.getMessage(), se);
    		 } catch (Exception ex){
    			 throw new ApplicationException(ex.getMessage(), ex);
    			 }*/ finally { 		 
    				 closeResources(rs, pstmt, cObj);
    			 }
    			 
    		return emailDetailsVO;
    	}	
    
    public boolean canSendEmail() throws Exception {	  

		  boolean canSendEmail = false;
		  DBConnection cObj = null;
  		  PreparedStatement pstmt = null;		
  		  ResultSet rs = null;
  		  
	      try {
	    	 cObj = DBConnectionFactory.getConnection(getDataSourceName());    
	         String sqlStr = "select count(*) from ips_udb_ic.PRODUCTION_PARAM where param_name='EMAIL_YN' and param_value='TRUE'"; 
	         	         
	         pstmt = cObj.createPreparedStatement(sqlStr);
 			 rs = cObj.executeQuery(pstmt);

	         int count=0;
	         while (rs.next()) {
	        	 count = rs.getInt(1);
	         }

	         if (count>0) {
	        	 canSendEmail=true;
	         }
	      }
	      finally {

	        try {
	          rs.close();
	          pstmt.close();
	          cObj.close();
	        } catch (Exception e) {

	        }
	      }	      
		  return canSendEmail;
	  }
	
    
    public Properties getCommonEmailProps() throws Exception {
		
		DBConnection cObj = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer sqlBuf = new StringBuffer();
		
		Properties emailProperties = null;
		
		try {
			
			cObj = DBConnectionFactory.getConnection(dataSourceName);
			sqlBuf.append("select param_name, param_value ");			
			sqlBuf.append("from ips_udb_ic.PRODUCTION_PARAM ");
			sqlBuf.append("where param_name in ('" + EmailConstantsIF.EMAIL_PROTOCOL_PARAM + "', '");
			sqlBuf.append(EmailConstantsIF.EMAIL_HOST + "')");
			
			String strSql = sqlBuf.toString();
			
			pstmt = cObj.createPreparedStatement(strSql);
			rs = cObj.executeQuery(pstmt);
			emailProperties = new Properties();
			
			while (rs.next()) {
				emailProperties.put(rs.getString(1), rs.getString(2));			
			}
			
			
		 }/* catch(SQLException se){
	 		 throw new ApplicationException(se.getMessage(), se);
		 } catch (Exception ex){
			 throw new ApplicationException(ex.getMessage(), ex);
			 }*/ finally { 		 
				 closeResources(rs, pstmt, cObj);
			 }
			 
		return emailProperties;
	}	 
    
	    private static void closeResources(ResultSet rs, Statement stmt, DBConnection conn) {	
		      
	    	if (rs != null) {
	          try {
	              rs.close();
	          } catch (SQLException e) { 
	          	e.printStackTrace(); 
	          }
	    	}
	     
		    if (stmt != null) {
	          try {
	              stmt.close();
	          } catch (SQLException e) { 
	          	e.printStackTrace(); 
	          }
		    }
	     
		    if (conn != null) {             
	           conn.close();
		    }         
	     
		}
}
