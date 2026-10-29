/*******************************************************************************
 *
 * FILE : CommandIF.java
 *
 *
 *  REVISION HISTORY :
 *
 * Date         	Name            Release              Description
 * -------  	-----------------   ----------     ---------------------------------
 * 05/25/16  	Vijay Sathiya                            	original version
 * 
 *
 * Copyright 2013 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/

package gov.bls.ips.shared.framework.api.interfaces;
 
import gov.bls.ips.shared.framework.api.util.IPSValue;

import java.util.HashMap;

public interface CommandIF {
	 
	public HashMap<String, Object> execute(IPSValue ipsRequest) throws Exception;
}
