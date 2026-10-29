package gov.bls.responseRates.dao;

import java.io.Serializable;

public class PercentAssignModePrelimGraphData implements Serializable {

	private String assign_mode_coll = "";
	private Integer period_ = 0;
	private Double pct_successful = 0.0d;
	private Double pct_usable = 0.0d;
	
	public String getAssignMode(){
		return assign_mode_coll;
	}
	
	public void setAssignMode(String assign_mode_coll){
		this.assign_mode_coll = assign_mode_coll;
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
