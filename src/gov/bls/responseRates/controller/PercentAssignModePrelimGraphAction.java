package gov.bls.responseRates.controller;

import gov.bls.platform.log.*;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import gov.bls.responseRates.dao.PercentAssignModePrelimGraphDAO;
import gov.bls.responseRates.dao.PercentAssignModePrelimGraphData;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.json.JSONArray;
import org.json.JSONException;

public class PercentAssignModePrelimGraphAction extends Action{

    final static ILog logger = LogFactory.getLogger();
    
	public ActionForward execute(ActionMapping mapping, ActionForm form,
                                HttpServletRequest req, HttpServletResponse resp)
                                throws Exception {
    	
        logger.info("Entering.");       	    

      
        PercentAssignModePrelimGraphDAO dao = new PercentAssignModePrelimGraphDAO();        
              
        ArrayList<PercentAssignModePrelimGraphData> data; 
        
        
        try{
        	data  = dao.getPercentAssignModePrelimGraphData();
        }catch(SQLException e){            
            resp.getWriter().println(new JSONException("There was no data for this period."));
            logger.warn("There was no data for this period.");
            return null;      	
        }
        
        JSONArray json_data = new JSONArray();
    
        //These are the data series arrays.
        JSONArray d_ticks = new JSONArray();
        JSONArray d_i_success = new JSONArray();
        JSONArray d_i_usable = new JSONArray();
        JSONArray d_iw_success = new JSONArray();
        JSONArray d_iw_usable = new JSONArray();
        JSONArray d_iwmnt_success = new JSONArray();
        JSONArray d_iwmnt_usable = new JSONArray();
        JSONArray d_m_success = new JSONArray();
        JSONArray d_m_usable = new JSONArray();
        JSONArray d_mnt_success = new JSONArray();
        JSONArray d_mnt_usable = new JSONArray();
        JSONArray d_n_success = new JSONArray();
        JSONArray d_n_usable = new JSONArray();
        JSONArray d_t_success = new JSONArray();
        JSONArray d_t_usable = new JSONArray();
        JSONArray d_w_success = new JSONArray();
        JSONArray d_w_usable = new JSONArray();
  
        DecimalFormat wrap = new DecimalFormat("#0.00");
        
        //Format an array of 24 graph series
        try{
        	int i = 0;
        	int j = 0;
        	int k = 0;
        	int l = 0;
        	int m = 0;
        	int o = 0;
        	int p = 0;
        	int q = 0;
        	
            for(PercentAssignModePrelimGraphData n : data){
            	
            	if(n.getAssignMode().equals("I")){
            	
            		d_i_success.put(new JSONArray().put(i).put(wrap.format(n.getPctSuccessful())));
            		d_i_usable.put(new JSONArray().put(i).put(wrap.format(n.getPctUsable())));
            		
            		if(i%12==0){
            			d_ticks.put(new JSONArray().put(i).put(n.getPeriod()));            			
            		}
                	i=i+1;            		
            	}else if(n.getAssignMode().equals("I+W")){
            		d_iw_success.put(new JSONArray().put(j).put(wrap.format(n.getPctSuccessful())));
            		d_iw_usable.put(new JSONArray().put(j).put(wrap.format(n.getPctUsable())));
                	j=j+1;
            	}else if(n.getAssignMode().equals("I+W+M+N+T")){
            		d_iwmnt_success.put(new JSONArray().put(k).put(wrap.format(n.getPctSuccessful())));
            		d_iwmnt_usable.put(new JSONArray().put(k).put(wrap.format(n.getPctUsable())));
                	k=k+1;
            	}else if(n.getAssignMode().equals("M")){
            		d_m_success.put(new JSONArray().put(l).put(wrap.format(n.getPctSuccessful())));
            		d_m_usable.put(new JSONArray().put(l).put(wrap.format(n.getPctUsable())));
                	l=l+1;
            	}else if(n.getAssignMode().equals("M+N+T")){
            		d_mnt_success.put(new JSONArray().put(m).put(wrap.format(n.getPctSuccessful())));
            		d_mnt_usable.put(new JSONArray().put(m).put(wrap.format(n.getPctUsable())));
            		m=m+1;
            	}else if(n.getAssignMode().equals("N")){
            		d_n_success.put(new JSONArray().put(o).put(wrap.format(n.getPctSuccessful())));
            		d_n_usable.put(new JSONArray().put(o).put(wrap.format(n.getPctUsable())));
            		o=o+1;
            	}else if(n.getAssignMode().equals("T")){
            		d_t_success.put(new JSONArray().put(p).put(wrap.format(n.getPctSuccessful())));
            		d_t_usable.put(new JSONArray().put(p).put(wrap.format(n.getPctUsable())));
            		p=p+1;
            	}else if(n.getAssignMode().equals("W")){
            		d_w_success.put(new JSONArray().put(q).put(wrap.format(n.getPctSuccessful())));
            		d_w_usable.put(new JSONArray().put(q).put(wrap.format(n.getPctUsable())));
            		q=q+1;
            	}                   
            	
            }
        }catch(Exception e){   	     
            resp.getWriter().println(new JSONException("There was a Web server error, needing system admin attention."));         
            logger.error("There was a parsing error.");
            return null;    	    	
        }
          
        json_data.put(d_ticks);
        json_data.put(d_i_success);
        json_data.put(d_i_usable);
        json_data.put(d_iw_success);
        json_data.put(d_iw_usable);
        json_data.put(d_iwmnt_success);
        json_data.put(d_iwmnt_usable);
        json_data.put(d_m_success);
        json_data.put(d_m_usable);
        json_data.put(d_mnt_success);
        json_data.put(d_mnt_usable);
        json_data.put(d_n_success);
        json_data.put(d_n_usable);
        json_data.put(d_t_success);
        json_data.put(d_t_usable);
        json_data.put(d_w_success);
        json_data.put(d_w_usable);
        
        resp.setContentType("application/json");
        resp.getWriter().println(json_data);

        logger.info( "Exiting." );
        return null;                  
    }
	
}
