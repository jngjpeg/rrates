/*******************************************************************************
 *
 * FILE : WriterIF.java
 *
 * DESCRIPTION :
 * Interface to hold common methods for the manager  
 * This interface is implemented by CRUDAdapter.java.
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
package gov.bls.ips.shared.framework.components.manager;

import java.util.List;

public interface WriterIF<T> {

	void create(T newVersion) throws Exception;
	void update(T newVersion, T prevVersion, List<T> changes) throws Exception;
	void delete(T prevVersion) throws Exception;
	void delete(Long key) throws Exception;
	void delete(Long key, String typeCode) throws Exception;
	void delete(Long key1, Long key2, String typeCode) throws Exception;
	
	void update(Long key1, Long key2) throws Exception;
	void create(Long key1, Long key2) throws Exception;
	void create(Long key1, Long key2, String typeCode) throws Exception;

}
