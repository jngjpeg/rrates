/*******************************************************************************
*
* FILE : CommandFactoryIF.java
*
*
*  REVISION HISTORY :
*
* Date         	Name            Release              Description
* -------  	-----------------   ----------     ---------------------------------
* 09/08/16  	Vijay Sathiya                            	original version
* 
*
* Copyright 2013 by All IPS-BLS .
* All rights reserved.
******************************************************************************/

package gov.bls.ips.shared.framework.api.interfaces;

public interface CommandFactoryIF {
	 
	public  CommandIF getCommand(String className) throws Exception;
}
