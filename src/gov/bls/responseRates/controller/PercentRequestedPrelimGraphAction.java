package gov.bls.responseRates.controller;

import gov.bls.platform.log.*;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import gov.bls.responseRates.dao.PercentRequestedPrelimGraphDAO;
import gov.bls.responseRates.dao.PercentRequestedPrelimGraphData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.json.JSONArray;
import org.json.JSONException;

public class PercentRequestedPrelimGraphAction extends Action{

    final static ILog logger = LogFactory.getLogger();
    
	public ActionForward execute(ActionMapping mapping, ActionForm form,
                                HttpServletRequest req, HttpServletResponse resp)
                                throws Exception {
    	
        logger.info("Entering.");       	    
      
        PercentRequestedPrelimGraphDAO dao = new PercentRequestedPrelimGraphDAO();        
              
        ArrayList<PercentRequestedPrelimGraphData> data; 
        
        
        try{
        	data  = dao.getPercentRequestedPrelimGraphData();
        }catch(SQLException e){            
            resp.getWriter().println(new JSONException("There was no data for this period."));
            logger.warn("There was no data for this period.");
            return null;      	
        }
        
        JSONArray json_data = new JSONArray();
    
        //These are the data series arrays.
        JSONArray d_ticks = new JSONArray();
        JSONArray d_p_pct_success = new JSONArray();
        JSONArray d_p_pct_usable = new JSONArray();
        JSONArray d_y_pct_success = new JSONArray();
        JSONArray d_y_pct_usable = new JSONArray();
           
        DecimalFormat wrap = new DecimalFormat("#0.00");
                
        //Format an array of nine graph series
        try{
        	int i = 0;
        	int j = 0;
        	
            for(PercentRequestedPrelimGraphData n : data){
            	
            	if(n.getRequested().equals("P")){
            		d_p_pct_success.put(new JSONArray().put(i).put(wrap.format(n.getPctSuccessful())));
            		d_p_pct_usable.put(new JSONArray().put(i).put(wrap.format(n.getPctUsable())));
                		
            		if(i%12==0){
            			d_ticks.put(new JSONArray().put(i).put(n.getPeriod()));            			
            		}
                	i=i+1;            		
            	}else if(n.getRequested().equals("Y")){
            		d_y_pct_success.put(new JSONArray().put(j).put(wrap.format(n.getPctSuccessful())));
            		d_y_pct_usable.put(new JSONArray().put(j).put(wrap.format(n.getPctUsable())));
                	
            		j=j+1;                	
            	}       
            	
            }
        }catch(Exception e){   	     
            resp.getWriter().println(new JSONException("There was a Web server error, needing system admin attention."));         
            logger.error("There was a parsing error.");
            return null;    	    	
        }
          
        json_data.put(d_ticks);
        json_data.put(d_p_pct_success);
        json_data.put(d_p_pct_usable);
        json_data.put(d_y_pct_success);
        json_data.put(d_y_pct_usable);
        
        resp.setContentType("application/json");
        resp.getWriter().println(json_data);

        logger.info( "Exiting." );
        return null;                  
    }
	
}
