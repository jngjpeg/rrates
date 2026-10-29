package gov.bls.ips.shared.email.components.util;

import gov.bls.ips.shared.email.components.exceptions.*;

public class EmailService {	
		
	public static void sendEmail(String subsystemCode, String featureCode, String overrideSubject, String prependToToSubject, String appendToSubject,
			String overrideBodyText, String dataSource) throws EmailException {
		
		EmailClient.sendEmail(subsystemCode, featureCode, overrideSubject, 
				prependToToSubject, appendToSubject, overrideBodyText, dataSource);
		
	}
           

}

