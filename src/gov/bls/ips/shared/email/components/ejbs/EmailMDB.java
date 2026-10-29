package gov.bls.ips.shared.email.components.ejbs;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.email.components.util.EmailHelper;
import gov.bls.ips.shared.logger.components.IPSLogger;


/**
 * Message-Driven Bean implementation class for: EmailMDB
 */

@MessageDriven(name = "EmailMDB",
activationConfig = {
	    @ActivationConfigProperty(propertyName  = "destinationType", 
	                              propertyValue = "javax.jms.Queue"),
	 
	    @ActivationConfigProperty(propertyName  = "connectionFactoryJndiName",
	                              propertyValue = "jms/EmailConnectionFactory"), 
	 
	    @ActivationConfigProperty(propertyName  = "destinationJndiName",
	                              propertyValue = "jms/EmailQueue")
	  }
)

public class EmailMDB implements MessageListener {	
	
	public static final String CLASS_NAME = ClassFromStatic.getCurrentClass().getName();
	
    /**
     * Default constructor. 
     */
    public EmailMDB() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	    	
    	 MapMessage mapmessage = (MapMessage) message;
         try {
           Enumeration e = mapmessage.getMapNames();
           Hashtable mail = new Hashtable();
           while (e.hasMoreElements()) {
             String key = (String) e.nextElement();
             String val = mapmessage.getString(key);
             mail.put(key, val);                      
            
           }
           EmailHelper.sendmail(mail);
          
         }
         catch (Exception e) {
			e.printStackTrace();
			IPSLogger.logError(CLASS_NAME, "Exception in onMessage:" + e);
         }
        
    }    
     

}
