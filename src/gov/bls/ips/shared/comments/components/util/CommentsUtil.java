/*******************************************************************************
*
* FILE : CommentsUtil.java
*
* DESCRIPTION :
*
*  REVISION HISTORY :
*
* Date         	Name            Release              Description
* -------  	-----------------   ----------     ---------------------------------
* 
* 10/07/2019  Udaya Allu       ICR V1 Sprint 20 New VersionOne (old 66):  Update Admin for ICR subsystem - remove copying roles for  FI/FOI   
* Copyright 2015 by All IPS-BLS .
* All rights reserved.
******************************************************************************/

package gov.bls.ips.shared.comments.components.util;

import gov.bls.ips.shared.comments.components.util.constants.*;

public class CommentsUtil {
	
	/****
	 * edits are allowed within the same program, same subsystem and same role
	 * exceptions are:
	 * 	1. item comments can be edited/deleted in same program, but different subsystem/role (post-init users can edit/delete field init item comments)
	 * 	2. item and reporter IA comments can be edited/deleted by post-init SR (IPP program only)
	 * 
	 * notes:
	 * 
	 * add -
	 * 	if su, add a new sid if diff prg, or diff subsystem or diff role 
	 *  if rptr, add a new sid if 
	 * 
	 * update - 
	 * 	if su, update only if same prg, subsystem, role 
	 * 	if rptr, update only if same prg, subsystem, role (except for ipp, if orig role was IA and new role is SR) 
	 * 	if item, if post-init, update if same prg and same role
	 * 			 if field init, update if same prg and orig prog was post-init
	 * 							update if same prg and orig prog was field init and same role (except for ipp, if orig role was IA and new role is SR) (  
	*****/
	public static boolean canAddToSameSID(String elementType, String programType, String origSubsystem, String newSubsystem, String origRoleCode, String newRoleCode) {
		
		boolean addtoSameSIDFlag = false;
		
		switch (elementType) {		
		
        case CommentElementTypeIF.ITEM :  
        	
        	if (SubsystemIF.ICR.equals(origSubsystem)) {
        		if (SubsystemIF.ICR.equals(newSubsystem)) {
        			if (RoleIF.FE.equals(origRoleCode) && RoleIF.FE.equals(newRoleCode)) {
        				addtoSameSIDFlag = true;
        			}
        		} 
			}
        	
            break;
            
        case CommentElementTypeIF.REPORTER :  
        	
        	if (SubsystemIF.ICR.equals(origSubsystem)) {
        		if (SubsystemIF.ICR.equals(newSubsystem)) {
        			if (RoleIF.FE.equals(origRoleCode) && RoleIF.FE.equals(newRoleCode)) {
        				addtoSameSIDFlag = true;
        			}
        		} 
        		
			} 
        	
        case CommentElementTypeIF.SURVEY_UNIT :  
        	
        	if (SubsystemIF.ICR.equals(origSubsystem)) {
        		if (SubsystemIF.ICR.equals(newSubsystem)) {
        			if (RoleIF.FE.equals(origRoleCode) && RoleIF.FE.equals(newRoleCode)) {
        				addtoSameSIDFlag = true;
        			}
        		} 
        		
			}
        	
            break;
		}
		
		return addtoSameSIDFlag;
	}

	
	public static boolean canEditSameSID(String elementType, String programType, String origSubsystem, String newSubsystem, String origRoleCode, String newRoleCode) {
		
		boolean editSameSIDFlag = false;
		
		switch (elementType) {		
		
        case CommentElementTypeIF.ITEM :  
        	
        	if (SubsystemIF.ICR.equals(origSubsystem)) {
        		if (SubsystemIF.ICR.equals(newSubsystem)) {
        			if (RoleIF.FE.equals(origRoleCode) && RoleIF.FE.equals(newRoleCode)) {
        				editSameSIDFlag = true;
        			}
        		} 
			} 
        	
            break;
            
        case CommentElementTypeIF.REPORTER :  
        	
        	if (SubsystemIF.ICR.equals(origSubsystem)) {
        		if (SubsystemIF.ICR.equals(newSubsystem)) {
        			if (RoleIF.FE.equals(origRoleCode) && RoleIF.FE.equals(newRoleCode)) {
        				editSameSIDFlag = true;
        			}
        		} 
        		
			} 
        	
        case CommentElementTypeIF.SURVEY_UNIT :  
        	
        	if (SubsystemIF.ICR.equals(origSubsystem)) {
        		if (SubsystemIF.ICR.equals(newSubsystem)) {
        			if (RoleIF.FE.equals(origRoleCode) && RoleIF.FE.equals(newRoleCode)) {
        				editSameSIDFlag = true;
        			}
        		} 
        		
			} 
        	
            break;
		}
		
		return editSameSIDFlag;
	}
	
	public static boolean canDeleteSameSID(String elementType, String programType, String origSubsystem, String newSubsystem, String origRoleCode, String newRoleCode) {
		
		//currently has same rules as edit
		return canDeleteSameSID(elementType, programType, origSubsystem, newSubsystem, origRoleCode, newRoleCode);
	}
	
	 public static int getDecrementedPeriod(int refMth, int decrement) {

	        if (refMth != 0 && decrement != 0) {
	        	String period = String.valueOf(refMth);
	            String yr = period.substring(0,4);
	            String mth = period.substring(4);

	            int mthInt = Integer.parseInt(mth);
	            int yrInt = Integer.parseInt(yr);
	            String prevPeriod = null;

	            mthInt = mthInt - decrement;
	            if (mthInt < 1) {
	                yrInt = (yrInt - 1) + (mthInt/12);
	                mthInt = 12 + (mthInt%12);
	            }
	            if (mthInt < 10) {
	                prevPeriod = (String.valueOf(yrInt)) + "0" + String.valueOf(mthInt);
	            } else {
	                prevPeriod = (String.valueOf(yrInt)) + String.valueOf(mthInt);
	            }

	            return Integer.parseInt(prevPeriod);

	        } else {
	            return 0;
	        }

	    }
}
