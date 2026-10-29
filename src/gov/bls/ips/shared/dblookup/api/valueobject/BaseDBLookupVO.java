/******************************************************************************
*
* FILE : BaseDBLookupVO.java
*
* DESCRIPTION :
*    This is the value object for BaseDBLookupVO to hold all the reference data.
* 
* 	This may be replaced with IPS Shared classes.
*
* REVISION HISTORY :
*
* Date           Name             Release              Description
* -------   -----------------    ----------     ---------------------------------
* 19Mar2014		Ranga Gorthi							Initial Revision - Taken from FI Code base 
* 23Nov2014		Ranga Gorthi	Sprint 12		Added Electronic Checklist Reference Data
* 08Dec2014		Ranga Gorthi					Refactor - Modified Country and State variable names specific to
* 															Electronic Checklist
* 19May2015		Sylvia Isidore  Sprint 4		Add ref data for Comp Pricing
* 09Feb2017		Kamran Anwar	ICR(Sprint34)	Modified to add _ACTIVE_TERMS_OF_TRAN_CODE_LIST attribute
* 09/27/2024	Vijay Sathiya	 ICR Sep2024	Added  _ALL_CURRENCY_LIST
*  
* Copyright 2014 by All IPS-BLS .
* All rights reserved.
******************************************************************************/

package gov.bls.ips.shared.dblookup.api.valueobject;

import java.util.ArrayList;

import gov.bls.ips.shared.framework.api.util.IPSValue;

public class BaseDBLookupVO extends IPSValue {

	private static final long serialVersionUID = 1L;

	public Long _dbLookupValueID=new Long(1); 
	
	public ArrayList<Object> _ALL_FORIEGN_COUNTRY_INFO_LIST = 
		new ArrayList<Object>();
	public ArrayList<Object> _ACTIVE_FORIEGN_COUNTRY_INFO_LIST = 
		new ArrayList<Object>();

	public ArrayList<RefInfoVO> _ALL_EC_STATE_INFO_LIST = 
		new ArrayList<RefInfoVO>();
	public ArrayList<RefInfoVO> _ACTIVE_EC_STATE_INFO_LIST = 
		new ArrayList<RefInfoVO>();
	
	public ArrayList<RefInfoVO> _ALL_CURRENCY_CODE_LIST = 
		new ArrayList<RefInfoVO>();
	public ArrayList<RefInfoVO> _ACTIVE_CURRENCY_CODE_LIST = 
		new ArrayList<RefInfoVO>();
	
	public ArrayList<RefInfoVO> _ALL_PRICE_BASIS_CODE_LIST = 
		new ArrayList<RefInfoVO>();
	public ArrayList<RefInfoVO> _ACTIVE_PRICE_BASIS_CODE_LIST = 
		new ArrayList<RefInfoVO>();
	
	public ArrayList<RefInfoVO> _ALL_UNIT_CODE_LIST = 
		new ArrayList<RefInfoVO>();
	public ArrayList<RefInfoVO> _ACTIVE_UNIT_CODE_LIST = 
		new ArrayList<RefInfoVO>();

	public ArrayList<RefInfoVO> _ALL_REPRICING_CYCLE_CODE_LIST = 
		new ArrayList<RefInfoVO>();
	public ArrayList<RefInfoVO> _ACTIVE_REPRICING_CYCLE_CODE_LIST = 
		new ArrayList<RefInfoVO>();
	
	public ArrayList<RefInfoVO> _ALL_TERMS_OF_TRAN_CODE_LIST = 
			new ArrayList<RefInfoVO>();
		public ArrayList<RefInfoVO> _ACTIVE_TERMS_OF_TRAN_CODE_LIST = 
			new ArrayList<RefInfoVO>();
	
	//Comp pricing
	
	public ArrayList<RefInfoVO> _PPI_ADJ_TYPE_LIST = 
			new ArrayList<RefInfoVO>();
	public ArrayList<RefInfoVO> _IPP_ADJ_TYPE_LIST = 
			new ArrayList<RefInfoVO>();
	
	public ArrayList<RefInfoVO> adjTypeList = 
			new ArrayList<RefInfoVO>();
	public ArrayList<RefInfoVO> _VALUE_TYPE_LIST = 
			new ArrayList<RefInfoVO>();
	public ArrayList<RefInfoVO> _CURRENCY_LIST = 
			new ArrayList<RefInfoVO>();
	public ArrayList<RefInfoVO> _ALL_CURRENCY_LIST = 
			new ArrayList<RefInfoVO>();
	public ArrayList<RefInfoVO> _COLUMNAR_DATA_FORMAT_LIST = 
			new ArrayList<RefInfoVO>();
	public ArrayList<RefInfoVO> _CELL_ALIGNMENT_LIST = 
			new ArrayList<RefInfoVO>();
	
	public ArrayList _CP_FIELD_DATASCREENING_LIST = 
			new ArrayList();
	
	public BaseDBLookupVO() throws Exception{
	}

}
