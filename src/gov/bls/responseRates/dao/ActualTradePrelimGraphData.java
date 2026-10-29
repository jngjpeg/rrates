package gov.bls.responseRates.dao;

import java.io.Serializable;

public class ActualTradePrelimGraphData implements Serializable {

	private String agg_type = "";
	private Integer period_ = 0;
	private Integer resp_successful = 0;
	private Integer resp_eligible = 0;
	private Integer resp_usable = 0;
	
	public String getAggType(){
		return agg_type;
	}
	
	public void setAggType(String agg_type){
		this.agg_type = agg_type;
	}
	
    public Integer getPeriod() {
        return period_;
    }

    public void setPeriod(Integer period_) {
        this.period_ = period_;
    }    

    public Integer getRespSuccessful() {
        return resp_successful;
    }

    public void setRespSuccessful(Integer resp_successful) {
        this.resp_successful = resp_successful;
    }    

    public Integer getRespEligible() {
        return resp_eligible;
    }

    public void setRespEligible(Integer resp_eligible) {
        this.resp_eligible = resp_eligible;
    }    
    
    public Integer getRespUsable() {
        return resp_usable;
    }

    public void setRespUsable(Integer resp_usable) {
        this.resp_usable = resp_usable;
    }    
    
 
}
