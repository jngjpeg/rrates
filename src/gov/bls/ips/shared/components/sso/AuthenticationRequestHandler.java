/*******************************************************************************
*
* FILE : AuthenticationRequestHandler.java
*
* DESCRIPTION :
*
*  REVISION HISTORY :
*
* Date         	Name            Release              Description
* -------  	-----------------   ----------     ---------------------------------
* 09/21/2015   Sirisha Kommi     	Sprint17   		Original Version
* 10/19/2015   Sirisha Kommi		Sprint 18   	Added 'token save or not' based on property and column Name change
* 10/20/2015   Arun Jayavel 		Sprint 18   	Added Illegal state exception handling
* 10/21/2015   Arun Jayavel 		Sprint 18   	Added implementation for _SSO_WEB_SERVICE_ERROR_MSG
* 10/21/2015   Selva Sathiah 		Sprint 18   	Added code to print webservice response in the log.
* 10/22/2015   Arun Jayavel 		Sprint 18   	Added added token to WS Response.
* 10/23/2015   Sirisha Kommi		Sprint 18		Allowing nulls for User_Sid
* 03/30/2016   Selva Sathiah		Sprint 23		Added code to store LAN name in session

* Copyright 2015 by All IPS-BLS .
* All rights reserved.
******************************************************************************/
/*
package gov.bls.ips.shared.components.sso;


import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.BindingProvider;

import org.apache.commons.lang3.StringUtils;

import gov.bls.ips.shared.components.sso.util.SSORequestData;
import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.components.util.IPSServiceLocator;
import gov.bls.ips.shared.json.IPSJSParser;
import gov.bls.ips.shared.logger.components.IPSLogger;
import gov.bls.ips.shared.sso.api.util.SSOConstants;
import gov.bls.ips.shared.sso.api.value.SSOInfoValue;
import gov.bls.ips.shared.sso.api.value.SSOUserTokenValue;
import gov.bls.ips.shared.sso.api.value.SSOWSResponseValue;
import gov.bls.ips.shared.sso.api.interfaces.SSOInfoRemote;
import gov.bls.ips.shared.sso.wsclient.AuthenticationService;
import gov.bls.ips.shared.sso.wsclient.IAuthenticationService;


	public class AuthenticationRequestHandler {

	public static String _CLASS_NAME=ClassFromStatic.getCurrentClass().getName();
	SSORequestData requestObject = null;

	public AuthenticationRequestHandler(SSORequestData requestObject){
		this.requestObject = requestObject;
	}

	public AuthenticationRequestHandler(){
	}

	public void handleRequest(HttpServletRequest request, HttpServletResponse response){
      IPSLogger.logDebug(_CLASS_NAME,".handleRedirectRequest() BEGIN ");
      String ssoToken=null;
	  try{
		String actionTypeCode=(String)request.getAttribute(SSOConstants._ACTION_TYPE);
		ssoToken=request.getParameter(SSOConstants._SSO_AUTHENTICATION_TOKEN_PARAM_NAME);
				
	    IPSLogger.logDebug(_CLASS_NAME,".handleRedirectRequest() BEGIN actionTypeCode="+actionTypeCode);
		if(StringUtils.isNotBlank(ssoToken)){
	 	  //IPSLogger.logDebug(_CLASS_NAME,".handleRedirectRequest() ssoToken="+ssoToken);
          SSOWSResponseValue wsResponseValueObj=this.getRedeemToken(ssoToken);
          if(wsResponseValueObj!=null){
        	SSOInfoValue ssoInfoValueObj=createSSOInfoValueObj(wsResponseValueObj);
        	if(StringUtils.equalsIgnoreCase(SSOConstants._INSERT_SSO_INFO_IN_DB_YN, "Y")==true){
        		insertSSOInfo(ssoInfoValueObj,getDataSourceName());
        	}
	        request.setAttribute(SSOConstants._LAN_NAME, wsResponseValueObj.samAccountName);
            request.setAttribute(SSOConstants._SSO_AUTHENTICATION_TOKEN_PARAM_NAME, ssoToken);
            request.setAttribute(SSOConstants._ACTION_TYPE,SSOConstants.
            		_SSO_AUTHENTICATION_SUCCESS_RESPONSE_CODE);
            HttpSession session=request.getSession();
            session.setAttribute(SSOConstants._AUTHORIZED_USER_LAN_NAME, wsResponseValueObj.samAccountName);
            RequestDispatcher requestDispatcher=request.getRequestDispatcher(
        		             SSOConstants._SSO_AUTHENTICATION_SUCCESS_RESPONSE_REDIRECT_URL);
		    requestDispatcher.forward(request, response);
          }
          else {
        	throw new Exception(SSOConstants._SSO_WEB_SERVICE_ERROR_MSG+ssoToken);
          }
        }
		else if(StringUtils.equalsIgnoreCase(actionTypeCode, SSOConstants._ACTION_TYPE_AUTHENTICATE_USER)==true){
		  IPSLogger.logDebug(_CLASS_NAME,".handleRedirectRequest() SSOConstants._SSO_AUTHENTICATION_REDIRECT_URL="+
		                      SSOConstants._SSO_AUTHENTICATION_REDIRECT_URL+"'");
          response.sendRedirect(SSOConstants._SSO_AUTHENTICATION_REDIRECT_URL+SSOConstants._APP_ID);
		  //response.sendRedirect(SSOConstants._SSO_AUTHENTICATION_REDIRECT_URL);
		}
		else{
          throw new Exception("Invalid Invokation");
		}
	  }
	  catch(IllegalStateException ex){
	     IPSLogger.logError(_CLASS_NAME+".handleRedirectRequest() Illegal state Exception  ", ex);
	     handleErrorResponse(request,response,ex);
	  }
	  catch(Exception e){
		 IPSLogger.logError(_CLASS_NAME+".handleRedirectRequest() Exception  ", e);
		 handleErrorResponse(request,response,e);
	  }
      IPSLogger.logDebug(_CLASS_NAME,".handleRedirectRequest() BEGIN ");
	}

    private void handleErrorResponse(HttpServletRequest request, HttpServletResponse response,Exception e){
    	try{
           request.setAttribute(SSOConstants._ERROR_MESSAGE,e.getMessage() );
           request.setAttribute(SSOConstants._ACTION_TYPE,SSOConstants._SSO_AUTHENTICATION_FAILURE_RESPONSE_CODE);

  		   IPSLogger.logDebug(_CLASS_NAME,".handleRedirectRequest() SSOConstants._SSO_AUTHENTICATION_REDIRECT_URL="+
  				             SSOConstants._SSO_AUTHENTICATION_SUCCESS_RESPONSE_REDIRECT_URL+"'");
           RequestDispatcher requestDispatcher=request.getRequestDispatcher(SSOConstants._SSO_AUTHENTICATION_SUCCESS_RESPONSE_REDIRECT_URL);
 		  requestDispatcher.forward(request, response);
 	    	}
    	catch(Exception er){
		  IPSLogger.logError(_CLASS_NAME+".handleErrorResponse() Exception  ", er);
    	}
    }
   private void insertSSOInfo(SSOInfoValue ssoInfoVO, String _dataSourceName) throws Exception {
	 IPSLogger.logDebug(_CLASS_NAME, ".insertSSOInfo()- BEGIN");
     try {
       getSSOInfoRemote().insertSSOInfo(ssoInfoVO,_dataSourceName);
	 }
     catch (Exception e) {
	   IPSLogger.logError(_CLASS_NAME+".insertSSOInfo(ssoInfoVO, _dataSourceName) Exception  ", e);
	   throw e;
     }
	 IPSLogger.logDebug(_CLASS_NAME,".insertSSOInfo() END");
	}


	private SSOInfoRemote getSSOInfoRemote() throws Exception {
		IPSLogger.logDebug(_CLASS_NAME, ".getSSOInfoRemote()- BEGIN");
		SSOInfoRemote ssoInfoRemote = null;
		try {
			ssoInfoRemote = (SSOInfoRemote) IPSServiceLocator.getEJBByJNDIName(SSOConstants.SSOINFO_EJB_JNDI_NAME);

		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME, "getSSOInfoRemote()"
					+ "Got Exception ", e);
			throw e;
		}
	    IPSLogger.logDebug(_CLASS_NAME,".getSSOInfoRemote() END");
		return ssoInfoRemote;
	}


	public static String getDataSourceName() {
		IPSLogger.logDebug(_CLASS_NAME, "getDataSourceName - Start");
		String dataSourceName = SSOConstants._SSO_DATA_SOURCE;

		if (dataSourceName == null || dataSourceName.isEmpty()) {
			dataSourceName = SSOConstants._SSO_DEFAULT_DATA_SOURCE;
		}
		IPSLogger.logDebug(_CLASS_NAME, "dataSourceName= " + dataSourceName + "  getDataSourceName - End");
		return dataSourceName;
	}

	private SSOWSResponseValue getRedeemToken(String token){
		IPSLogger.logDebug(_CLASS_NAME, "getRedeemToken - START");
		SSOWSResponseValue ssoResponseObj=null;
		try{			
			AuthenticationService authnService = new AuthenticationService();

			BindingProvider bp = (BindingProvider)authnService.getPort(IAuthenticationService.class);
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, SSOConstants._SSO_WS_END_POINT_URL);

			IAuthenticationService iAuthService= (IAuthenticationService)bp;
			String authnResponse=iAuthService.redeemToken(SSOConstants._APP_ID, token);
			//IPSLogger.logDebug(_CLASS_NAME, "Token-->"+token+"  getRedeemToken() - SSO WS RESPONSE-->"+authnResponse);
			ssoResponseObj=(SSOWSResponseValue)IPSJSParser.readValue(authnResponse,new SSOWSResponseValue(),true);

		}catch(Exception e){
			//IPSLogger.logError(_CLASS_NAME, "Exception in getRedeemToken() Token-->"+token+"Error:"+e.getMessage());
			IPSLogger.logError(_CLASS_NAME, "Exception in getRedeemToken() Token--> Error:"+e.getMessage());
		}
		IPSLogger.logDebug(_CLASS_NAME, "getRedeemToken - END");
		return ssoResponseObj;
	}
	public SSOWSResponseValue authenticateUserByLanName(String userName,String domain){
		IPSLogger.logDebug(_CLASS_NAME, "getRedeemToken - START");
		SSOWSResponseValue ssoResponseObj=null;
		try{
			AuthenticationService authnService = new AuthenticationService();

			BindingProvider bp = (BindingProvider)authnService.getPort(IAuthenticationService.class);
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, SSOConstants._SSO_WS_END_POINT_URL);

			IAuthenticationService iAuthService= (IAuthenticationService)bp;
			String lanNameWithDomain=domain+"\\"+userName;
			
			///Authenticate User by user name and domain.
			String authUserResponse = iAuthService.authenticateUser(SSOConstants._APP_ID, lanNameWithDomain, "");
			//IPSLogger.logDebug(_CLASS_NAME, "lan name with domain-->"+lanNameWithDomain+"  authenticateUser() - SSO WS RESPONSE-->"+authUserResponse);			
			SSOUserTokenValue ssoUserToken = (SSOUserTokenValue) IPSJSParser.readValue(authUserResponse, new SSOUserTokenValue(),true);				
			//IPSLogger.logDebug(_CLASS_NAME, "authenticateUserByLanName authenticateUser() - sso User Token obj-->"+ssoUserToken);
			
			//Get UserProfile object by Redeem Token
			if(ssoUserToken!=null && ssoUserToken.userToken !=null){				
				String redeemTokenResponse = iAuthService.redeemToken(SSOConstants._APP_ID, ssoUserToken.userToken);
				ssoResponseObj=(SSOWSResponseValue)IPSJSParser.readValue(redeemTokenResponse,new SSOWSResponseValue(),true);				
			}
			//IPSLogger.logDebug(_CLASS_NAME, "authenticateUserByLanName SSO User Obj"+ssoResponseObj);
		}catch(Exception e){
			IPSLogger.logError(_CLASS_NAME, "Exception in authenticateUserByLanName() lanName-->"+userName+" Domain:"+domain+" Error:"+e.getMessage());
		}
		IPSLogger.logDebug(_CLASS_NAME, "authenticateUserByLanName - END");
		return ssoResponseObj;
	}
	private SSOInfoValue createSSOInfoValueObj(SSOWSResponseValue sSOWSResponseValue){
		SSOInfoValue ssoInfoValueObj=null;
		try{
		   ssoInfoValueObj=new SSOInfoValue();
		   //ssoInfoValueObj._ssoToken=sSOWSResponseValue.userToken;
		   ssoInfoValueObj._userIpAddr=sSOWSResponseValue.clientIpAddress;
		   ssoInfoValueObj._userSid=null;
		   ssoInfoValueObj._userLanName=sSOWSResponseValue.samAccountName;
		   ssoInfoValueObj._userEmailAddr=sSOWSResponseValue.emailAddress;
		   ssoInfoValueObj._ssoRelyingPartyURL=sSOWSResponseValue.siteRedirect;
		   ssoInfoValueObj._authenDate=getDateFromSSOWSString(sSOWSResponseValue.webServiceLoginTime);
		}
		catch(Exception e){
           IPSLogger.logError(_CLASS_NAME+"Exception: ", e);
		}
		return ssoInfoValueObj;
	}
	private Date getDateFromSSOWSString(String ssoDateString){
		Date dateVal=null;
		try{
			String timestampSeconds=ssoDateString.substring(ssoDateString.indexOf("(")+1,ssoDateString.indexOf(")"));
		    dateVal=new Date(Long.parseLong(timestampSeconds));
		}catch(Exception e){
			IPSLogger.logError(_CLASS_NAME, "Error:"+e.getMessage());
		}
	    return dateVal;
	}

    private SSOInfoValue getSSOInfo(String userLanName,String _dataSourceName) {
		IPSLogger.logDebug(_CLASS_NAME, ".insertSSOInfo()- BEGIN");
		SSOInfoValue ssoInfoVO = new SSOInfoValue();
		//TODO:remove this hardcoded test data
		userLanName="jayavel_a";
		try {
			ssoInfoVO = getSSOInfoRemote().getSSOInfo(userLanName, _dataSourceName);
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME+".insertSSOInfo(ssoInfoVO, _dataSourceName) Exception  ", e);
			e.printStackTrace();
		}
	    IPSLogger.logDebug(_CLASS_NAME,".insertSSOInfo() END");
		return ssoInfoVO;
	}
}
*/