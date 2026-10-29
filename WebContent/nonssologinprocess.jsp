<%@ page import="gov.bls.ips.shared.sso.api.util.SSOConstants" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="gov.bls.platform.log.LogFactory" %>
<%@ page import="gov.bls.platform.log.ILog" %>
<%@ page import="gov.bls.responseRates.properties.PropertyFactory" %>

<html>
<head>

<%
	ILog logger = LogFactory.getLogger();
	String enableNonSSOLogin = "false";
	String pageName = "nonssologinprocess.jsp";
	String lanName;
	String urlToForward = null;
	
	try {
		enableNonSSOLogin = PropertyFactory.getEnvInfo().getEnableNonSsoLogin();
		logger.info(pageName, "WARP - enableNonSSOLogin:" + enableNonSSOLogin);
		System.out.println("WARP - enableNonSSOLogin:" + enableNonSSOLogin);
	} catch (Exception e) {
		logger.warn(pageName, e.getMessage());
		e.printStackTrace();
	}
	
	if (enableNonSSOLogin.equalsIgnoreCase("true")) {
  		lanName=request.getParameter("lanName");
  		
  		if(StringUtils.isNotBlank(lanName)==true){
      		lanName=StringUtils.lowerCase(lanName);
      		request.getSession(true).setAttribute("LOGINNAME", lanName);
			urlToForward="/IPPMeasures/gui.html";
	  		logger.info(pageName, "LOGINNAME: " + lanName);
	  		logger.info(pageName, "urlToForward: " + urlToForward);
	  		System.out.println("LOGINNAME: " + lanName);
	  		System.out.println("urlToForward: " + urlToForward);
  		} else {
	  		logger.info(pageName, "Login id is empty");
      		System.out.println("Login id is empty");
  		}
	}else{
		urlToForward="/IPPMeasures/ssoenforcederror.jsp";
	}
	
  	if (urlToForward!=null) {
%>
		<script type="text/javascript">
			window.location.href = '<%=urlToForward%>';
		</script>

<%
}
 %>
</head>
<BODY>

</BODY>
</HTML>