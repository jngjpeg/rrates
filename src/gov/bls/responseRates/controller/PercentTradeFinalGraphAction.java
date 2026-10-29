package gov.bls.responseRates.controller;

import gov.bls.platform.log.*;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import gov.bls.responseRates.dao.PercentTradeFinalGraphDAO;
import gov.bls.responseRates.dao.PercentTradeFinalGraphData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.json.JSONArray;
import org.json.JSONException;

public class PercentTradeFinalGraphAction extends Action{

    final static ILog logger = LogFactory.getLogger();
    
	public ActionForward execute(ActionMapping mapping, ActionForm form,
                                HttpServletRequest req, HttpServletResponse resp)
                                throws Exception {
    	
        logger.info("Entering.");       	    
      
        PercentTradeFinalGraphDAO dao = new PercentTradeFinalGraphDAO();        
              
        ArrayList<PercentTradeFinalGraphData> data; 
        
        
        try{
        	data  = dao.getPercentTradeFinalGraphData();
        }catch(SQLException e){            
            resp.getWriter().println(new JSONException("There was no data for this period."));
            logger.warn("There was no data for this period.");
            return null;      	
        }
        
        JSONArray json_data = new JSONArray();
    
        //These are the data series arrays.
        JSONArray d_ticks = new JSONArray();
        JSONArray d_a_pct_success = new JSONArray();
        JSONArray d_a_pct_usable = new JSONArray();
        JSONArray d_m_pct_success = new JSONArray();
        JSONArray d_m_pct_usable = new JSONArray();
        JSONArray d_x_pct_success = new JSONArray();
        JSONArray d_x_pct_usable = new JSONArray();
                        
        DecimalFormat wrap = new DecimalFormat("#0.00");
                
        //Format an array of nine graph series
        try{
        	int i = 0;
        	int j = 0;
        	int k = 0;
        	
            for(PercentTradeFinalGraphData n : data){
            	
            	if(n.getAggType().equals("A")){
            		d_a_pct_success.put(new JSONArray().put(i).put(wrap.format(n.getPctSuccessful())));
            		d_a_pct_usable.put(new JSONArray().put(i).put(wrap.format(n.getPctUsable())));
            		
            		if(i%12==0){
            			d_ticks.put(new JSONArray().put(i).put(n.getPeriod()));            			
            		}
                	i=i+1;            		
            	}else if(n.getAggType().equals("M")){
            		d_m_pct_success.put(new JSONArray().put(i).put(wrap.format(n.getPctSuccessful())));
            		d_m_pct_usable.put(new JSONArray().put(i).put(wrap.format(n.getPctUsable())));
                	j=j+1;
            	}else if(n.getAggType().equals("X")){
            		d_x_pct_success.put(new JSONArray().put(i).put(wrap.format(n.getPctSuccessful())));
            		d_x_pct_usable.put(new JSONArray().put(i).put(wrap.format(n.getPctUsable())));
                	k=k+1;
            	}       
            	
            }
        }catch(Exception e){   	     
            resp.getWriter().println(new JSONException("There was a Web server error, needing system admin attention."));         
            logger.error("There was a parsing error.");
            return null;    	    	
        }
          
        json_data.put(d_ticks);
        json_data.put(d_a_pct_success);
        json_data.put(d_a_pct_usable);
        json_data.put(d_m_pct_success);
        json_data.put(d_m_pct_usable);
        json_data.put(d_x_pct_success);
        json_data.put(d_x_pct_usable);
        
        resp.setContentType("application/json");
        resp.getWriter().println(json_data);

        logger.info( "Exiting." );
        return null;                  
    }
	
}
