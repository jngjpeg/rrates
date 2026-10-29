/*******************************************************************************
*
* FILE : SSOWSResponseValue.java
*
*
*  REVISION HISTORY :
*
* Date         	Name            Release              Description
* -------  	-----------------   ----------     ---------------------------------
* 10/01/2015  Selva Sathiah	   Sprint 18   			Original Version
* 09/27/2018  Vijay Sathiya	   Sprint 53(ICR)  Commented toString()
* Copyright 2015 by All IPS-BLS .
* All rights reserved.
******************************************************************************/
package gov.bls.ips.shared.sso.api.value;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.framework.api.util.IPSValue;
import gov.bls.ips.shared.logger.components.IPSLogger;


public class SSOWSResponseValue extends IPSValue{

	private static final long serialVersionUID = 1L;
	public static String _CLASS_NAME=ClassFromStatic.getCurrentClass().getName();
	public String webServiceLoginTime;
	public String displayName;
	public String distinguishedName;
	public String emailAddress;
	public String homeDirectory;
	public String lastLogon;
	public String name;
	public String middleName;
	public String surname;
	public String samAccountName;
	public String telephoneNumber;
	public String userPrincipalName;
	public String smartCardLogonRequired;
	public String enabled;
	public String siteRedirect;
	public String isAuthenticated;
	public String userToken;
	public String clientIpAddress;
/*
    public String toString(){
        String toStringValue=null;
  	  try{
  	      toStringValue=ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
        }
        catch(Exception e){
  		   IPSLogger.logError(_CLASS_NAME+".toString() Exception  ",e);
        }
  	  return toStringValue;
     }
*/
	}
