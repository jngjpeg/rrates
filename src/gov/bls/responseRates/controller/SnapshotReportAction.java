package gov.bls.responseRates.controller;

import java.sql.SQLException;

import gov.bls.platform.log.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.bls.responseRates.dao.SnapshotReportDAO;
import gov.bls.responseRates.dao.SnapshotReportData;

import org.json.JSONArray;
import org.json.JSONException;



public final class SnapshotReportAction extends Action {

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
        
        //Production Code
        //Retrieve the values from the request object.      
                
        Integer p_period = (Integer) req.getAttribute("p_period");
        java.sql.Date p_date_time = (java.sql.Date) req.getAttribute("p_date_time");
        
        logger.info("p_period = " + p_period + ", p_date_time = " + p_date_time);
        
        SnapshotReportDAO dao = new SnapshotReportDAO();     
        
        //This contains the response, failure or success.
        JSONArray json_data = new JSONArray();
        
    	resp.setContentType("application/json");
     
        java.sql.Date mail_out_date;
        
        try{
        	mail_out_date = dao.getMailoutDate( p_period );     
        	logger.info("mail_out_date"+mail_out_date);
        }catch(SQLException e){	       	      
            resp.getWriter().println(new JSONException("There is no mailout date in table: EST_SCHEDULE for this period.").getMessage());
        	return null;
        }catch(Exception e){
//        	resp.getWriter().println(new JSONException("These is Oracle exception, the query for get mail out date did not work.").getMessage());
        	resp.getWriter().println(new JSONException(e.getMessage()));
            System.err.println("Exception:"+e);
        	return null;
        }

        Integer work_days;

        try{
        	work_days = dao.getWorkDays( mail_out_date, p_date_time );
        }catch(SQLException e){	       	     
            resp.getWriter().println(new JSONException("There is was an exception in Oracle function: F_COUNT_WORKDAYS."));
            return null;
        }catch(Exception e){	       	     
            resp.getWriter().println(new JSONException("There is was an exception in Oracle: either F_COUNT_WORKDAYS is not in the database or you do not have access to it."));
            return null;
        }
              
        ArrayList<SnapshotReportData> data;
        
        logger.info("SnapshotReportAction: dao.getSnapshotData");  
        try{
        	data = dao.getSnapshotData( p_period, p_date_time  );
        }catch(SQLException e){            
        	logger.error(e.getMessage());
            resp.getWriter().println(new JSONException("Stored procedure: RESP_RATE_SNAPSHOT did not return data for these arguments."));
            return null;       	
        }catch(Exception e){            
            resp.getWriter().println(new JSONException("Either you do not have access to stored procedure: RESP_RATE_SNAPSHOT or it is not in the database ."));
            return null;       	
        }
        
        //Format the response.
        
        //Format to a date like the format on the html forms.
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-d hh:mm:ssa");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-d");
        String mail_out_date2 = sdf.format(new java.util.Date( mail_out_date.getTime() ));
        
        JSONArray report_parms = new JSONArray();
        report_parms.put(work_days);
        report_parms.put(mail_out_date2);

        DecimalFormat wrap = new DecimalFormat("#0.00");

        try{
            for(SnapshotReportData n : data){
            	
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
        	logger.error("parsing exception " + e.getMessage());           
            return null;    	    	
        }
        
        //Send formatted data.
        json_data.put(report_parms);                       
        resp.getWriter().println(json_data);
      
        logger.info( "Exiting." );
        
        return null;
    }   
}
