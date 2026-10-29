package gov.bls.ips.shared.email.components.util;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.email.components.dao.EmailDAO;
import gov.bls.ips.shared.email.components.exceptions.EmailException;
import gov.bls.ips.shared.email.components.util.constants.EmailConstantsIF;
import gov.bls.ips.shared.logger.components.IPSLogger;

public class EmailHelper {   
		
		public static final String CLASS_NAME = ClassFromStatic.getCurrentClass().getName();
		
        private static Properties props = new Properties();
       
        private EmailHelper() {}

        public static void sendmail(Map mail) throws EmailException {        	
    		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
    		 IPSLogger.logDebug(CLASS_NAME, "." + methodName + "BEGIN");
    		 
			  if ((mail.get("from")==null) || (mail.get("to")==null) || (mail.get("subject")==null)) {
		   	   	throw new EmailException("Error in Email-Required properties not set");
		      }
        	  
    		 IPSLogger.logDebug(CLASS_NAME, "." + methodName + "- " + "From : " + mail.get("from"));
    		 
    		 IPSLogger.logDebug(CLASS_NAME, "." + methodName + "- " + "To : " + mail.get("to"));
			  String dataSourceName = null;
			  if (mail.get("dataSource")!=null) {
				  dataSourceName = (String) mail.get("dataSource");
			  } else {
				  throw new EmailException("Error in Email-Data Source name not specified");
	          }	 
			  
			  try {
	                   	
	           props = EmailDAO.getObject(dataSourceName).getCommonEmailProps();
	
	           Session session = Session.getDefaultInstance(props, null);
	           Message msg = new MimeMessage(session);
	           
	           msg.setFrom(new InternetAddress((String)mail.get("from")));
	           msg.setRecipients(Message.RecipientType.TO,
	                             InternetAddress.parse(
	                             (String)mail.get("to")));
	       
	           msg.setSubject((String)mail.get("subject"));
	           msg.setSentDate(new Date());
	           
	           if(   mail.containsKey(EmailConstantsIF.MIME_TYPE)
	              && EmailConstantsIF.MIME_TYPE_HTML.equals((String) mail.get(EmailConstantsIF.MIME_TYPE))){
	        	 msg.setContent((String)mail.get("message"), EmailConstantsIF.MIME_TYPE_HTML);
	           }else{
	             msg.setText((String)mail.get("message"));
	           }
	           
	           Transport.send(msg);
	           IPSLogger.logDebug(CLASS_NAME, "." + methodName + "END");
	         }
	         catch(SendFailedException se) {        	 
	        	 se.printStackTrace();          
	        	 throw new EmailException("Error in Email - Unable to send email");
	         }
	         catch(MessagingException me) {         
	        	 me.printStackTrace();
	        	 IPSLogger.logError(CLASS_NAME, "Messaging Exception in" + methodName + ": " + me);
	        	 throw new EmailException("Error in Email- Messaging Error");
	         }         
			 catch(Exception ee) {         
	        	 ee.printStackTrace();
	        	 IPSLogger.logError(CLASS_NAME, "Exception in" + methodName + ": " + ee);
	        	 throw new EmailException("Email failed");
	         } 
        }
		
	               
	}

