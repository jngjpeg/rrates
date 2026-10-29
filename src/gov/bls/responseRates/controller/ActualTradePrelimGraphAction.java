package gov.bls.responseRates.controller;

import gov.bls.platform.log.*;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import gov.bls.responseRates.dao.ActualTradePrelimGraphDAO;
import gov.bls.responseRates.dao.ActualTradePrelimGraphData;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.json.JSONArray;
import org.json.JSONException;

public class ActualTradePrelimGraphAction extends Action{

    final static ILog logger = LogFactory.getLogger();
    
	public ActionForward execute(ActionMapping mapping, ActionForm form,
                                HttpServletRequest req, HttpServletResponse resp)
                                throws Exception {
    	
        logger.info("Entering.");       	    

      
        ActualTradePrelimGraphDAO dao = new ActualTradePrelimGraphDAO();        
              
        ArrayList<ActualTradePrelimGraphData> data; 
        
        
        try{
        	data  = dao.getActualTradePrelimGraphData();
        }catch(SQLException e){            
            resp.getWriter().println(new JSONException("There was no data for this period."));
            logger.warn("There was no data for this period.");
            return null;      	
        }
        
        JSONArray json_data = new JSONArray();
    
        //These are the data series arrays.
        JSONArray d_ticks = new JSONArray();
        JSONArray d_a_success = new JSONArray();
        JSONArray d_a_usable = new JSONArray();
        JSONArray d_a_eligible = new JSONArray();
        JSONArray d_m_success = new JSONArray();
        JSONArray d_m_usable = new JSONArray();
        JSONArray d_m_eligible = new JSONArray();
        JSONArray d_x_success = new JSONArray();
        JSONArray d_x_usable = new JSONArray();
        JSONArray d_x_eligible = new JSONArray();
                        
        //Format an array of nine graph series
        try{
        	int i = 0;
        	int j = 0;
        	int k = 0;
        	
            for(ActualTradePrelimGraphData n : data){
            	
            	if(n.getAggType().equals("A")){
            		d_a_success.put(new JSONArray().put(i).put(n.getRespSuccessful()));
            		d_a_usable.put(new JSONArray().put(i).put(n.getRespUsable()));
            		d_a_eligible.put(new JSONArray().put(i).put(n.getRespEligible()));
            		
            		if(i%12==0){
            			d_ticks.put(new JSONArray().put(i).put(n.getPeriod()));            			
            		}
                	i=i+1;            		
            	}else if(n.getAggType().equals("M")){
            		d_m_success.put(new JSONArray().put(j).put(n.getRespSuccessful()));
            		d_m_usable.put(new JSONArray().put(j).put(n.getRespUsable()));
            		d_m_eligible.put(new JSONArray().put(j).put(n.getRespEligible()));
                	j=j+1;
            	}else if(n.getAggType().equals("X")){
            		d_x_success.put(new JSONArray().put(k).put(n.getRespSuccessful()));
            		d_x_usable.put(new JSONArray().put(k).put(n.getRespUsable()));
            		d_x_eligible.put(new JSONArray().put(k).put(n.getRespEligible()));            		
                	k=k+1;
            	}       
            	
            }
        }catch(Exception e){   	     
            resp.getWriter().println(new JSONException("There was a Web server error, needing system admin attention."));         
            logger.error("There was a parsing error.");
            return null;    	    	
        }
          
        json_data.put(d_ticks);
        json_data.put(d_a_success);
        json_data.put(d_a_usable);
        json_data.put(d_a_eligible);
        json_data.put(d_m_success);
        json_data.put(d_m_usable);
        json_data.put(d_m_eligible);
        json_data.put(d_x_success);
        json_data.put(d_x_usable);
        json_data.put(d_x_eligible);
        
        resp.setContentType("application/json");
        resp.getWriter().println(json_data);

        logger.info( "Exiting." );
        return null;                  
    }
	
}
