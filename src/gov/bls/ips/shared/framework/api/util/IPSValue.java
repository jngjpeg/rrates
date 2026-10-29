/*******************************************************************************
 *
 * FILE : IPSValue.java
 *
 * DESCRIPTION :
 * Base VO for all the VOs in the project.  
 * All VOs required to extend this class to allow the framework classes to work
 * 
 *  REVISION HISTORY :
 *
 * Date         Name            Release              Description
 * -------  -----------------   ----------     ---------------------------------
 * 19Mar2014	Ranga Gorthi						Initial Revision - Taken from FI Code base
 * 8Jun2016 	Vijay Sathiya					 Added _isNew,_isupdate, _isDelete
 * 16Dec2016	Kamran Anwar	Sprint32(ICR)	Modified to replace loadViewKey, key attributes taken from InitValue
 * 05Feb2021	Ashok Yeddula	Beta2(Search)	Added _heapWarning variable.
 *
 *  Copyright 2014 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.framework.api.util;

import java.io.Serializable;

public class IPSValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final int prime = 31;
	public boolean _isNew=false;
	public boolean _isUpdate=false;
	public boolean _isDelete=false;
	public String loadViewKey;
	public boolean _heapWarning;
	public String key;

}
