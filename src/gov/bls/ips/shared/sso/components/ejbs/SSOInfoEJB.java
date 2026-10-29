/*******************************************************************************
 *
 * FILE : SSOInfoEJB.java
 *
 * DESCRIPTION :This is the 'Single Sign On' ejb that can be remotely invoked
 *
 *  REVISION HISTORY :
 *
 * Date         	Name            Release              Description
 * -------  	-----------------   ----------     ---------------------------------
 * 09/17/2015  Sirisha Kommi     	Sprint 17 			Original Version
 * 06/17/2020   Vijay Sathiya   	ICR New Sprint28   	Added getUserLanNameFromSession()
 * 05/05/2023	Ashok Yeddula		IPS7.0				Added annotation to enable call-by-reference for remote ejb. 
 * 
 * Copyright 2015 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.sso.components.ejbs;

import gov.bls.ips.shared.sso.api.interfaces.SSOInfoLocal;
import gov.bls.ips.shared.sso.api.interfaces.SSOInfoRemote;
import gov.bls.ips.shared.sso.components.manager.SSOManager;
import weblogic.javaee.CallByReference;
import gov.bls.ips.shared.sso.api.value.SSOInfoValue;

import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.logger.components.IPSLogger;

import javax.ejb.EJBException;
import javax.ejb.Stateless;

@Stateless(mappedName = "IPSSharedSSOInfoEJB")
@CallByReference
public class SSOInfoEJB implements SSOInfoRemote, SSOInfoLocal {
	public static String _CLASS_NAME = ClassFromStatic.getCurrentClass().getName();
	private static final String _BEGIN = "\tBEGIN";
	private static final String _END = "\tEND";

	public void insertSSOInfo(SSOInfoValue ssoInfoVO, String dataSourceName)
			throws EJBException {
		IPSLogger.logDebug(_CLASS_NAME + "insertSSOInfo(vo,ds)", _BEGIN);
		SSOManager ssoManager;
		try {
			ssoManager = new SSOManager();
			ssoManager.insertSSOInfo(ssoInfoVO, dataSourceName);
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME + ".insertSSOInfo(vo,ds) Exception  ", e);
			throw new EJBException(e);
		} finally {
			ssoManager = null;
		}
		IPSLogger.logDebug(SSOInfoEJB._CLASS_NAME + "insertSSOInfo(vo,ds)",	_END);
	}

	public SSOInfoValue getSSOInfo(String userLanName, String dataSourceName) throws Exception {
		IPSLogger.logDebug(_CLASS_NAME + "getSSOInfo(userLanName,ds)", _BEGIN);
		SSOManager ssoManager= null;
		SSOInfoValue ssoInfoVO= null;
		try {
			ssoManager = new SSOManager();
			IPSLogger.logDebug(SSOInfoEJB._CLASS_NAME ,"In EJB userLanName,ds are " + userLanName + " " +dataSourceName );
			ssoInfoVO = ssoManager.getSSOInfo(userLanName,dataSourceName);
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME + ".getSSOInfo(userLanName,ds) Exception  ", e);
			throw e;
		} finally {
			ssoManager = null;
		}
		IPSLogger.logDebug(SSOInfoEJB._CLASS_NAME + "getSSOInfo(userLanName,ds)", _END);
		return ssoInfoVO;
	}
	public String getUserLanNameFromSession(String sessionID, String dataSourceName) throws Exception {
		IPSLogger.logDebug(_CLASS_NAME + "getUserLanNameFromSession(userLanName,ds)", _BEGIN);
		SSOManager ssoManager= null;
		String lanName= null;
		try {
			ssoManager = new SSOManager();
			IPSLogger.logDebug(SSOInfoEJB._CLASS_NAME ,"In EJB sessionID,ds are " + sessionID + " " +dataSourceName );
			lanName = ssoManager.getUserLanNameFromSession(sessionID,dataSourceName);
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME + ".getUserLanNameFromSession(userLanName,ds) Exception  ", e);
			throw e;
		} finally {
			ssoManager = null;
		}
		IPSLogger.logDebug(SSOInfoEJB._CLASS_NAME + "getUserLanNameFromSession(userLanName,ds)", _END);
		return lanName;
	}

}
