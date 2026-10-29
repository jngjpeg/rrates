package gov.bls.responseRates.dao;

import java.io.Serializable;

public class PercentRequestedPrelimGraphData implements Serializable {

	private String requested_ = "";
	private Integer period_ = 0;
	private Double pct_successful = 0.0d;
	private Double pct_eligible = 0.0d;
	private Double pct_usable = 0.0d;
	
	public String getRequested(){
		return requested_;
	}
	
	public void setRequested(String requested_){
		this.requested_ = requested_;
	}
	
    public Integer getPeriod() {
        return period_;
    }

    public void setPeriod(Integer period_) {
        this.period_ = period_;
    }    

    public Double getPctSuccessful() {
        return pct_successful;
    }

    public void setPctSuccessful(Double pct_successful) {
        this.pct_successful = pct_successful;
    }     
    
    public Double getPctUsable() {
        return pct_usable;
    }

    public void setPctUsable(Double pct_usable) {
        this.pct_usable = pct_usable;
    }    
    
 
}
