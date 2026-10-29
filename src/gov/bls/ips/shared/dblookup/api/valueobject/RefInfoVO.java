/******************************************************************************
*
* FILE : RefInfoVO.java
*
* DESCRIPTION :
*    This is the value object template to represent reference data.
* 
*
* REVISION HISTORY :
*
* Date           Name             Release              Description
* -------   -----------------    ----------     ---------------------------------
* 19Mar2014		Ranga Gorthi					Initial Revision - Taken from NOIPreInit Code base
* 19May2015		Sylvia Isidore  Sprint 4		Add ref data for Comp Pricing
* 20180424		Jeff Bricker  	Beta13			Added IPSValue as an object for each RefInfoVO.  Not required, use when needed.
* 
*  
* Copyright 2018 by All IPS-BLS .
* All rights reserved.
******************************************************************************/

package gov.bls.ips.shared.dblookup.api.valueobject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import gov.bls.ips.shared.framework.api.util.IPSConstants;
import gov.bls.ips.shared.framework.api.util.IPSValue;

public class RefInfoVO extends IPSValue {

	private static final long serialVersionUID = 1L;
	
	public String _code;
	public String _desc;
	public Object _object;
	public String _activeYN;
	
	public boolean isActive(){
		return StringUtils.equalsIgnoreCase(IPSConstants._YES,this._activeYN);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof RefInfoVO) {
			return StringUtils.equalsIgnoreCase(this._code, ((RefInfoVO)obj)._code);
		}
		return false;
	}
	
	@Override
	public int hashCode() {		
		int result = 1;
		result = prime * result
		+ ((_code == null) ? 0 : _code.hashCode());
		result = prime * result + ((_desc == null) ? 0 : _desc.hashCode());
		result = prime * result
		+ ((_activeYN == null) ? 0 : _activeYN.hashCode());
		return result;		
	}
}
