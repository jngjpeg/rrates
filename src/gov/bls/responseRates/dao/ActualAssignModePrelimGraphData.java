package gov.bls.responseRates.dao;

import java.io.Serializable;

public class ActualAssignModePrelimGraphData implements Serializable {

	private String assign_mode_coll = "";
	private Integer period_ = 0;
	private Integer resp_successful = 0;
	private Integer resp_eligible = 0;
	private Integer resp_usable = 0;
	
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
