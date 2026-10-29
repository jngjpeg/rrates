/*******************************************************************************
 *
 * FILE : ManagerIF.java
 *
 * DESCRIPTION :
 * This is a base framework interface for all managers.  
 * All managers needs to implement this and the Manager Factory class uses this.
 * This interface is used in the Assembler classes to have access to manager class methods.
 * 
 *  REVISION HISTORY :
 *
 * Date         Name            Release             Description
 * -------  -----------------   ----------     ---------------------------------
 * 13sep2011  Arun Jayavel                            original version
 * 19Mar2014  Ranga Gorthi						Initial Revision - Taken from NOIPreInit Code base
 * 05Dec2014  Kamran Anwar		Sprint8(FI)		Modified to refactor and make changes for integration with other teams
 * 
 *
 *  Copyright 2014 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.framework.api.util;

import gov.bls.ips.shared.framework.components.manager.ReaderIF;
import gov.bls.ips.shared.framework.components.manager.WriterIF;



public interface ManagerIF<T> extends ReaderIF<T>, WriterIF<T>{
}
