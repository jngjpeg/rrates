package gov.bls.responseRates.controller;

import gov.bls.platform.log.*;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import gov.bls.responseRates.dao.ActualRequestedPrelimGraphDAO;
import gov.bls.responseRates.dao.ActualRequestedPrelimGraphData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.json.JSONArray;
import org.json.JSONException;

public class ActualRequestedPrelimGraphAction extends Action{

    final static ILog logger = LogFactory.getLogger();
    
	public ActionForward execute(ActionMapping mapping, ActionForm form,
                                HttpServletRequest req, HttpServletResponse resp)
                                throws Exception {
    	
        logger.info("Entering.");       	    

      
        ActualRequestedPrelimGraphDAO dao = new ActualRequestedPrelimGraphDAO();        
              
        ArrayList<ActualRequestedPrelimGraphData> data; 
        
        
        try{
        	data  = dao.getActualRequestedPrelimGraphData();
        }catch(SQLException e){            
            resp.getWriter().println(new JSONException("There was no data for this period."));
            logger.warn("There was no data for this period.");
            return null;      	
        }
        
        JSONArray json_data = new JSONArray();
    
        //These are the data series arrays.
        JSONArray d_ticks = new JSONArray();
        JSONArray d_p_success = new JSONArray();
        JSONArray d_p_usable = new JSONArray();
        JSONArray d_p_eligible = new JSONArray();
        JSONArray d_y_success = new JSONArray();
        JSONArray d_y_usable = new JSONArray();
        JSONArray d_y_eligible = new JSONArray();
                        
        //Format an array of nine graph series
        try{
        	int i = 0;
        	int j = 0;
        	
            for(ActualRequestedPrelimGraphData n : data){
            	
            	if(n.getRequested().equals("P")){
            		d_p_success.put(new JSONArray().put(i).put(n.getRespSuccessful()));
            		d_p_usable.put(new JSONArray().put(i).put(n.getRespUsable()));
            		d_p_eligible.put(new JSONArray().put(i).put(n.getRespEligible()));
            		
            		if(i%12==0){
            			d_ticks.put(new JSONArray().put(i).put(n.getPeriod()));            			
            		}
                	i=i+1;            		
            	}else if(n.getRequested().equals("Y")){
            		d_y_success.put(new JSONArray().put(j).put(n.getRespSuccessful()));
            		d_y_usable.put(new JSONArray().put(j).put(n.getRespUsable()));
            		d_y_eligible.put(new JSONArray().put(j).put(n.getRespEligible()));
                	j=j+1;
            	}       
            	
            }
        }catch(Exception e){   	     
            resp.getWriter().println(new JSONException("There was a Web server error, needing system admin attention."));         
            logger.error("There was a parsing error.");
            return null;    	    	
        }
          
        json_data.put(d_ticks);
        json_data.put(d_p_success);
        json_data.put(d_p_usable);
        json_data.put(d_p_eligible);
        json_data.put(d_y_success);
        json_data.put(d_y_usable);
        json_data.put(d_y_eligible);
        
        resp.setContentType("application/json");
        resp.getWriter().println(json_data);

        logger.info( "Exiting." );
        return null;                  
    }
	
}
