/*

Chris Elrod, Ed Jimenez, Lambert Tchaptchet-Ngamga
12/11/2008
CSci 252
Prof. Hasan

Project 4 - EJB

Abstract:

        One instance of a JDBCclass or one value accessible globally in an application. 

	The Service Locator is used to perform lookups to remote components.  It 
        centralizes the lookup process.  It hides the complexity of the lookups so the 
        objects that use it do not have to do the work.  It decouples the web tier from 
        more remotes components on the business tier.  Furthermore, the API for remote 
        objects can be complex and vary by vendor; therefore putting the logic in the 
        pattern helps wedge vendor specs.  It is implemented by having obtaining InitialContext 
        objects that access the component registry.  Look-ups are cached in the EJB container 
        to improve performance.  The client then accesses the service locator and not the registry.  
        Also, it handles the lookup details and exceptions. Examples would be looking up objects in 
        JNDI, RMI, JMS even a UDDI (outside the J2EE realm) if need be.  Combining a Singleton, 
        would ensure only one look-up object is obtained


	1) Example 7.8 	ServiceLocator.java: Implementing JDBC DataSource Service Locator Strategy
	   from source download of http://www.corej2eepatterns.com/Patterns2ndEd/index.htm

        2) In order to make sense, www.javacamp.org - Singleton Pattern
*/


package gov.bls.responseRates.service.dbAccess;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class JDBCServiceLocator {

    private InitialContext initialContext;
    private Map cache;
    private static JDBCServiceLocator _instance;

    //All instances see a static block.  It is called at load time.
    static {
        try {
            _instance = new JDBCServiceLocator();
        } catch (ServiceLocatorException se) {
            System.err.println(se);
            se.printStackTrace(System.err);
        }
    }


    //Singleton Pattern - clients have no access to the private constructor.   
    private JDBCServiceLocator() throws ServiceLocatorException {
        try {
            initialContext = new InitialContext();
            cache = Collections.synchronizedMap(new HashMap());

            //For Weblogic Production. 
          //  cache.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
           // cache.put(Context.PROVIDER_URL, "t3://mizar.psb.bls.gov:7140");
			
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    static public JDBCServiceLocator getInstance() {
        return _instance;
    }


    /*Access the Application Server's pre-pooled connections.*/
    public DataSource getDataSource(String dataSourceName) throws ServiceLocatorException{

        DataSource dataSource = null;
        try {
            if (cache.containsKey(dataSourceName)) {
                dataSource = (DataSource) cache.get(dataSourceName);
            } else {

                dataSource = (DataSource) initialContext.lookup(dataSourceName);
                cache.put(dataSourceName, dataSource );
            }
        } catch (NamingException nex) { 
            throw new ServiceLocatorException(nex);
        } 
          catch (Exception nex) {
            throw new ServiceLocatorException(nex);
        }

        return dataSource;

    }
    
}