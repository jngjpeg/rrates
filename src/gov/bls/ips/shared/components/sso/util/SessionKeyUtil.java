/*******************************************************************************
 *
 * FILE : SessionKeyUtil.java
 * 
 *  REVISION HISTORY :
 *
 * Date         Name            Release              Description
 * -------  -----------------   ----------     ---------------------------------
 * 06/17/2020	Vijay Sathiya	Sprint 28		Initial version						
 * 
 ******************************************************************************/
package gov.bls.ips.shared.components.sso.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SessionKeyUtil {
  	
    
    public static byte[] getSessionKey() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);        
        //System.err.println("salt:"+salt.toString());
        return salt;
    }
    
    public static synchronized String getSessionTokenHash(String sessionID, byte[] salt) throws Exception
    {
        String generatedID = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt);
            byte[] bytes = md.digest(sessionID.getBytes());
            StringBuilder sb = new StringBuilder(); 
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toHexString(bytes[i] & 0xff));
            }
            generatedID = sb.toString();            
            
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
            throw new Exception(e);
        }
        return generatedID;
    }

    
}
