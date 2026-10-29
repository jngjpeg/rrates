package gov.bls.responseRates.controller;

import gov.bls.platform.log.*;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import gov.bls.responseRates.dao.EstimationReportDAO;
import gov.bls.responseRates.dao.EstimationReportData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.json.JSONArray;
import org.json.JSONException;

//import model.domain.EstimationReportData; 


public final class EstimationReportAction extends Action {

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

        Integer p_period = (Integer) req.getAttribute("p_period");
        
        logger.info("p_period is " + p_period);      
        
        EstimationReportDAO dao = new EstimationReportDAO();        
              
        ArrayList<EstimationReportData> data; 
        
        try{
        	data  = dao.getEstimationData( p_period );
        }catch(SQLException e){            
            resp.getWriter().println(new JSONException("There was no data for this period."));
            logger.warn("There was no data for this period.");
            return null;      	
        }
        
        JSONArray json_data = new JSONArray();
        DecimalFormat wrap = new DecimalFormat("#0.00");
        
        try{
            for(EstimationReportData n : data){
            	JSONArray row_data = new JSONArray();
            	row_data.put(n.getDataType());
            	row_data.put(n.getPeriod());
            	row_data.put(wrap.format(n.getUsabilityRatio()));
            	row_data.put(wrap.format(n.getUseableRatio()));
            	json_data.put(row_data);               	
            }
        }catch(Exception e){   	     
            resp.getWriter().println(new JSONException("There was a Web server error, needing system admin attention."));         
            logger.error("There was a parsing error.");
            return null;    	    	
        }
             
        //logger.info("EstimationReportAction data " + json_data.toString());
        
        resp.setContentType("application/json");
        resp.getWriter().println(json_data);

        logger.info( "Exiting." );
        return null;                  
    }
	

}
