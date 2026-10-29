/*******************************************************************************
 *
 * FILE : IPSConstants.java
 *
 * DESCRIPTION :
 * This interface holds constants.  
 * 
 * 
 *  REVISION HISTORY :
 *
 * Date         Name            Release              Description
 * -------  -----------------   ----------     ---------------------------------
 * 19Mar2014	Ranga Gorthi					Initial Revision - Taken from FI Code base
 * 
 * 19May2014	Kamran Anwar	Iter14(Sprint4)	Modified to add _NODE_ID constant
 * 
 * 20Apr2015	Kamran Anwar	Sprint12(FI)	Modified to add _THREE constant
 *
 *  Copyright 2014 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.framework.api.util;

public interface IPSConstants {
	
	
	final static String IPP_PROGRAM = "IPP";
	final static String PPI_PROGRAM = "PPI";
	
	public static final int _ZERO =0;
	public static final int _ONE =1;	
	public static final int _TWO =2;
	public static final int _THREE =3;
	
	public static String _YES="Y";
	public static String _NO="N";
	public static final String _BEGIN="\tBEGIN";
	public static final String _END="\tEND";
	public static final String _DESKTOP_CLIENT="AIR";
	public static final String _ENV_REQ_PARAMETER_NAME="client";
	public static final String _XML_CONTENT_TYPE="text/xml";
	
	public static final String _RTMP_PROTOCOL="rtmp://";
	public static final String _RTMP_HOST_PORT_SEP=":";
	public static final String _RTMP_SERVER_NAME="rtmp.server.name";
	public static final String _RTMP_PORT="rtmp.port";
	
	public static final String _MBEAN_JMX_RUNTIME="java:comp/env/jmx/runtime";
	public static final String _RUNTIME_SERVER_MBEAN="com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean";
	public static final String _DOMAIN="Domain";
	public static final String _DOMAIN_CONFIG="DomainConfiguration";
	public static final String _SERVER="Server";
	public static final String _SERVER_NAME="ServerName";
	public static final String _NODE_ID="NODE_ID";
	
	
}
