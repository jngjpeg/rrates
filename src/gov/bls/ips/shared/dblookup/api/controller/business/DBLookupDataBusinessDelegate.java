
/*******************************************************************************
*
* FILE : DBLookupDataBusinessDelegate.java
*
*
*  REVISION HISTORY :
*
* Date         	Name            Release              Description
* -------  	-----------------   ----------     ---------------------------------
* 08/11/16  	Kamran Anwar    ICR(Sprint28) 	      original version
* 05/09/2017	Hari			ICR(Sprint37)	Modified to implement local interface instead of remote interface
* 05/22/2017	Hari			ICR(Sprint37)	Modified to add overloaded getLookupDataOnDemand() to accept parameters
* 
* Copyright 2016 by All IPS-BLS .
* All rights reserved.
******************************************************************************/

package gov.bls.ips.shared.dblookup.api.controller.business;
import gov.bls.ips.shared.dblookup.api.interfaces.DBLookupConstantsIF;
import gov.bls.ips.shared.dblookup.api.interfaces.DBLookupDataEJBLocal;
import gov.bls.ips.shared.dblookup.api.interfaces.DBLookupDataEJBRemote;
import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.components.util.IPSServiceLocator;
import gov.bls.ips.shared.framework.api.util.IPSValue;
import gov.bls.ips.shared.logger.components.IPSLogger;

import java.util.HashMap;

import javax.naming.NamingException;

public class DBLookupDataBusinessDelegate {

	 private static final long serialVersionUID = 1L;
	 public static String _CLASS_NAME=ClassFromStatic.getCurrentClass().getName();	 	
	  
	 public DBLookupDataBusinessDelegate() {	  
		 
	   }

	 /**
	  * getLookupDataOnDemand : method to get lookup data
	  * @param moduleName
	  * @param ejbLookupName
	  * @return
	  * @throws Exception
	  */
	public HashMap<String, Object> getLookupDataOnDemand(String moduleName, String ejbLookupName)throws Exception {
		IPSLogger.logDebug(_CLASS_NAME,".getLookupDataOnDemand() BEGIN : moduleName " + moduleName );
		DBLookupDataEJBLocal ejb = null;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();		
		IPSValue value = null;
		try {
			ejb = (DBLookupDataEJBLocal)IPSServiceLocator.getLocalEJBByName(moduleName,ejbLookupName);
		    value = ejb.getLookupDataOnDemand();
			IPSLogger.logDebug(_CLASS_NAME, "-DBLookupValueObject -" + value + ejb.toString());

		    resultMap.put(DBLookupConstantsIF._ACTION_TYPE_GET_DBLOOKUP_DATA, value); 		  }
		catch (NamingException ex) {		    
		    IPSLogger.logError(_CLASS_NAME, "getLookupDataOnDemand", ex);
		    throw new Exception("Exception in getLookupDataOnDemand"+ex.getMessage());
		}
		catch (Exception ex) {			
			 IPSLogger.logError(_CLASS_NAME, "getLookupDataOnDemand", ex);
			 throw new Exception("Exception in getLookupDataOnDemand"+ex.getMessage());
		}	 
		IPSLogger.logDebug(_CLASS_NAME,".getLookupDataOnDemand() END" );
		return resultMap; 
	}
}
