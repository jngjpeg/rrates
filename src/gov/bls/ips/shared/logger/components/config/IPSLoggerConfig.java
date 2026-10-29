/*******************************************************************************
*
* FILE : IPSConfig.java
*
* DESCRIPTION :
*
* REVISION HISTORY :
*
* Date         Name            Release             Description
* -------  -----------------   ----------     ---------------------------------
* 18Apr2011  Arun Jayavel                            original version
* 16Apr2020  Vijay Sathiya                   Log4j2 implementation. Added _LOGGER_CONFIG.
* 											 Removed _LOGGER_CONFIG_FILE, _DELAY
*  Copyright 2011 by All IPS-BLS .
* All rights reserved.
******************************************************************************/
//package name
package gov.bls.ips.shared.logger.components.config;


import java.util.ResourceBundle;

import gov.bls.ips.shared.components.util.ClassFromStatic;

public interface IPSLoggerConfig{
  public static final String _CLASS_NAME = ClassFromStatic.getCurrentClass()
                                           .getName();
  //Constant for properties bundle name
  public static final String _BUNDLE_NAME =
	           "gov.bls.ips.IPSConfig";
  public static ResourceBundle _IPS_CONFIG_PROPERTIES =
	            ResourceBundle.getBundle(IPSLoggerConfig._BUNDLE_NAME);
 //Constant for <<ApplicationName>>_LOGGER_CONFIG 
  public static final String _LOGGER_CONFIG="_LOGGER_CONFIG";
 /* public static String _LOGGER_CONFIG_FILE=
	  _IPS_CONFIG_PROPERTIES.getString("_LOGGER_CONFIG_FILE");
  public static String _DELAY=
	  _IPS_CONFIG_PROPERTIES.getString("_DELAY");*/
  public static String _RESOURCE_REF=
	  _IPS_CONFIG_PROPERTIES.getString("_RESOURCE_REF");
  public static String _DATE_FORMAT = 
		  _IPS_CONFIG_PROPERTIES.getString("_DATE_FORMAT");
}