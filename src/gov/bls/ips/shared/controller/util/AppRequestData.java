/*******************************************************************************
*
* FILE : AppRequestData.java
*
* DESCRIPTION :
*
*
* REVISION HISTORY :
*
* Date         Name            Release             Description
* -------  -----------------   ----------     ---------------------------------
* 18May2016   Vijay Sathiya                           original version
* 10Oct2016	  Kamran Anwar		ICR(Sprint30)	Modified key type to String
* 07Nov2016	  Selva Sathiah		Admin(Sprint31)	Added varaiable initWorkloadDataValue.
* 11/21/16	  Vijay Sathiya		ICR(Sprint31)	Modified to add subSystemCode and taskCode
* 03/14/17	  Hari				ICR(Sprint35)	Modified to add programTypeCode
* 06/17/2020   Vijay Sathiya   ICR V1 Sprint28   		Added sessionKey,sessionID
*  Copyright 2016 by All IPS-BLS .
* All rights reserved.
******************************************************************************/
package gov.bls.ips.shared.controller.util;

import gov.bls.ips.shared.framework.api.util.IPSValue;

public class AppRequestData extends IPSValue {
 /**
	 * 
	 */
private static final long serialVersionUID = -8381826536023436588L;
 public String actionType;
 public String lanName;
 public String subSystemCode;
 public String ejbModuleName;
 public String ejbLinkName;
 public String dataSourceName;
 public Long echecklistEntrySid=(long) 0;
 public String sessionKey;
 public String sessionID;
 }