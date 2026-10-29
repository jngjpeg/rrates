/*******************************************************************************
 *
 * FILE : SSORequestData.java
 *
 * DESCRIPTION :
 *   
 *   
 * REVISION HISTORY :
 *
 * Date         Name            Release             Description
 * -------  -----------------   ----------     ---------------------------------
 * 09/21/2015  Sirisha Kommi                            original version
 *
 *  Copyright 2015 by All IPS-BLS .
 * All rights reserved.
******************************************************************************/
package gov.bls.ips.shared.components.sso.util;

import gov.bls.ips.shared.framework.api.util.IPSValue;
import gov.bls.ips.shared.sso.api.value.SSOInfoValue;

public class SSORequestData extends IPSValue {
  private static final long serialVersionUID = 1L;	
  public String actionType;
  public String lanName;
  public String datasourceName;
  public String email;
  public SSOInfoValue ssoInfoValue;
}
