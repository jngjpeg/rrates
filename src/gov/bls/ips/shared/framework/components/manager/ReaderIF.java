/*******************************************************************************
 *
 * FILE : ReaderIF.java
 *
 * DESCRIPTION :
 * Interface to hold common methods for the manager  
 * This interface is implemented by CRUDAdapter.java.
 * 
 * REVISION HISTORY :
 *
 * Date         Name            Release              Description
 * -------  -----------------   ----------     ---------------------------------
 * 19Mar2014	Ranga Gorthi					Initial Revision - Taken from FI Code base
 * 05Dec2014	Kamran Anwar	Sprint8(FI)		Modified to refactor and make changes for integration with other teams
 * 14Apr2015	Dileep Soundararajan	Sprint17		Removed the signature that uses UserProfileVO and added a new method signature to implement locking objects feature
 * 15Apr2015	Kamran anwar	Sprint12(FI)	Modified to add getCount method signature 
 * 29Apr2015	Jeff Bricker	Sprint18(PI)	Added getItem(long, long, string) 
 * 22Oct2015	Jeff Bricker	Sprint23(PI)	Added getList(Long key, String key1, String key2) for searching for checklists. 
 * 13Apr2018	Jeff Bricker	Beta13			Added getList(String key, String key1) for searching for BLSIndustry. 
 * 
 *
 * Copyright 2018 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.framework.components.manager;
import java.util.List;

public interface ReaderIF<T> {	
	T getItem(Long key) throws Exception;
	T getItem(Long key1, Long key2) throws Exception;
	T getItem(Long key1, String key2) throws Exception;
	T getItem(Long key1, String key2, String key3) throws Exception;
	T getItem(Long key1, String key2, String key3, String key4) throws Exception;
	T getItem(Long key1, Long userSID, String userRole, boolean lockObject) throws Exception;
	T getItem(Long key1, Long userSID, String userRole) throws Exception;
	T getItem(String key1, String key2) throws Exception;
	T getItem(String key1) throws Exception;
	int getCount(String key) throws Exception;
	List<T> getList() throws Exception;
	List<T> getList(T key) throws Exception;
	List<T> getList(Long key) throws Exception;
	List<T> getList(String key) throws Exception;
	List<T> getList(Long key, String type) throws Exception;
	List<T> getList(Long key1, Long key2) throws Exception;
	List<T> getList(String key, int key1, int key2) throws Exception;
	List<T> getList(Long key, String key1, String key2) throws Exception;
	List<T> getList(String key, String key1) throws Exception;

}
