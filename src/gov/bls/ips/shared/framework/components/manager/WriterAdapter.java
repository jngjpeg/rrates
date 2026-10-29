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
 * 
 *
 *  Copyright 2014 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.framework.components.manager;

import java.util.List;

public class WriterAdapter<T> implements IPSManagerConstants, WriterIF<T> {

	@Override
	public void create(T newVersion) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(T newVersion, T prevVersion, List<T> changes)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(T prevVersion) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long key) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long key, String typeCode) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long key1, Long key2, String typeCode) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Long key1, Long key2) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Long key1, Long key2) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Long key1, Long key2, String typeCode) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
