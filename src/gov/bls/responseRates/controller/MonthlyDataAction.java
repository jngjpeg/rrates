/*
 * MonthlyDataAction.java
 *
 * Created on September 18, 2007, 3:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.bls.responseRates.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.bls.platform.log.*;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import gov.bls.responseRates.dao.MonthlyDataDAO;
import gov.bls.responseRates.dao.MonthlyData;
import gov.bls.responseRates.dao.SnapshotReportDAO;

public final class MonthlyDataAction extends Action {

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
                                HttpServletRequest request, HttpServletResponse response)
                                throws Exception {
      
        logger.info("Entering.");       	    
                
        //Production Code
        //Retrieve the values from the request object.      
                
        Integer p_period = (Integer) request.getAttribute("p_period");
        java.sql.Date p_date_time = (java.sql.Date) request.getAttribute("p_date_time");       
        String p_prelim_final = (String) request.getAttribute("p_prelim_final");

        logger.info("p_period = " + p_period + ", p_date_time = " + p_date_time + ", p_prelim_final = " + p_prelim_final);
        
        MonthlyDataDAO dao = new MonthlyDataDAO(); 
        ArrayList<MonthlyData> data;
        
        try{
        	data = dao.runMonthlyData( p_prelim_final, p_period, p_date_time );
        }catch(SQLException e){	       	      
            response.getWriter().println(new JSONException("The stored procedure: RESP_RATE_MONTHLY failed.  The Oracle message is " + e.getMessage()).getMessage());
        	return null;
        }
          
        //This contains the response, failure or success.
        JSONArray json_data = new JSONArray();        
        json_data.put( data.get(0).getFeedback() );
        
        response.getWriter().println( json_data );
                
        logger.info( "Exiting." );
             
        return null;    
    }
}
