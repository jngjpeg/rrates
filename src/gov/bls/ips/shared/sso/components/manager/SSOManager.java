/*******************************************************************************
 *
 * FILE : SSOManager.java
 *
 * DESCRIPTION :
 *
 *  REVISION HISTORY :
 *
 * Date         	Name            Release              Description
 * -------  	-----------------   ----------     ---------------------------------
 * 09/17/2015  Sirisha Kommi    	Sprint 17   			Original Version
 * 06/17/2020   Vijay Sathiya   	ICR New Sprint28   	Added getUserLanNameFromSession() 
 * Copyright 2015 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.sso.components.manager;

import gov.bls.ips.shared.sso.components.dao.SSOInfoDAO;
import gov.bls.ips.shared.sso.components.ejbs.SSOInfoEJB;
import gov.bls.ips.shared.sso.api.value.SSOInfoValue;
import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.framework.api.util.IPSValue;
import gov.bls.ips.shared.logger.components.IPSLogger;

public class SSOManager {
	public static String _CLASS_NAME = ClassFromStatic.getCurrentClass().getName();

	public void insertSSOInfo(SSOInfoValue ssoInfoVO, String _dataSourceName)
			throws Exception {
		IPSLogger.logDebug(SSOManager._CLASS_NAME, ".insertSSOInfo() BEGIN");
		SSOInfoDAO<IPSValue> ssoInfoDAO = null;

		try {
			ssoInfoDAO = new SSOInfoDAO<IPSValue>(_dataSourceName);
			ssoInfoDAO.insertSSOInfo(ssoInfoVO);
		} catch (Exception e) {
			IPSLogger.logError(SSOManager._CLASS_NAME + ".insertSSOInfo() Exception  ", e);
			throw e;
		} finally {
			ssoInfoDAO = null;
		}
		IPSLogger.logDebug(SSOManager._CLASS_NAME, ".insertSSOInfo() END");

	}

	public SSOInfoValue getSSOInfo(String userLanName, String dataSourceName) throws Exception {
		IPSLogger.logDebug(SSOManager._CLASS_NAME, "getSSOInfo(userLanName,ds) BEGIN");
		SSOInfoDAO<IPSValue> ssoInfoDAO = null;
		SSOInfoValue ssoInfoVO = null;
		try {
			ssoInfoDAO = new SSOInfoDAO<IPSValue>(dataSourceName);
			ssoInfoVO = ssoInfoDAO.getSSOInfo(userLanName);
		} catch (Exception e) {
			IPSLogger.logError(SSOManager._CLASS_NAME + "getSSOInfo(userLanName,ds) Exception  ", e);
			throw e;
		} finally {
			ssoInfoDAO = null;
		}
		IPSLogger.logDebug(SSOManager._CLASS_NAME, "getSSOInfo(userLanName,ds) END");
		return ssoInfoVO;

	}
	
	public String getUserLanNameFromSession(String sessionID, String dataSourceName) throws Exception {
		IPSLogger.logDebug(SSOManager._CLASS_NAME, "getUserLanNameFromSession(sessionID,ds) BEGIN");
		IPSLogger.logDebug(SSOManager._CLASS_NAME ,"In getUserLanNameFromSession sessionID,ds are " + sessionID + " " + dataSourceName );
		SSOInfoDAO<IPSValue> ssoInfoDAO = null;
		String lanName= null;
		try {
			ssoInfoDAO = new SSOInfoDAO<IPSValue>(dataSourceName);
			lanName = ssoInfoDAO.getUserLanName(sessionID);
		} catch (Exception e) {
			IPSLogger.logError(SSOManager._CLASS_NAME + "getUserLanNameFromSession(userLanName,ds) Exception  ", e);
			throw e;
		} finally {
			ssoInfoDAO = null;
		}
		IPSLogger.logDebug(SSOManager._CLASS_NAME, "getUserLanNameFromSession(userLanName,ds) END");
		return lanName;

	}

	  
}
