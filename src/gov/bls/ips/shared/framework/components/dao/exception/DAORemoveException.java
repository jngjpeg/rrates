/*******************************************************************************
 *
 * FILE : DAORemoveException.java
 *
 * DESCRIPTION :
 *
 *
 * REVISION HISTORY :
 *
  Date         Name               Release             Description
 * -------  -----------------   ----------     ---------------------------------
 * 10May2011  Arun Jayavel                            original version
 * 02Oct2015  Kamran Anwar		Sprint18(FI)	Modified to change Exception to RuntimeException
 *  Copyright 2011 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
//package declaration
package gov.bls.ips.shared.framework.components.dao.exception;

public class DAORemoveException extends RuntimeException{
  /**
	 *
	 */
	private static final long serialVersionUID = -3617355112218684222L;

public DAORemoveException(String msg){
	super(msg);
  }
}

