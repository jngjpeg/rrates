package gov.bls.ips.shared.email.components.util;


import gov.bls.ips.shared.email.components.dao.EmailDAO;
import gov.bls.ips.shared.email.components.valueobjects.*;
import gov.bls.ips.shared.email.components.exceptions.*;

import java.util.*;
import javax.naming.*;
import javax.jms.*;
import javax.jms.Queue;

public class EmailClient {
    
    private static String QUEUE_NAME         = "jms/EmailQueue";
    private static String CONNECTION_FACTORY = "jms/EmailConnectionFactory";
    private QueueSender  sender;
    private QueueSession session;
    
    public static final String MIME_TYPE= "mimeType";
    public static final String MIME_TYPE_PLAIN= "text/plain";
    public static final String MIME_TYPE_HTML= "text/html";

    public EmailClient() {
        try {
            Context ctx = getInitialContext();
            QueueConnectionFactory conFactory =
                (QueueConnectionFactory) ctx.lookup(CONNECTION_FACTORY);            
            QueueConnection connection = conFactory.createQueueConnection();
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue chatQueue = (Queue) ctx.lookup(QUEUE_NAME);    
            sender = session.createSender(chatQueue);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    
    public static void sendEmail(String subsystemCode, String featureCode, String overrideSubject, 
    		String prePendToSubject, String appendToSubject,
			String overrideBodyText, String dataSource) throws EmailException {
    	
    	EmailDetailsVO emailDetailsVO = null;
    	String subject = null;
    	String body = null;
    	
    	try {
	    	EmailDAO emailDAO = EmailDAO.getObject(dataSource);
	    	if (emailDAO.canSendEmail()==false) return;	    	
	    	emailDetailsVO = emailDAO.getEmailDetails(subsystemCode, featureCode);
    	} catch(Exception e) {
    		e.printStackTrace();
    		throw new EmailException("Email Error: Unable to retrieve email details from the database");
    	}    	
    	
    	if (emailDetailsVO!=null) {
	    	EmailClient client = new EmailClient();
	        HashMap mail = new HashMap();
	        
	        if (emailDetailsVO.getRecipientEmailAddresses()!=null && emailDetailsVO.getRecipientEmailAddresses().trim().length()>0) {
	        	mail.put("to", emailDetailsVO.getRecipientEmailAddresses());
	        } else {
	        	throw new EmailException("Email Error: Recipient email addresses have not be specified");
	        }
	        
	        if (emailDetailsVO.getSenderEmailAddress()!=null && emailDetailsVO.getSenderEmailAddress().trim().length()>0) {
	        	mail.put("from", emailDetailsVO.getSenderEmailAddress());
	        } else {
	        	throw new EmailException("Email Error: Sender email address has not be specified");
	        }	     
	        
	        if (overrideSubject!=null && overrideSubject.length()>0) {
	        	subject = overrideSubject;
	        } else if (emailDetailsVO.getSubject()!=null && emailDetailsVO.getSubject().trim().length()>0) {
	        	subject = emailDetailsVO.getSubject();
	        } else {
	        	throw new EmailException("Email Error: Subject has not be specified");
	        }
	        
	        if (prePendToSubject!=null && prePendToSubject.trim().length()>0) {
	        	subject = prePendToSubject + " " + subject;	         
	        }
	        
	        if (appendToSubject!=null && appendToSubject.trim().length()>0) {
	        	subject = subject + " " + appendToSubject;	         
	        }
	        
	        mail.put("subject", subject);
	        
	        if (overrideBodyText!=null && overrideBodyText.length()>0) {
	        	body = overrideBodyText;
	        } else if (emailDetailsVO.getBodyText()!=null && emailDetailsVO.getBodyText().trim().length()>0) {
	        	body = emailDetailsVO.getBodyText();
	        } else if (overrideBodyText==null) {
	        	body = "";
	        }
	        
	        
	        mail.put("dataSource", dataSource);
	        mail.put("message", body);
	        
	        if (emailDetailsVO.getContentType()!=null && emailDetailsVO.getContentType().trim().length()>0) {
	        	mail.put(MIME_TYPE, emailDetailsVO.getContentType());
	        } else {
	        	throw new EmailException("Email Error: Mime Type has not be specified");
	        }	        
	      
	        try {
	        	client.sendmail(mail);
	        } catch(Exception e) {
	    		e.printStackTrace();
	    		throw new EmailException("Email Error: Unable to send email");
	    	}   
    	}
		
	}
           
      
    public void sendmail(Map mail) throws Exception {
        MapMessage message = session.createMapMessage();

        Set keys = mail.keySet();
        Iterator i = keys.iterator();
        while (i.hasNext()) {
          String key = (String) i.next();
          String val = (String) mail.get(key);
          message.setString(key, val); 
        }
        
        sender.send(message);
    }

    private Context getInitialContext() throws NamingException {
        return new InitialContext();
    }
   

}
