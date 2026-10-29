/*******************************************************************************
 *
 * FILE : ClassFromStatic.java
 *
 * DESCRIPTION :
 *
 * REVISION HISTORY :
 *
 * Date         Name            Release             Description
 * -------  -----------------   ----------     ---------------------------------
 * 18Apr2011  Arun Jayavel                            original version
 *
 *  Copyright 2011 by All IPS-BLS .
 * All rights reserved.
******************************************************************************/

//package name
package gov.bls.ips.shared.components.util;

public class ClassFromStatic extends SecurityManager{
  public static Class getCurrentClass(){
    return new ClassFromStatic().getClassContext()[1];
  }
}




