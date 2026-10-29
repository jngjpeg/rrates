package gov.bls.responseRates.dao;

import java.io.Serializable;

/*Note: named Data; a.k.a.: TransferObject.*/

public class SnapshotReportData implements Serializable {
	
	private String mode_of_collection = "";
	private String requested = "";
	private Integer successful = 0;
	private Double pct_successful = 0.0d;
	private Integer usable = 0;
	private Double pct_usable = 0.0d;
	private Integer eligible = 0;	
	
    public String getModeOfCollection() {
        return mode_of_collection;
    }

    public void setModeOfCollection(String mode_of_collection) {
        this.mode_of_collection = mode_of_collection;
    }    

    public String getRequested() {
        return requested;
    }

    public void setRequested(String requested) {
        this.requested = requested;
    }    
        
    public Integer getSuccessful() {
        return successful;
    }

    public void setSuccessful(Integer successful) {
        this.successful = successful;
    }    

    public Double getPctSuccessful() {
        return pct_successful;
    }

    public void setPctSuccessful(Double pct_successful) {
        this.pct_successful = pct_successful;
    }    
    
    public Integer getUsable() {
        return usable;
    }

    public void setUsable(Integer usable) {
        this.usable = usable;
    } 
    
    public Double getPctUsable() {
        return pct_usable;
    }

    public void setPctUsable(Double pct_usable) {
        this.pct_usable = pct_usable;
    }    
    
    public Integer getEligible() {
        return eligible;
    }

    public void setEligible(Integer eligible) {
        this.eligible = eligible;
    }        
            
}
