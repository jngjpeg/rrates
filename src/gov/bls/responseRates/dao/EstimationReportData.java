package gov.bls.responseRates.dao;

import java.io.Serializable;

/*Note: named Data; a.k.a.: TransferObject.*/

public class EstimationReportData implements Serializable {
	
	private String data_type = "";
	private Integer period = 0;
	private Double usability_ratio = 0.0d;
	private Double useable_ratio = 0.0d;
	
    public String getDataType() {
        return data_type;
    }

    public void setDataType(String data_type) {
        this.data_type = data_type;
    }    
    
    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }    

    public Double getUsabilityRatio() {
        return usability_ratio;
    }

    public void setUsabilityRatio(Double usability_ratio) {
        this.usability_ratio = usability_ratio;
    }    
    
    public Double getUseableRatio() {
        return useable_ratio;
    }

    public void setUseableRatio(Double useable_ratio) {
        this.useable_ratio = useable_ratio;
    }    
    
}
