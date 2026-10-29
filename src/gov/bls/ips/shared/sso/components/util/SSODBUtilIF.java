/*******************************************************************************
*
* FILE : SSODBUtilIF.java
*
* DESCRIPTION :
*
* REVISION HISTORY :
*
* Date         	Name            	Release             Description
* -------  		-----------------   ----------     		---------------------------------
* 09/17/2015  	Sirisha Kommi                   		original version
* 10/19/2015    Sirisha Kommi		Sprint 18   		SEQ Name change 
* 												
*  Copyright 2013 by All IPS-BLS .
* All rights reserved.
******************************************************************************/
//package name
package gov.bls.ips.shared.sso.components.util;

import java.util.ResourceBundle;
public interface SSODBUtilIF{
  //Constant for properties bundle name
  public static final String _BUNDLE_NAME =
	           "gov.bls.ips.shared.sso.components.util.SSODBUtil";
  public static ResourceBundle _DB_UTIL_PROPERTIES =
	            ResourceBundle.getBundle(SSODBUtilIF._BUNDLE_NAME);
  
  public static String _SSO_INFO_SID_SEQUENCE_NAME="SEQ_SSO_INFO";
  
  public static String _NEXT_SSO_INFO_SID_SQL = 
		  _DB_UTIL_PROPERTIES.getString("NEXT_SSO_INFO_SID_SQL");
}
