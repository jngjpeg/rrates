/*******************************************************************************
 *
 * FILE : IPSLogDebugInterceptor.java
 *
 * DESCRIPTION :
 *
 *
 * REVISION HISTORY :
 *
  Date           Name               Release             Description
 * -------    -----------------   ----------     ---------------------------------
 * 29thAug2011  Arun Jayavel                            original version
 *
 *  Copyright 2011 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.logger.components.interceptor;

import gov.bls.ips.shared.logger.components.IPSLogger;

import java.util.Arrays;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;


public class IPSLogDebugInterceptor {
  private static final String _BEGIN="\tBEGIN";
  private static final String _END="\tEND";
  @AroundInvoke       
  public Object log(InvocationContext ctx) throws Exception {
	Object returnObj=null;
	String className = null;
    String methodName = null;
	try{
	  className = ctx.getMethod().getDeclaringClass().getName();	
	  methodName = ctx.getMethod().getName();
	  IPSLogger.logDebug(className,methodName+IPSLogDebugInterceptor._BEGIN);
	  
	  if(ctx.getParameters()!=null)
	   IPSLogger.logDebug("Parameters=", Arrays.toString(ctx.getParameters()));
	  returnObj=ctx.proceed();

	  IPSLogger.logDebug(className,methodName+IPSLogDebugInterceptor._END);
	}
	catch(Exception e){
	 throw e;
	}
	finally{
      className = null;
      methodName = null;		
	}
	return returnObj;	
  }
}
