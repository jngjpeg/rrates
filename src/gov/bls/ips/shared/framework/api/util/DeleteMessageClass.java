/*******************************************************************************
 *
 * FILE : DeleteMessageClass.java
 *
 * DESCRIPTION :
 * DeleteMessageClass that holds unique id and associated items that are to be deleted.
 * 
 *  REVISION HISTORY :
 *
 * Date         Name            Release              Description
 * -------  -----------------   ----------     ---------------------------------
 * 25Jul2015	Kamran Anwar	Sprint15(FI)		Initial Revision - 
 * 
 *
 *  Copyright 2015 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.framework.api.util;

import java.io.Serializable;

public class DeleteMessageClass implements Serializable{

	/**
	 * 
	 */
	public String _uid;
	public Object _item;
	private static final long serialVersionUID = 1L;
	protected static final int prime = 31;

}
