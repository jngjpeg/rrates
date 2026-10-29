<%@ page import="gov.bls.ips.shared.components.sso.RestAuthenticationRequestHandler" %>
<%@ page import="gov.bls.ips.shared.sso.api.util.SSOConstants" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="gov.bls.platform.log.LogFactory" %>
<%@ page import="gov.bls.platform.log.ILog" %>
<HTML>
<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">

<title>R-Rates</title>

<style>
#html, body {
  top: 0px;
  right: 0px;
  bottom: 0px;
  left: 0px;
  width:980px;
  height: 90%;
  margin: auto;
}
img { border-style: none; }
</style></head>

<%
  final String PAGE_NAME = "ssologin.jsp";

  ILog logger = LogFactory.getLogger();
	
  String actionTypeCode=(String)request.getAttribute(SSOConstants._ACTION_TYPE);
  if(StringUtils.isBlank(actionTypeCode)==true)
     actionTypeCode=SSOConstants._ACTION_TYPE_AUTHENTICATE_USER;
  String userToken=(String)request.getAttribute(SSOConstants._SSO_AUTHENTICATION_TOKEN_PARAM_NAME);
  String lanName=(String)request.getAttribute(SSOConstants._LAN_NAME);
  String errorMsg=(String)request.getAttribute(SSOConstants._ERROR_MESSAGE);
  logger.info(PAGE_NAME, "action type: " + actionTypeCode);
  logger.info(PAGE_NAME, "userToken: " + userToken); 
  logger.info(PAGE_NAME, "lan name: " + lanName);
  logger.info(PAGE_NAME, "error msg: " + errorMsg);
				  
  String urlToForward=null;
  if(SSOConstants._ACTION_TYPE_AUTHENTICATE_USER.equalsIgnoreCase(actionTypeCode)==true){
	RestAuthenticationRequestHandler ssoRequestHandler;
	ssoRequestHandler= new RestAuthenticationRequestHandler();
	request.setAttribute(SSOConstants._ACTION_TYPE,actionTypeCode);
	ssoRequestHandler.handleRequest(request, response);
  }
  else if(SSOConstants._SSO_AUTHENTICATION_SUCCESS_RESPONSE_CODE.equalsIgnoreCase(actionTypeCode)==true){
      if(StringUtils.isBlank(lanName)==false){
        lanName=StringUtils.lowerCase(lanName);
      }
      
      request.getSession(true).setAttribute("LOGINNAME", lanName);
	  urlToForward=SSOConstants._AUTHORIZATION_SUCCESS_URL;
  }
  else if(SSOConstants._SSO_AUTHENTICATION_FAILURE_RESPONSE_CODE.equalsIgnoreCase(actionTypeCode)==true){
    if(StringUtils.isNotBlank(lanName)==true){
      request.getSession(true).setAttribute("LOGINNAME", lanName);
      urlToForward=SSOConstants._AUTHORIZATION_ERROR_URL;
      }
    else
      urlToForward=SSOConstants._AUTHORIZATION_ERROR_URL;       
  }

  logger.info(PAGE_NAME, urlToForward);

  if(urlToForward!=null){
	  response.sendRedirect(urlToForward);
  }
%>

</head>
<BODY>
</BODY>
</HTML>