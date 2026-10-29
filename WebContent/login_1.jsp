<%@ page import="gov.bls.platform.log.LogFactory" %>
<%@ page import="gov.bls.platform.log.ILog" %>
<%@ page import="gov.bls.responseRates.properties.PropertyFactory" %>
<html>
<head>
<title>Temporary Log In</title>
<style type="text/css">
.warning {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	font-weight: bold;
	color: #990000;	
}
</style>
<script>

function submitForm(){
   document.getElementById("lanName").value = document.getElementById("Login").value;
   loginForm.submit();
}

</script>


<%
	final String _FALSE = "false", _TRUE = "true", _PAGE_NAME = "login_1.jsp";

	ILog logger = LogFactory.getLogger();
	String enableNonSSOLogin = _FALSE;
	try {
		enableNonSSOLogin = PropertyFactory.getEnvInfo().getEnableNonSsoLogin();
		logger.info(_PAGE_NAME, "R-Rates - enableNonSSOLogin:" + enableNonSSOLogin);
	} catch (Exception e) {
		logger.warn(_PAGE_NAME, e.getMessage());
		e.printStackTrace();
	}
%>



<body>
	<%
		if (_TRUE.equalsIgnoreCase(enableNonSSOLogin)) {
			logger.info(_PAGE_NAME, "inside non-sso login_1.jsp");
	%>
		Login Name : <input  type="text" id="Login" value="" size="20">
			<button onclick="submitForm();">Submit</button>
	        <form id="loginForm"  method="POST" action="/IPPMeasures/nonssologinprocess.jsp">
	          <input  type="hidden" id="lanName" name="lanName" value="" size="20">
	          <input type="hidden" name="FromNonSsoPage" value="true">
	        </form>
    <% 
		} else {
    %>
			<div class="warning" style="padding-top: 50px; padding-left: 50px;">
				Non-SSO Login is Disabled. Please use SSO Login.
			</div>
	<%
		}		
    %>    
</body>
</html>
