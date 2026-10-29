/*******************************************************************************
 *
 * FILE : IPSServiceLocator.java
 *
 * DESCRIPTION :
 *
 * REVISION HISTORY :
 *
 * Date         Name            Release             Description
 * -------  -----------------   ----------     ---------------------------------
 * 7thApr2013  Arun Jayavel     Iteration10        original version
 * 01APR2015    Vijay Sathiya	 Sprint-11(PostInit) Implemented getAPPContextName getLocalEJBByName
 *  Copyright 2011 by All IPS-BLS .
 * All rights reserved.
******************************************************************************/

package gov.bls.ips.shared.components.util;

import gov.bls.ips.shared.logger.components.IPSLogger;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

public class IPSServiceLocator {
  public static final String _CLASS_NAME = ClassFromStatic.getCurrentClass().getName();
  private static IPSServiceLocator _IPS_SERVICE_LOCATOR_INSTANCE = null;
  private static String _TRUE = "TRUE";
  private static  InitialContext _INITIAL_CONTEXT=null;
  private static ConcurrentHashMap<String,Object>  _JNDI_HASHMAP = new ConcurrentHashMap<String,Object>();

  //following attributes are used for non app server clients
  private static final String _INITIAL_CONTEXT_FACTORY_VALUE="weblogic.jndi.WLInitialContextFactory";
  private static final String _PROVIDER_URL_VALUE="t3://localhost:7001";
  //private static final String _SECURITY_PRINCIPAL_VALUE="weblogic";
  //private static final String _SECURITY_CREDE_VALUE="weblogic1";  
  
  private static final String _NAMING_CONTEXT_APP_DIR = "java:/app";
  private static final String _NAMING_CONTEXT_APPNAME = "java:app/AppName";
  
  //used to hold references to EJBHomes/JMS Resources for re-use
  private  IPSServiceLocator() throws Exception{
    IPSLogger.logDebug(_CLASS_NAME,"DBConnectionFactory() BEGIN");
    init();
    IPSLogger.logDebug(_CLASS_NAME,"DBConnectionFactory() END");
  }

  //following operation is used for non app server clients
  /*
  final public static void initExternalClient(){
	IPSLogger.logDebug(_CLASS_NAME,"initExternalClient() BEGIN");
	try{
	  Hashtable<String, String> hashTableObj = new Hashtable<String, String>();
	  hashTableObj.put(Context.INITIAL_CONTEXT_FACTORY, _INITIAL_CONTEXT_FACTORY_VALUE);
	  hashTableObj.put(Context.PROVIDER_URL, _PROVIDER_URL_VALUE);
	  hashTableObj.put(Context.SECURITY_PRINCIPAL, _SECURITY_PRINCIPAL_VALUE);
	  hashTableObj.put(Context.SECURITY_CREDENTIALS, _SECURITY_CREDE_VALUE);
	  IPSServiceLocator._INITIAL_CONTEXT = new InitialContext(hashTableObj);
	}
	catch(Exception e){
      IPSLogger.logError(_CLASS_NAME+".initExternalClient() got an Exception",e);
	}
	IPSLogger.logDebug(_CLASS_NAME,"initExternalClient() END");
  }
  */

  final private static void init(){
    IPSLogger.logDebug(_CLASS_NAME,"init() BEGIN");
	try{
      IPSServiceLocator._INITIAL_CONTEXT = new InitialContext();
	}
    catch(Exception e){
      IPSLogger.logError(_CLASS_NAME+".init() got an Exception",e);
	}
    IPSLogger.logDebug(_CLASS_NAME,"init() END");
  }
  /**
   *	Get the Singleton instance
   */
  public static  IPSServiceLocator  getInstance() throws Exception{
    IPSLogger.logDebug(_CLASS_NAME,"getInstance() BEGIN");
    try{
      // if already initialized return reference to instance
      if (IPSServiceLocator._IPS_SERVICE_LOCATOR_INSTANCE != null){
        return  IPSServiceLocator._IPS_SERVICE_LOCATOR_INSTANCE;
      }
      synchronized (IPSServiceLocator._TRUE) {
        if(IPSServiceLocator._IPS_SERVICE_LOCATOR_INSTANCE==null)
          IPSServiceLocator._IPS_SERVICE_LOCATOR_INSTANCE = new IPSServiceLocator();
      }
    }
    catch (Exception  e){
      IPSLogger.logError(_CLASS_NAME+".getInstance() got an Exception",e);
      throw  e;
    }
	IPSLogger.logDebug(_CLASS_NAME,"getInstance() END");
    return  IPSServiceLocator._IPS_SERVICE_LOCATOR_INSTANCE;
  }

