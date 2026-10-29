/*******************************************************************************
 *
 * FILE : MbeanUtility
 *
 * DESCRIPTION : Utility to read Application server information
 * Can have methods to read server name, domain name etc. 
 *
 * REVISION HISTORY :
 *
 * Date         Name            Release             Description
 * -------  -----------------   ----------     ---------------------------------
 * 19Mar2014  Ranga Gorthi     				        original version
 *
 *  Copyright 2014 by All IPS-BLS .
 * All rights reserved.
******************************************************************************/
package gov.bls.ips.shared.components.util;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.NamingException;

public enum MbeanUtility {

	INSTANCE;
	
	private String _MBEAN_JMX_RUNTIME="java:comp/env/jmx/runtime";
	private String _RUNTIME_SERVER_MBEAN="com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean";
	private String _DOMAIN="Domain";
	private String _DOMAIN_CONFIG="DomainConfiguration";
	private String _SERVER="Server";
	private String _SERVER_NAME="ServerName";
	
	private Object getMBeanServerAttribute(String type, String paramName) throws NamingException{
		try {
			javax.naming.InitialContext ctxt = new javax.naming.InitialContext();
			MBeanServer server = (MBeanServer)ctxt.lookup(_MBEAN_JMX_RUNTIME);
			ObjectName objName = new ObjectName(_RUNTIME_SERVER_MBEAN);
			if(type != null && type.equalsIgnoreCase(_DOMAIN)){
				objName = (ObjectName) server.getAttribute(objName, _DOMAIN_CONFIG);
			}
			ctxt.close();		
			return server.getAttribute(objName,paramName);
		}catch(Exception e) {
			e.printStackTrace();
			throw new NamingException(e.getMessage());
		}	
	}
	
	public  String getServerName() throws NamingException  { 
		return (String)getMBeanServerAttribute(_SERVER, _SERVER_NAME);
	}
		
}
