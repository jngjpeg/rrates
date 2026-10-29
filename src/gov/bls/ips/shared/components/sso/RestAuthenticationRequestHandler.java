/*******************************************************************************
*
* FILE : RestAuthenticationRequestHandler.java
*
* DESCRIPTION :
*
*  REVISION HISTORY :
*
* Date         	Name            Release              Description
* -------  	-----------------   ----------     ---------------------------------
* 27/06/2018   Vijay Sathiya   	ICR Sprint50   		Original Version 
* 07/09/2018   Vijay Sathiya   	ICR Sprint51   		Updated getDateFromSSOWSString() to format time. 
* 													Commented setting _authenDate as sysdate is used in sql.
* 06/17/2020   Vijay Sathiya   	ICR New Sprint28   	Updated handleRequest() to manage Session ID
* 06/24/2020   Vijay Sathiya   	ICR New Sprint28   	Updated handleRequest() to add ssoInfo._sessionId
* Copyright 2015 by All IPS-BLS .
* All rights reserved. 
******************************************************************************/
package gov.bls.ips.shared.components.sso;


import java.sql.Date;

import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import gov.bls.ips.shared.components.sso.util.SSORequestData;
import gov.bls.ips.shared.components.sso.util.SessionKeyUtil;
import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.components.util.IPSServiceLocator;
import gov.bls.ips.shared.json.IPSJSParser;
import gov.bls.ips.shared.logger.components.IPSLogger;
import gov.bls.ips.shared.sso.api.interfaces.SSOInfoRemote;
import gov.bls.ips.shared.sso.api.util.SSOConstants;
import gov.bls.ips.shared.sso.api.value.SSOInfoValue;
import gov.bls.ips.shared.sso.api.value.SSOUserTokenValue;
import gov.bls.ips.shared.sso.api.value.SSOWSResponseValue;


	public class RestAuthenticationRequestHandler {

	public static String _CLASS_NAME=ClassFromStatic.getCurrentClass().getName();
	public static String  _APP_ID_TOKEN_JSON= "{\"AppId\":\""+SSOConstants._APP_ID+"\",\"Token\":\"";
	public static String  _APP_ID_USER_NAME_JSON= "{\"AppId\":\""+SSOConstants._APP_ID+"\",\"ClientIpAddress\":\"1\",\"UserName\":\"";
	
	private SSORequestData requestObject = null;
	private  WebTarget webServiceTarget = null;

	public RestAuthenticationRequestHandler(SSORequestData requestObject){
		this.requestObject = requestObject;
	}

	public RestAuthenticationRequestHandler(){
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
          HttpSession session=request.getSession(true);
          
          if(wsResponseValueObj!=null){
        	SSOInfoValue ssoInfoValueObj=createSSOInfoValueObj(wsResponseValueObj);
        	
        	if(StringUtils.equalsIgnoreCase(SSOConstants._INSERT_SSO_INFO_IN_DB_YN, "Y")==true){
        		 session.setAttribute(SSOConstants._SESSION_KEY, SessionKeyUtil.getSessionKey().toString());
        		 session.setAttribute(SSOConstants._SESSION_ID,session.getId());
        		 ssoInfoValueObj._sessionId=SessionKeyUtil.getSessionTokenHash(session.getId(), ((String)session.getAttribute("SESSION_KEY")).getBytes());
        		 insertSSOInfo(ssoInfoValueObj);
        	}
	        request.setAttribute(SSOConstants._LAN_NAME, wsResponseValueObj.samAccountName);
            request.setAttribute(SSOConstants._SSO_AUTHENTICATION_TOKEN_PARAM_NAME, ssoToken);
            request.setAttribute(SSOConstants._ACTION_TYPE,SSOConstants.
            		_SSO_AUTHENTICATION_SUCCESS_RESPONSE_CODE);
            
            session.setAttribute(SSOConstants._AUTHORIZED_USER_LAN_NAME, wsResponseValueObj.samAccountName);
            RequestDispatcher requestDispatcher=request.getRequestDispatcher(
        		             SSOConstants._SSO_AUTHENTICATION_SUCCESS_RESPONSE_REDIRECT_URL);
		    requestDispatcher.forward(request, response);
          }
          else {
        	//throw new Exception(SSOConstants._SSO_WEB_SERVICE_ERROR_MSG+ssoToken);
			throw new Exception(SSOConstants._SSO_WEB_SERVICE_ERROR_MSG);
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
		  ex.printStackTrace();
	     IPSLogger.logError(_CLASS_NAME+".handleRedirectRequest() Illegal state Exception  ", ex);
	     handleErrorResponse(request,response,ex);
	  }
	  catch(Exception e){
		  e.printStackTrace();
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
			er.printStackTrace();
		  IPSLogger.logError(_CLASS_NAME+".handleErrorResponse() Exception  ", er);
    	}
    }
   public void insertSSOInfo(SSOInfoValue ssoInfoVO) throws Exception {
	 IPSLogger.logDebug(_CLASS_NAME, ".insertSSOInfo()- BEGIN");
     try {
    	 if(StringUtils.equalsIgnoreCase(SSOConstants._INSERT_SSO_INFO_IN_DB_YN, "Y")==true) {
    		 //ssoInfoVO._ssoToken=encryptToken(ssoInfoVO._ssoToken);
    		 getSSOInfoRemote().insertSSOInfo(ssoInfoVO,getDataSourceName());
    		 }
	 }
     catch (EJBException e) {
		 e.printStackTrace();
	   IPSLogger.logError(_CLASS_NAME+".insertSSOInfo(ssoInfoVO, _dataSourceName) Exception  ", e);
	   throw new Exception(e);
     }
	 IPSLogger.logDebug(_CLASS_NAME,".insertSSOInfo() END");
	}
   

	private SSOInfoRemote getSSOInfoRemote() throws Exception {
		IPSLogger.logDebug(_CLASS_NAME, ".getSSOInfoRemote()- BEGIN");
		SSOInfoRemote ssoInfoRemote = null;
		try {
			ssoInfoRemote = (SSOInfoRemote) IPSServiceLocator.getEJBByJNDIName(SSOConstants.SSOINFO_EJB_JNDI_NAME);

		} catch (Exception e) {
			e.printStackTrace();
			IPSLogger.logError(_CLASS_NAME, "getSSOInfoRemote()"
					+ "Got Exception ", e);
			throw e;
		}
	    IPSLogger.logDebug(_CLASS_NAME,".getSSOInfoRemote() END");
		return ssoInfoRemote;
	}


	private static String getDataSourceName() {
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
		Builder builder =null;
		Response response =null;
		String responseString =null;		
		String jsonInput=null;
		try{			
						
			webServiceTarget=getWebserviceTarget(SSOConstants._SSO_REST_WS_END_POINT_URL+SSOConstants._SERVICE_REDEEM_TOKEN);
			IPSLogger.logDebug(_CLASS_NAME, "getRedeemToken Begin webServiceTarget uri:"+webServiceTarget.getUri()); 
			builder = webServiceTarget.request();
			jsonInput=_APP_ID_TOKEN_JSON + token + "\"}";
			
			//IPSLogger.logDebug(_CLASS_NAME, "getRedeemToken input:"+jsonInput);
			response = builder.post(Entity.json(jsonInput));
			responseString = response.readEntity(String.class);
			
			//IPSLogger.logDebug(_CLASS_NAME, "Parsed Response:"+responseString);			
				
			ssoResponseObj=new SSOWSResponseValue(); 
			ssoResponseObj=(SSOWSResponseValue)IPSJSParser.readValue(responseString,ssoResponseObj,true);			
			

		}catch(Exception e){
			e.printStackTrace();
			//IPSLogger.logError(_CLASS_NAME, "Exception in getRedeemToken() Token-->"+token+"Error:"+e.getMessage());
			IPSLogger.logError(_CLASS_NAME, "Exception in getRedeemToken() Token--> Error:"+e.getMessage());
		}
		IPSLogger.logDebug(_CLASS_NAME, "getRedeemToken - END");
		return ssoResponseObj;
	}

	public SSOWSResponseValue authenticateUserByLanName(String userName,String domain){
		IPSLogger.logDebug(_CLASS_NAME, "getRedeemToken - START");
		SSOWSResponseValue ssoResponseObj=null;
		SSOUserTokenValue ssoUserToken=null;
		Builder builder =null;
		Response response =null;
		String responseAuthString =null;
		String lanNameWithDomain=null;
		String jsonInput=null;
		try{			
			
			
			
			webServiceTarget=getWebserviceTarget(SSOConstants._SSO_REST_WS_END_POINT_URL+SSOConstants._SERVICE_AUTHENTICATE);
			 
			builder = webServiceTarget.request();
			lanNameWithDomain=domain+"\\\\"+userName;
			
			jsonInput=_APP_ID_USER_NAME_JSON + lanNameWithDomain + "\"}";
			
			//IPSLogger.logDebug(_CLASS_NAME, "Authenticate input:"+jsonInput);
			response = builder.post(Entity.json(jsonInput));
			responseAuthString = response.readEntity(String.class);
			//IPSLogger.logDebug(_CLASS_NAME, "Parsed Auth Response:"+responseAuthString);	
			
			ssoUserToken=new SSOUserTokenValue();
			ssoUserToken = (SSOUserTokenValue) IPSJSParser.readValue(responseAuthString, ssoUserToken,true); 			
			ssoResponseObj=this.getRedeemToken(ssoUserToken.userToken);						

		}catch(Exception e){
			e.printStackTrace();
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
		   //ssoInfoValueObj._authenDate=getDateFromSSOWSString(sSOWSResponseValue.webServiceLoginTime);
		}
		catch(Exception e){
			e.printStackTrace();
           IPSLogger.logError(_CLASS_NAME+"Exception: ", e);
		}
		return ssoInfoValueObj;
	}
	private Date getDateFromSSOWSString(String ssoDateString){
		Date dateVal=null;
		String timestampSeconds=null;
		try{
			//ssoDateString from JSON in this format "/Date(1531102692000-0400)\/"
			timestampSeconds=ssoDateString.substring(ssoDateString.indexOf("(")+1,ssoDateString.indexOf("-"));
			
		    dateVal=new Date(Long.parseLong(timestampSeconds));
		    
		   // IPSLogger.logError(_CLASS_NAME, "dateVal:"+new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS").format(dateVal) );
		}catch(Exception e){
			e.printStackTrace();
			IPSLogger.logError(_CLASS_NAME, "Error:"+e.getMessage());
		}
	    return dateVal;
	}

    private SSOInfoValue getSSOInfo(String userLanName,String _dataSourceName) {
		IPSLogger.logDebug(_CLASS_NAME, ".insertSSOInfo()- BEGIN");
		SSOInfoValue ssoInfoVO = new SSOInfoValue(); 
		try {
			ssoInfoVO = getSSOInfoRemote().getSSOInfo(userLanName, _dataSourceName);
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME+".insertSSOInfo(ssoInfoVO, _dataSourceName) Exception  ", e);
			e.printStackTrace();
		}
	    IPSLogger.logDebug(_CLASS_NAME,".insertSSOInfo() END");
		return ssoInfoVO;
	}
    
    private  WebTarget getWebserviceTarget(String webServiceURL) {
		IPSLogger.logDebug(_CLASS_NAME, "getWebserviceTarget() BEGIN WebService URL:"+webServiceURL);
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(webServiceURL);
		IPSLogger.logDebug(_CLASS_NAME, "getWebserviceTarget() END");
		return target;		
	}

}