  /**
   *	Get the Singleton instance
   */
  public static  Object  getForeignEJB(String foreignEjBJndiName) throws Exception{
    IPSLogger.logDebug(_CLASS_NAME,"getForeignEJB() BEGIN");
	Context context = null;
	Object foreignEJB=null;
    try{
      context=new InitialContext();
      if(StringUtils.isNotBlank(foreignEjBJndiName))
        foreignEJB=context.lookup(foreignEjBJndiName);
    }
    catch (NamingException  ne){
      IPSLogger.logError(_CLASS_NAME+".getForeignEJB() got an Naming Exception",ne);
      throw  new Exception (ne.getMessage());
    }
    catch(Exception e){
      IPSLogger.logError(_CLASS_NAME+".getForeignEJB() got an Exception",e);
      throw  new Exception (e);
    }
    finally{
      try{
    	  if (context != null)
    		  context.close();
      }
      catch(Exception e){
      }
    }
	IPSLogger.logDebug(_CLASS_NAME,"getForeignEJB() END");
    return  foreignEJB;
  }


  public static Object getEJBByJNDIName(String jndiName) throws Exception {
    IPSLogger.logDebug(_CLASS_NAME,"getEJBByJNDIName() BEGIN");
	Object returnObj= null;
    try{
      IPSServiceLocator.getInstance();
      returnObj=IPSServiceLocator._JNDI_HASHMAP.get(jndiName);
      if(returnObj==null){
    	returnObj= (Object)IPSServiceLocator._INITIAL_CONTEXT.lookup(jndiName);
    	IPSServiceLocator._JNDI_HASHMAP.put(jndiName, returnObj);
      }
	}
	catch(NamingException ne){
      IPSLogger.logError(_CLASS_NAME+".getEJBByJNDIName() got an Exception",ne);
      throw ne;
	}
	catch(Exception e){
      IPSLogger.logError(_CLASS_NAME+".getEJBByJNDIName() got an Exception",e);
	  throw e;
    }

    IPSLogger.logDebug(_CLASS_NAME,"getEJBByJNDIName() END");
    return returnObj;
  }
  
  /**
   * Gets a fresh EJB from the container pool.  Unlike getEJBByJNDIName method above.
   * @param moduleName
   * @param ejbName
   * @return Fresh instance of the EJB from the container's pool.
   * @throws Exception
   */
  public static Object getEJBInstanceByJNDIName(String jndiName) throws Exception {
	  IPSLogger.logDebug(_CLASS_NAME,"getEJBInstanceByJNDIName() BEGIN"+jndiName);
	  Object returnObj= null;
	  try{
		  IPSServiceLocator.getInstance();
	      returnObj= (Object)IPSServiceLocator._INITIAL_CONTEXT.lookup(jndiName);
	  }
	  catch(NamingException ne){
		  IPSLogger.logError(_CLASS_NAME+".getEJBInstanceByJNDIName() got an Exception",ne);
		  throw ne;
	  }
	  catch(Exception e){
		  IPSLogger.logError(_CLASS_NAME+".getEJBInstanceByJNDIName() got an Exception",e);
		  throw e;
	  }

	  IPSLogger.logDebug(_CLASS_NAME,"getEJBInstanceByJNDIName() END");
	  return returnObj;
  }
  
