package gov.bls.responseRates.dao;

import java.io.Serializable;

/*Note: named Data; a.k.a.: TransferObject.*/

public class MonthlyData implements Serializable {
	
	private String feedback = "";

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }    
        
}
