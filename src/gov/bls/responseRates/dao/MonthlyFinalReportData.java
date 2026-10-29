package gov.bls.responseRates.dao;

import java.io.Serializable;

/*Note: named Data; a.k.a.: TransferObject.*/

public class MonthlyFinalReportData implements Serializable {
	
	private Integer period_ = 0;
	private Integer resp_successful = 0;
	private Integer resp_eligible = 0;
	private Integer resp_usable = 0;
	private Double pct_successful = 0.0d;
	private Double pct_usable = 0.0d;
	private Double st_pct_successful = 0.0d;
	private Double lt_pct_successful = 0.0d;
	private Double st_pct_usable = 0.0d;
	private Double lt_pct_usable = 0.0d;
	private Double bls_price_qoutes = 0.0d;
	private Double bls_estimation_resp = 0.0d;
	
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
    
    public Double getStPctSuccessful() {
        return st_pct_successful;
    }

    public void setStPctSuccessful(Double st_pct_successful) {
        this.st_pct_successful = st_pct_successful;
    }     
    public Double getLtPctSuccessful() {
        return lt_pct_successful;
    }

    public void setLtPctSuccessful(Double lt_pct_successful) {
        this.lt_pct_successful = lt_pct_successful;
    } 
    
    public Double getStPctUsable() {
        return st_pct_usable;
    }

    public void setStPctUsable(Double st_pct_usable) {
        this.st_pct_usable = st_pct_usable;
    }     
    public Double getLtPctUsable() {
        return lt_pct_usable;
    }

    public void setLtPctUsable(Double lt_pct_usable) {
        this.lt_pct_usable = lt_pct_usable;
    }     
  
    public Double getBlsPriceQuotes() {
        return bls_price_qoutes;
    }

    public void setBlsPriceQuotes(Double bls_price_qoutes) {
        this.bls_price_qoutes = bls_price_qoutes;
    }  
    
    public Double getBlsEstimationResp() {
        return bls_estimation_resp;
    }

    public void setBlsEstimationResp(Double bls_estimation_resp) {
        this.bls_estimation_resp = bls_estimation_resp;
    }  	
}
