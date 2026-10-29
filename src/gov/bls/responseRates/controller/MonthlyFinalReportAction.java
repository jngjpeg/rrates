package gov.bls.responseRates.controller;

import gov.bls.platform.log.*;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import gov.bls.responseRates.dao.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import gov.bls.responseRates.dao.MonthlyFinalReportDAO;


public final class MonthlyFinalReportAction extends Action {

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
 
        //Retrieve the values from the request object.
        String p_agg_type = (String) req.getAttribute("p_agg_type");
        
        logger.info("p_agg_type = " + p_agg_type);   
        
        MonthlyFinalReportDAO dao = new MonthlyFinalReportDAO();                      
        ArrayList<MonthlyFinalReportData> data; 
   
        try{
        	data  = dao.getMonthlyFinalReportData( p_agg_type );
        }catch(SQLException e){            
            resp.getWriter().println(new JSONException("There was no data."));
            return null;      	
        }
        
        JSONArray json_data = new JSONArray();
        DecimalFormat wrap = new DecimalFormat("#0.00");
        
        try{
        	for(MonthlyFinalReportData n : data){
        		JSONArray row_data = new JSONArray();
        	
        		row_data.put(n.getPeriod());
        		row_data.put(n.getRespSuccessful());
        		row_data.put(n.getRespEligible());
        		row_data.put(n.getRespUsable());
        		
           		if( n.getPctSuccessful()==999.99 ){
        			row_data.put("n.a.");
        		}else{
        			row_data.put(wrap.format( n.getPctSuccessful() ));
        		}

        		if( n.getPctUsable()==999.99 ){
        			row_data.put("n.a.");
        		}else{
        			row_data.put(wrap.format( n.getPctUsable() ));
        		}
        		
        		if( n.getStPctSuccessful()==999.99 ){
        			row_data.put("n.a.");
        		}else{
        			row_data.put(wrap.format( n.getStPctSuccessful() ));
        		}
        		
        		if( n.getLtPctSuccessful()==999.99 ){
        			row_data.put("n.a.");
        		}else{
        			row_data.put(wrap.format( n.getLtPctSuccessful() ));
        		}
        		
        		if( n.getStPctUsable()==999.99 ){
        			row_data.put("n.a.");
        		}else{
        			row_data.put(wrap.format( n.getStPctUsable() ));
        		}
        	
        		if( n.getLtPctUsable()==999.99 ){
        			row_data.put("n.a.");
        		}else{
        			row_data.put(wrap.format( n.getLtPctUsable() ));
        		}
        		
        		if( n.getBlsPriceQuotes()==999.99 ){
        			row_data.put("n.a.");
        		}else{
        			row_data.put(wrap.format(n.getBlsPriceQuotes()));
        		}
        		
        		if( n.getBlsEstimationResp()==999.99 ){
        			row_data.put("n.a.");
        		}else{
        			row_data.put(wrap.format(n.getBlsEstimationResp()));
        		}
        		
        		json_data.put(row_data);             	
        	}
        }catch(Exception e){   	     
        	resp.getWriter().println(new JSONException("There was a Web server error, needing system admin attention."));         
        	logger.error("Parsing error "+e.getMessage());
        	return null;    	    	
        }
             
        resp.setContentType("application/json");
        resp.getWriter().println(json_data);
                    
        logger.info( "Exiting." );       
        return null;
                
    }
	

}
