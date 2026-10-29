/*******************************************************************************
 *
 * FILE : DBLookupDataDataEJBRemote.java
 *
 *
 *  REVISION HISTORY :
 *
 * Date         	Name            Release              Description
 * -------  	-----------------   ------------     ---------------------------------
 * 08/11/2016  	Kamran Anwar        ICR(Sprint28)        original version
 * 
 *
 * Copyright 2016 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/

package gov.bls.ips.shared.dblookup.api.interfaces;

import gov.bls.ips.shared.framework.api.util.IPSValue;

import javax.ejb.Remote;

@Remote
public interface DBLookupDataEJBRemote { 

		public IPSValue getLookupDataOnDemand() throws Exception;
		
}
