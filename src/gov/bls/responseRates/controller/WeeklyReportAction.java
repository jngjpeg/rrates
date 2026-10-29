package gov.bls.responseRates.controller;

import gov.bls.responseRates.dao.WeeklyReportDAO;
import gov.bls.responseRates.dao.SnapshotReportData;

import gov.bls.platform.log.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONException;


public final class WeeklyReportAction extends Action {

    final static ILog logger = LogFactory.getLogger();
 
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @return Action to forward to
     * @exception Exception if an input/output error or servlet exception occurs
     */

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                HttpServletRequest req, HttpServletResponse resp)
                                throws Exception {
       
    	logger.info("Entering.");  
    	logger.info("Entering2."); 
            	
        //Production Code
        //Retrieve the values from the request object.      
                
        Integer p_period = (Integer) req.getAttribute("p_period");
        logger.info("p_period " + p_period);
        
        java.sql.Date p_date_time = (java.sql.Date) req.getAttribute("p_date_time");
        logger.info("p_date_time " + p_date_time.toString());
        
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MMMM-d kk:mm ");
        String temp = sdf1.format(new java.util.Date( p_date_time.getTime() ));
        logger.info("p_date_time with the hour is" +temp);
        
        
        
        logger.info("p_period = " + p_period + ", p_date_time = " + p_date_time);
        
        WeeklyReportDAO dao = new WeeklyReportDAO();     
        
        //This contains the response, failure or success.
        JSONArray json_data = new JSONArray();
        
    	resp.setContentType("application/json");

    	HashMap<Object, Object> data1; 
    	
        try{
        	data1  = dao.getParms( p_period, p_date_time  );
    	}catch(SQLException e){	       	      
    		resp.getWriter().println(new JSONException("There is was an exception in Oracle stored procedure: RESP_WEEKLY_PARMS.").getMessage());
    		return null;
    	}

        ArrayList<SnapshotReportData> data2;
        
        try{
        	logger.info("Debug begin");
        	
        	data2 = dao.getSnapshotData( p_period, p_date_time  );
        	
        	//Debug code
        	for(int i=0; i<data2.size();i++){
        		logger.info("Mode of Collection is " + data2.get(i).getModeOfCollection());
        		logger.info("Requested is " + data2.get(i).getRequested());
        	}
        	
        }catch(SQLException e){            
            resp.getWriter().println(new JSONException("There was no current data for these arguments."));
            return null;       	
        }
                
        Integer p_prev_period = new Integer( data1.get("p_prev_period").toString() );
        java.sql.Date p_prev_date_time = new java.sql.Date ( ((Long)data1.get("p_prev_date_time")).longValue() );
              
        ArrayList<SnapshotReportData> data3;  
        
        try{
        	data3 = dao.getSnapshotData( p_prev_period, p_prev_date_time ) ;
        }catch(SQLException e){            
        	resp.getWriter().println(new JSONException("There was no previous data for these arguments."));
        	return null;       	
        }

        Integer p_num_days = new Integer( data1.get("p_num_days").toString() );
                     
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMMM-d");
        java.sql.Date p_mail_out = new java.sql.Date ( ((Long)data1.get("p_mail_out")).longValue() );
        String p_mail_out_date2 = sdf.format(new java.util.Date( p_mail_out.getTime() ));

        java.sql.Date p_prev_mail_out = new java.sql.Date ( ((Long)data1.get("p_prev_mail_out")).longValue() );
        String p_prev_mail_out_date2 = sdf.format(new java.util.Date( p_prev_mail_out.getTime() ));

        String p_prev_date_time2 = sdf.format(new java.util.Date( p_prev_date_time.getTime() ));
             
        sdf = new SimpleDateFormat("yyyyMM");
        java.util.Date p_prev_period2 = sdf.parse(p_prev_period.toString());
        
        sdf = new SimpleDateFormat("yyyy-MMMM");
        String p_prev_period3 = sdf.format(p_prev_period2);
        
        JSONArray report_parms = new JSONArray();

        report_parms.put(p_mail_out_date2);
        report_parms.put(p_num_days);
        report_parms.put(p_prev_mail_out_date2);
        report_parms.put(p_prev_date_time2);
        report_parms.put(p_prev_period3);  
        //New code to fix bug
        report_parms.put(data2.size());
        report_parms.put(data3.size());
        
        DecimalFormat wrap = new DecimalFormat("#0.00");
       
        
        try{
            for(SnapshotReportData n : data2){
            	
            	JSONArray row_data = new JSONArray();
            	
            	row_data.put(n.getModeOfCollection());
            	row_data.put(n.getRequested());
            	row_data.put(n.getSuccessful());
            	row_data.put(wrap.format(n.getPctSuccessful()));
            	row_data.put(n.getUsable());
            	row_data.put(wrap.format(n.getPctUsable()));
            	row_data.put(n.getEligible());
            	
            	json_data.put(row_data);               	
            }
            
            for(SnapshotReportData n : data3){
            	
            	JSONArray row_data = new JSONArray();
            	
            	row_data.put(n.getModeOfCollection());
            	row_data.put(n.getRequested());
            	row_data.put(n.getSuccessful());
            	row_data.put(wrap.format(n.getPctSuccessful()));
            	row_data.put(n.getUsable());
            	row_data.put(wrap.format(n.getPctUsable()));
            	row_data.put(n.getEligible());
            	
            	json_data.put(row_data);               	
            }
            
        }catch(Exception e){   	     
            resp.getWriter().println(new JSONException("There was a Web server error, needing system admin attention."));         
            logger.error("parsing error"+e.getMessage());
            return null;    	    	
        }
        
        //Send formatted data.
        json_data.put(report_parms);                       
        resp.getWriter().println(json_data);
        
        logger.info( "Exiting." );
        return null;
    }
}