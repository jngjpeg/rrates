/*******************************************************************************
*
* FILE : SSOInfoValue.java
*
*  REVISION HISTORY :
*
* Date         	Name            Release        Description
* -------  	-----------------   ----------     ---------------------------------
* 09/17/2015  Sirisha Kommi    	Sprint 17   	Original Version
* 10/19/2015  Sirisha Kommi		Sprint 18   	column Name changes
* 09/27/2018  Vijay Sathiya		Sprint 53(ICR)  Commented toString()
* 06/24/2020  Vijay Sathiya   	ICR New Sprint28   	added _sessionId.removed _ssoToken
* Copyright 2015 by All IPS-BLS .
* All rights reserved.
******************************************************************************/
package gov.bls.ips.shared.sso.api.value;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

import gov.bls.ips.shared.framework.api.util.IPSValue;
import gov.bls.ips.shared.logger.components.IPSLogger;
import gov.bls.ips.shared.components.util.ClassFromStatic;

public class SSOInfoValue extends IPSValue{
	
	private static final long serialVersionUID = 1L;
	public static String _CLASS_NAME=ClassFromStatic.getCurrentClass().getName();

	public Long _ssoInfoSid;
	//public String _ssoToken;
	public Date _authenDate;
	public java.sql.Date _authenExpiryDate;
	public String _userLanName;
	public Long _userSid;
	public String _userIpAddr;
	public String _userEmailAddr;
	public String _ssoRelyingPartyURL;
	public Date _createdDate;
	public Date _updatedDate;
	public String _sessionId;
	/**
	 * Default constructor.
	 * @return None.
	 */
	public SSOInfoValue() {
	}
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
