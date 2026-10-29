/*******************************************************************************
 *
 * FILE : DAORemoveException.java
 *
 * DESCRIPTION :
 *
 *
 * REVISION HISTORY :
 *
 * Date         Name               Release             Description
 * -------  -----------------   ----------     ---------------------------------
 * 10May2011  Arun Jayavel                            original version
 * 02Oct2015  Kamran Anwar		Sprint18(FI)	Modified to change Exception to RuntimeException
 * 02Oct2015  Kamran Anwar		Sprint18(FI)	Modified to implement constructors
 *
 *  Copyright 2011 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
//package declaration
package gov.bls.ips.shared.framework.components.dao.exception;

public class DAOSystemException extends RuntimeException{
  /**
	 * 
	 */
	private static final long serialVersionUID = -6416411767161353837L;

public DAOSystemException(String msg){
	super(msg);
  }

public DAOSystemException(Throwable cause)
{
	super(cause);
}

public DAOSystemException(String message, Throwable cause)
{
	super(message, cause);
}
}
