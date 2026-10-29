/*******************************************************************************
*
* FILE : SSOConstants.java
*
*
*  REVISION HISTORY :
*
* Date         	Name            	Release              Description
* -------  	-----------------   	----------     ---------------------------------
* 09/17/2015   Sirisha Kommi	   	Sprint 17   		Original Version
* 10/19/2015   Sirisha Kommi		Sprint 18   		Added 'token save or not' based on property
* 10/21/2015   Arun Jayavel		    Sprint 18   		Added '_SSO_WEB_SERVICE_ERROR_MSG' 
* 03/30/2016   Selva Sathiah		Sprint 23   		Added '_AUTHORIZED_USER_LAN_NAME'
* 07/05/2018 	Vijay Sathiya   	ICR Sprint51   		Added _SSO_AUTHENTICATION_REST_YN,_SSO_REST_WS_END_POINT_URL
* 06/17/2020   Vijay Sathiya   		ICR V1 Sprint28   	Added _SESSION_KEY, _SESSION_ID
* 01/06/2022   Ram Velkur           IPS6.0              Commented out _SSO_WS_END_POINT_URL.
* 01/19/2022   Ram Velkur           IPS6.0              Commented out _SSO_AUTHENTICATION_REST_YN.
* Copyright 2015 by All IPS-BLS .
* All rights reserved.
******************************************************************************/
package gov.bls.ips.shared.sso.api.util;

import gov.bls.ips.shared.framework.api.util.IPSValue;

import java.util.ResourceBundle;

public class SSOConstants extends IPSValue {
	
	private static final long serialVersionUID = 1L;
	//Constant for properties bundle name 
    public static final String _BUNDLE_NAME = "gov.bls.ips.shared.sso.api.util.sso_info";
    public static ResourceBundle _SSO_CONSTANTS_PROPERTIES = ResourceBundle.getBundle(SSOConstants._BUNDLE_NAME);

	public static final String SSOINFO_EJB_JNDI_NAME ="IPSSharedSSOInfoEJB#gov.bls.ips.shared.sso.api.interfaces.SSOInfoRemote";
	public static final String _ACTION_TYPE_GET_SSO_INFO="GET_SSO_INFO";
	public static final String _ACTION_TYPE_SET_SSO_INFO="SET_SSO_INFO";
 	
	public static String _ACTION_TYPE = "ACTION_TYPE";
	
	public static String _LAN_NAME="LAN_NAME";
	public static String _ERROR_MESSAGE="ERROR_MESSAGE";
	
	public static String _AUTHORIZED_USER_LAN_NAME="AUTHORIZED_USER_LAN_NAME";
	public static String _SERVICE_REDEEM_TOKEN="/RedeemToken"; 
	public static String _SERVICE_AUTHENTICATE="/Authenticate"; 

    // sso_info properties
	public static String _SSO_TOKEN_EXPIRY_TIME = _SSO_CONSTANTS_PROPERTIES.getString("SSO_TOKEN_EXPIRY_TIME");
	public static String _SSO_DATA_SOURCE = _SSO_CONSTANTS_PROPERTIES.getString("SSO_DATA_SOURCE");
	public static String _SSO_DEFAULT_DATA_SOURCE = _SSO_CONSTANTS_PROPERTIES.getString("SSO_DEFAULT_DATA_SOURCE");
	public static String _ACTION_TYPE_AUTHENTICATE_USER = "AuthenticateUser";
	public static String _INSERT_SSO_INFO_IN_DB_YN = _SSO_CONSTANTS_PROPERTIES.getString("INSERT_SSO_INFO_IN_DB_YN");

	public static String _SSO_AUTHENTICATION_REDIRECT_URL = _SSO_CONSTANTS_PROPERTIES.getString("SSO_AUTHENTICATION_REDIRECT_URL");
	public static String _APP_ID = _SSO_CONSTANTS_PROPERTIES.getString("APP_ID");
	//public static String _SSO_WS_END_POINT_URL=_SSO_CONSTANTS_PROPERTIES.getString("SSO_WS_END_POINT_URL");
	public static String _SSO_REST_WS_END_POINT_URL=_SSO_CONSTANTS_PROPERTIES.getString("SSO_REST_WS_END_POINT_URL");
	public static String _SSO_AUTHENTICATION_SUCCESS_RESPONSE_CODE= _SSO_CONSTANTS_PROPERTIES.getString("SSO_AUTHENTICATION_SUCCESS_RESPONSE_CODE");
	public static String _SSO_AUTHENTICATION_FAILURE_RESPONSE_CODE = _SSO_CONSTANTS_PROPERTIES.getString("SSO_AUTHENTICATION_FAILURE_RESPONSE_CODE");
	
	public static String _SSO_AUTHENTICATION_TOKEN_PARAM_NAME = _SSO_CONSTANTS_PROPERTIES.getString("SSO_AUTHENTICATION_TOKEN_PARAM_NAME");
	public static String _SSO_RESPONSE_CODE_PARAM_NAME = _SSO_CONSTANTS_PROPERTIES.getString("SSO_RESPONSE_CODE_PARAM_NAME");
	
	public static String _SSO_AUTHENTICATION_SUCCESS_RESPONSE_REDIRECT_URL = _SSO_CONSTANTS_PROPERTIES.getString("SSO_AUTHENTICATION_SUCCESS_RESPONSE_REDIRECT_URL");
		
	public static String _AUTHORIZATION_SUCCESS_URL = _SSO_CONSTANTS_PROPERTIES.getString("AUTHORIZATION_SUCCESS_URL");
	public static String _AUTHORIZATION_ERROR_URL = _SSO_CONSTANTS_PROPERTIES.getString("AUTHORIZATION_ERROR_URL");
	public static String _SSO_WEB_SERVICE_ERROR_MSG = _SSO_CONSTANTS_PROPERTIES.getString("SSO_WEB_SERVICE_ERROR_MSG");	
	//public static String _SSO_AUTHENTICATION_REST_YN=_SSO_CONSTANTS_PROPERTIES.getString("SSO_AUTHENTICATION_REST_YN");
	
	public static final String _SESSION_KEY="SESSION_KEY";
    public static final String _SESSION_ID="SESSION_ID";
	
	

}
