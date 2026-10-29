/*******************************************************************************
 *
 * FILE : CRUDAdapter.java
 *
 * DESCRIPTION :
 * Base implementation of ReaderIF and WriterIF for all managers  
 * All the managers need to extend this class.
 *
 *  REVISION HISTORY :
 *
 * Date         Name            Release              Description
 * -------  -----------------   ----------     	---------------------------------
 * 19Mar2014	Ranga Gorthi					Initial Revision - Taken from FI Code base
 * 05Dec2014	Kamran Anwar	Sprint8(FI)		Modified to refactor and make changes for integration with other teams
 * 09Apr2015	Dileep Soundararajan    Sprint17		Added a new method to implement locking objects feature 
 * 14Apr2015	Dileep Soundararajan	Sprint17		Removed the signature that uses UserProfileVO and added a new method signature to implement locking objects feature 
 * 14Apr2015	Jeff Bricker			Sprint17		Removed the signature and import that uses UserProfileVO  
 * 14Apr2015	Kamran Anwar			Sprint12(FI)	Modified to add getCount(String key) signature
 * 22Oct2015	Jeff Bricker			Sprint23(PI)	Added getList(Long key, String key1, String key2) for searching for checklists. 
 * 13Apr2018	Jeff Bricker			Beta13			Added getList(String key, String key1) for searching for BLSIndustry. 
 * 
 *
 *  Copyright 2014 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.framework.components.manager;

import java.util.List;

public class ReaderAdapter<T> implements IPSManagerConstants, ReaderIF<T>{

	@Override
	public T getItem(Long key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getItem(Long key1, Long key2) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getItem(Long key1, String key2) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getItem(Long key1, String key2, String key3) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getItem(Long key1, String key2, String key3, String key4)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getItem(String key1, String key2) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getItem(String key1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getList(T key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getList(Long key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getList(String key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<T> getList(String key, int key1, int key2) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getList(Long key, String type) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getList(Long key1, Long key2) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getList(Long key1, String key2, String key3) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getList(String key, String key1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getCount(String key) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public T getItem(Long key1, Long userSID, String userRole,
			boolean lockObject) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public T getItem(Long key1, Long userSID, String userRole) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
