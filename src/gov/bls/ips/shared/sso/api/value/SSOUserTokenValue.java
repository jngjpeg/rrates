/*******************************************************************************
*
* FILE : SSOUserTokenValue.java
*
* REVISION HISTORY :
*
* Date         	Name            Release        Description
* -------  	-----------------   ----------     ---------------------------------
* 05/07/2018  Selva Sathiah    	Sprint6(Janus)   Original Version 
* 
* Copyright 2015 by All IPS-BLS .
* All rights reserved.
******************************************************************************/
package gov.bls.ips.shared.sso.api.value;

import gov.bls.ips.shared.framework.api.util.IPSValue;

public class SSOUserTokenValue extends IPSValue{
	private static final long serialVersionUID = 1L;
	public String userToken;
	public String siteRedirect;
}
