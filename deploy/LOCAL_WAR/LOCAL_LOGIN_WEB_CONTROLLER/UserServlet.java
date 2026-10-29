package gov.bls.responseRates.utility.user;


import org.json.JSONArray;
import org.json.JSONException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

import gov.bls.responseRates.service.dbAccess.DbConnection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    final static Logger logger = Logger.getLogger(UserServlet.class);
    
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
    	
    	//HttpSession session=req.getSession(true);

		 //String lanName =(String) request.getParameter("lanName");

		/** set session attribute LOGINNAME */
    	//Comment from here to end comment for local testing
	  
    	String lanName ="Schneider_Aric";
        
    	
/*    	for (Enumeration e=req.getHeaderNames(); e.hasMoreElements();) {
    	    String headerName = (String)e.nextElement();
    	    logger.info("Name = " + headerName);
    	    logger.info("Value = " + req.getHeader(headerName));
    	}

    	logger.info("The remote user is: " + req.getRemoteUser());
    	
    	//There is no "proxy-remote-user"
 */     	
    	if ( req.getHeader("proxy-remote-user") != null) {
	        //lanName = request.getRemoteUser();
	        lanName=req.getHeader("proxy-remote-user");
	        System.err.println("proxy-remote-user: " + req.getHeader("proxy-remote-user"));  
	        String domain = "";
	        String loginName = "";
	        //lanName format : DIP\sathiya_v  ie., Domain \ user login name
	        StringTokenizer st = new StringTokenizer(lanName, "\\");
	        try {
	          domain = st.nextToken();
	          loginName = st.nextToken();
	        }
	        //if the domain name is not available.
	        catch (NoSuchElementException nse) {
	          logger.info("NoSuchElementException: " + nse);
	          loginName = req.getRemoteUser();
	        }
	       lanName= loginName;
	       
	    }
    	
    	lanName="Schneider_Aric";
    	String last_name = "Schneider";
    	String first_name = "Aric";
    	
		DbConnection dconn = DbConnection.getInstance();  		
		String sql =  "SELECT user_first_name, user_last_name FROM udb_user WHERE UPPER(lan_name) = UPPER('" + lanName + "')";

		Statement stmt = null;
		ResultSet rs = null;		
		try {    
			 stmt = dconn.getConnection().createStatement();               
			 rs = stmt.executeQuery( sql );             
			
			while(rs.next()){		      
					first_name = rs.getString(1).trim();
					last_name = rs.getString(2).trim();
					logger.info("First name is " + first_name);
					logger.info("Last name is " + last_name);					
			}
			
        }catch (SQLException e) {
             logger.error(e.getMessage());             
         }
		finally{
	        //closes all the database resources.
	        try{
	          if(rs!=null) rs.close();
	          if(stmt!=null) stmt.close();
	          
	        }catch(SQLException sqle){
	          System.err.println("INSIDE FINALLY CLOSING: "+sqle);
	        }
	}//end finally
			    	
        String sql2 = "";
        Boolean is_admin = null;
        
		try {    
			 stmt = dconn.getConnection().createStatement();               
			
			sql2 = "SELECT COUNT(*) from worker " + 
			"WHERE worker_status = 'Y'" +
			"AND org_unit_id IN (10,11,14,34,35)" +
			"AND user_code IN" +
			"(SELECT user_code FROM udb_user WHERE UPPER(lan_name) = UPPER('" + lanName +"'))";
    	
			 rs = stmt.executeQuery( sql2 );             
		
			while(rs.next()){		      
				Integer temp = rs.getInt(1);
			
				if(temp>=1){
					is_admin = true;
				}else{
					is_admin = false;
				}
				
				logger.info("Is admin " + is_admin);					
			}
		
    }catch (SQLException e) {
         logger.error(e.getMessage());             
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
			    	
        JSONArray json_data = new JSONArray();

//        json_data.put("Sumangala");
//        json_data.put("Bhandari");
//        json_data.put("true");
//        json_data.put("Bhandari_S");
        
        
        
        
        //json_data.put(lanName);
        json_data.put(first_name);
        json_data.put(last_name);
        json_data.put(is_admin);
        json_data.put(lanName);
   
        System.err.println("UserServlet 147 json_data.toString   " + json_data.toString());
        logger.info("UserServlet 148 json_data.toString  " + json_data.toString());
        
        res.setContentType("application/json");
        try{
        	res.getWriter().println(json_data);
        }catch(Exception e){
        	logger.error(new JSONException("There was a Web server error, needing system admin attention: " + e));                	
        }
    	
     }
     

}
