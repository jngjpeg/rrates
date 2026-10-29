/*******************************************************************************
 *
 * FILE : SSOInfoIF.java
 *
 *
 *  REVISION HISTORY :
 *
 * Date         	Name            Release              Description
 * -------  	-----------------   ----------     ---------------------------------
 * 09/17/2015  Sirisha Kommi    	Sprint 17   			Original Version 
 * 06/17/2020   Vijay Sathiya   	ICR New Sprint28   	Added EJBException() for  insertSSOInfo
 * 06/17/2020   Vijay Sathiya   	ICR New Sprint28   	Added getUserLanNameFromSession()
 * Copyright 2015 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.sso.api.interfaces;


import javax.ejb.EJBException;

import gov.bls.ips.shared.sso.api.value.SSOInfoValue;

public interface SSOInfoIF {
	public void insertSSOInfo(SSOInfoValue ssoInfoVO, String _dataSourceName) throws EJBException;
	public SSOInfoValue getSSOInfo(String userLanName, String _dataSourceName) throws Exception;
	public String getUserLanNameFromSession(String sessionID, String dataSourceName) throws Exception ;
}
