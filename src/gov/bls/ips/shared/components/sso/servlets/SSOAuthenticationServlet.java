/*******************************************************************************
*
* FILE : SSOAuthenticationServlet.java
*
* DESCRIPTION :
*   
*   
* REVISION HISTORY :
*
* Date         Name              Release             Description
* -------  -----------------     ----------     ---------------------------------
* 1Sep2015   Arun Jayavel          Sprint-17              original version
* 21Sep2015  Arun Jayavel          Sprint-18              Removed the dependency from IPS user package 
* 27/06/2018 Vijay Sathiya   	ICR Sprint50   		updated doPost for RestAuthenticationRequestHandler
* 01/06/2022 Ram Velkur         IPS6.0              Modified doPost() method and removed SOAP call for SSO Authentication.
* 01/19/2022 Ram Velkur         IPS6.0              Modified doPost() method and removed _SSO_AUTHENTICATION_REST_YN property check.
* 04/07/2023 Ashok Yeddula		IPS7.0				injected SSOEJB to pass the instance to handler for DB call.
*  Copyright 2015 by All IPS-BLS .
* All rights reserved.
******************************************************************************/
package gov.bls.ips.shared.components.sso.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.bls.ips.shared.components.sso.RestAuthenticationRequestHandler;
import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.logger.components.IPSLogger;

public class SSOAuthenticationServlet extends HttpServlet {

   private static final long serialVersionUID = 1L;
   public static String _CLASS_NAME=ClassFromStatic.getCurrentClass().getName();
   
   
   @Override
	public void init() throws ServletException {		
		super.init();
		IPSLogger.logDebug(_CLASS_NAME,".init() BEGIN ");
		IPSLogger.logDebug(_CLASS_NAME,".init() END");
	}
   
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException{
  	  IPSLogger.logDebug(_CLASS_NAME,".doGet() BEGIN ");
  	 
     doPost(request, response);
     IPSLogger.logDebug(_CLASS_NAME,".doGet() END");	  
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException{
     IPSLogger.logDebug(_CLASS_NAME,".doPost() BEGIN ");
     RestAuthenticationRequestHandler ssoRestRequestHandler;
     try{
    	 //Use Rest Web Service
    	 ssoRestRequestHandler= new RestAuthenticationRequestHandler();
    	 ssoRestRequestHandler.handleRequest(request, response);
     }
     catch(Exception e){
		e.printStackTrace();
       IPSLogger.logError(_CLASS_NAME+".doPost() got an Exception  ",e);
     }
     finally{
    	 ssoRestRequestHandler=null;
     }
     IPSLogger.logDebug(_CLASS_NAME,".doPost() END ");	  
   }
}