  public static Object getLocalEJBByName(String moduleName,String ejbName) throws Exception {
	    IPSLogger.logDebug(_CLASS_NAME,"getLocalEJBByName() BEGIN"+ejbName);
		Object returnObj= null;
		String localJndiName=null;
	    try{
	      IPSServiceLocator.getInstance();
	      localJndiName= _NAMING_CONTEXT_APP_DIR+"/"+moduleName+"/"+ejbName;
	      returnObj=IPSServiceLocator._JNDI_HASHMAP.get(localJndiName);
	      if(returnObj==null){
	    	returnObj= (Object)IPSServiceLocator._INITIAL_CONTEXT.lookup(localJndiName);
	    	IPSServiceLocator._JNDI_HASHMAP.put(localJndiName, returnObj);
	      }
		}
		catch(NamingException ne){
	      IPSLogger.logError(_CLASS_NAME+".getLocalEJBByName() got an Exception",ne);
	      throw ne;
		}
		catch(Exception e){
	      IPSLogger.logError(_CLASS_NAME+".getLocalEJBByName() got an Exception",e);
		  throw e;
	    }

	    IPSLogger.logDebug(_CLASS_NAME,"getLocalEJBByName() END");
	    return returnObj;
  }
  
  /**
   * Gets a fresh EJB from the container pool.  Unlike getLocalEJBByName method above.
   * @param moduleName
   * @param ejbName
   * @return Fresh instance of the EJB from the container's pool.
   * @throws Exception
   */
  public static Object getLocalEJBInstanceByName(String moduleName,String ejbName) throws Exception {
	  IPSLogger.logDebug(_CLASS_NAME,"getLocalEJBInstanceByName() BEGIN"+ejbName);
	  Object returnObj= null;
	  String localJndiName=null;
	  try{
		  IPSServiceLocator.getInstance();
		  localJndiName= _NAMING_CONTEXT_APP_DIR+"/"+moduleName+"/"+ejbName;
		  returnObj= (Object)IPSServiceLocator._INITIAL_CONTEXT.lookup(localJndiName);
	  }
	  catch(NamingException ne){
		  IPSLogger.logError(_CLASS_NAME+".getLocalEJBInstanceByName() got an Exception",ne);
		  throw ne;
	  }
	  catch(Exception e){
		  IPSLogger.logError(_CLASS_NAME+".getLocalEJBInstanceByName() got an Exception",e);
		  throw e;
	  }

	  IPSLogger.logDebug(_CLASS_NAME,"getLocalEJBInstanceByName() END");
	  return returnObj;
  }


  /*getAPPContextName() returns deployed Application Name*/
  public static String getAPPContextName() throws Exception {
	    IPSLogger.logDebug(_CLASS_NAME,"getAPPContextName() BEGIN");		
		String appName=null;
	    try{
	      IPSServiceLocator.getInstance();
	      appName=(String)IPSServiceLocator._JNDI_HASHMAP.get(_NAMING_CONTEXT_APPNAME);
	      if(appName==null){
	    	  appName= (String)IPSServiceLocator._INITIAL_CONTEXT.lookup(_NAMING_CONTEXT_APPNAME);
	    	IPSServiceLocator._JNDI_HASHMAP.put(_NAMING_CONTEXT_APPNAME, appName);
	      }
		}
		catch(NamingException ne){
	      IPSLogger.logError(_CLASS_NAME+".getAPPContextName() got an Exception",ne);
	      throw ne;
		}
		catch(Exception e){
	      IPSLogger.logError(_CLASS_NAME+".getAPPContextName() got an Exception",e);
		  throw e;
	    }

	    IPSLogger.logDebug(_CLASS_NAME,"getAPPContextName() END");
	    return appName;
	  }
  
  public static Object getResourceByJNDIName(String jndiName) throws Exception {
    IPSLogger.logDebug(_CLASS_NAME,"getResourceByJNDIName() BEGIN");
	Object returnObj= null;
    try{
      IPSServiceLocator.getInstance();
      returnObj=IPSServiceLocator._JNDI_HASHMAP.get(jndiName);
      if(returnObj==null){
    	returnObj= (Object)IPSServiceLocator._INITIAL_CONTEXT.lookup(jndiName);
    	IPSServiceLocator._JNDI_HASHMAP.put(jndiName, returnObj);
      }
	}
	catch(NamingException ne){
      IPSLogger.logError(_CLASS_NAME+".getResourceByJNDIName() got an Exception",ne);
      throw ne;
	}
	catch(Exception e){
      IPSLogger.logError(_CLASS_NAME+".getResourceByJNDIName() got an Exception",e);
	  throw e;
    }

    IPSLogger.logDebug(_CLASS_NAME,"getResourceByJNDIName() END");
    return returnObj;
  }
}
