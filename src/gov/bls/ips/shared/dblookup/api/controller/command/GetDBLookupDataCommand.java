package gov.bls.ips.shared.dblookup.api.controller.command;
import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.controller.util.AppRequestData;
import gov.bls.ips.shared.dblookup.api.controller.business.DBLookupDataBusinessDelegate;
import gov.bls.ips.shared.framework.api.interfaces.CommandIF;
import gov.bls.ips.shared.framework.api.util.IPSValue;
import gov.bls.ips.shared.logger.components.IPSLogger;

import java.util.HashMap;

public class GetDBLookupDataCommand implements CommandIF {
		
	private static final long serialVersionUID = 1L;
	public static String _CLASS_NAME=ClassFromStatic.getCurrentClass().getName();
	
	public HashMap<String, Object> execute(IPSValue initRequest) throws Exception{	
		
		
		IPSLogger.logDebug(_CLASS_NAME,".execute() BEGIN" );
		HashMap<String, Object> resultMap=new HashMap<String, Object>();
		try{ 
		  DBLookupDataBusinessDelegate dbLookupDataBusinessDelegate=new DBLookupDataBusinessDelegate();
		  resultMap=dbLookupDataBusinessDelegate.getLookupDataOnDemand(((AppRequestData)initRequest).ejbModuleName, ((AppRequestData)initRequest).ejbLinkName);				 		 
		}
		catch(Exception e){
			IPSLogger.logError(_CLASS_NAME+
					".execute() Exception  ",e); 
			throw new  Exception(e);
		}		
		IPSLogger.logDebug(_CLASS_NAME,".execute() END" );
		
		return resultMap;
	}	 
}
