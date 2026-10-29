/*******************************************************************************
 *
 * FILE : SSOInfoDAOIF.java
 *
 * 
 *  REVISION HISTORY :
 *
 * Date         	Name            	Release              Description
 * -------  		-----------------   ----------     ---------------------------------
 * 09/09/2015		Sirisha Kommi		Sprint 17			Original						
 * 10/19/2015  		Sirisha Kommi		Sprint 18   		Table and column Name changes 
 * 06/17/2020       Vijay Sathiya   	ICR New Sprint28   	Added _SELECT_USER_LAN_NAME_SESSION 
 * 06/24/2020   	Vijay Sathiya   	ICR New Sprint28   	Updated TO REPLACE  SSO_TOKEN WITH SESSION_ID
 ******************************************************************************/
package gov.bls.ips.shared.sso.components.dao;


/**
 * @author Sirisha Kommi
 *
 */
public interface SSOInfoDAOIF {
		
	public static String _INSERT_SQL="INSERT INTO SSO_INFO (SSO_INFO_SID, SESSION_ID, AUTHEN_TIME_DATE, AUTHEN_EXPIRY_TIME_DATE, " +
			" USER_LAN_NAME, USER_SID, USER_IP_ADDR, CREATED_DATE, UPDATED_DATE, USER_EMAIL_ADDR, SSO_RELYING_PARTY_URL ) " +
			" VALUES (?,?,sysdate,sysdate+9/24,?,?,?,sysdate,sysdate,?,?)";
	
	public static String _UPDATE_IP_SQL="UPDATE SSO_INFO SET  user_ip_address=?,  updated_date =? " +
			" WHERE SSO_INFO_sid=?";
	
	public static String _UPDATE_expiry_time_SQL="UPDATE SSO_INFO SET  authen_expiry_time_date=?,  updated_date =? " +
			" WHERE SSO_INFO_sid=?";
//			" WHERE user_lan_name=? AND user_sid =? AND created_date=?";

	public static String _SELECT_LATEST_USER_SSOINFO="SELECT SSO_INFO_SID, SESSION_ID, AUTHEN_TIME_DATE, AUTHEN_EXPIRY_TIME_DATE," +
			" USER_LAN_NAME, USER_SID, USER_IP_ADDR, CREATED_DATE, UPDATED_DATE, USER_EMAIL_ADDR, SSO_RELYING_PARTY_URL " +
			" FROM SSO_INFO " +
			" WHERE user_lan_name=? order by SSO_INFO_SID DESC";
	
	//public static String _SELECT_MATCH_SSO_KEY=" SELECT 1 FROM SSO_INFO WHERE SESSION_ID=? AND TO_CHAR(AUTHEN_TIME_DATE, 'dd-mm-yyyy')=TO_CHAR(SYSDATE,'dd-mm-yyyy')";
	
	public static String _SELECT_USER_LAN_NAME_SESSION=" SELECT USER_LAN_NAME FROM SSO_INFO WHERE SESSION_ID=? AND TO_CHAR(AUTHEN_TIME_DATE, 'dd-mm-yyyy')=TO_CHAR(SYSDATE,'dd-mm-yyyy')";
	
	public static String _SSO_INFO_SID = "SSO_INFO_SID";
	public static String _SESSION_ID = "SESSION_ID";
	public static String _AUTHEN_TIME_DATE = "AUTHEN_TIME_DATE";
	public static String _AUTHEN_EXPIRY_TIME_DATE = "AUTHEN_EXPIRY_TIME_DATE";
	public static String _USER_LAN_NAME = "USER_LAN_NAME";
	public static String _USER_SID = "USER_SID";
	public static String _USER_IP_ADDR = "USER_IP_ADDR";
	public static String _CREATED_DATE = "CREATED_DATE";
	public static String _UPDATED_DATE = "UPDATED_DATE";
	public static String _USER_EMAIL_ADDR = "USER_EMAIL_ADDR";
	public static String _SSO_RELYING_PARTY_URL = "SSO_RELYING_PARTY_URL";
	public static final String _DATE_FORMAT = "mm/dd/yyyy hh24:MI:ss";
	
	
}
