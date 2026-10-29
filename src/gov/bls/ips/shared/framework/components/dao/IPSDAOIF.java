/*******************************************************************************
 *
 * FILE : IPSDAOIF.java
 *
 * DESCRIPTION :
 * This is the base interface for all DAOs   
 *  
 *
 *  REVISION HISTORY :
 *
 * Date         Name            Release              Description
 * -------  -----------------   ----------     ---------------------------------
 * 19Mar2014	Ranga Gorthi					Initial Revision - Taken from FI Code base
 * 
 *
 *  Copyright 2014 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.framework.components.dao;

import gov.bls.ips.shared.framework.components.dao.exception.DAOCreateException;
import gov.bls.ips.shared.framework.components.dao.exception.DAOFinderException;
import gov.bls.ips.shared.framework.components.dao.exception.DAORemoveException;
import gov.bls.ips.shared.framework.components.dao.exception.DAOSystemException;

import java.util.List;

public interface IPSDAOIF<T>{

   public abstract int insertRecord(T ipsValue) throws DAOCreateException,DAOSystemException;

   public abstract boolean updateRecord(T ipsValue) throws DAOSystemException;

   public abstract boolean deleteRecord(T ipsValue) throws DAORemoveException,DAOSystemException;
   
   public abstract T findRecordByPrimaryKey(Object pKey) throws DAOFinderException,DAOSystemException;

   public abstract int insertBatch(List<T> list) throws DAOCreateException,DAOSystemException;

   public abstract int updateBatch(List<T> list) throws DAOSystemException;

   public abstract List<T> findAll(String condition) throws DAOFinderException,DAOSystemException;

}
