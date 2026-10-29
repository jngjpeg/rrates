/*******************************************************************************
 *
 * FILE : DBUtil.java
 *
 *Interface: DAO
 *
 * DESCRIPTION :
 *   
 * 
 * REVISION HISTORY :
 *
 * Date         Name            Release             Description
 * -------  -----------------   ----------     ---------------------------------
 * 11Sep2011  Arun Jayavel                            original version
 * 01Mar2013  Arun Jayavel                     Added following methods
 *                                             getDateFromString(),
 *                                             getStringFromDate() 
 *                                             getCurrentDateStr() and 
 *                                             _FI_DATE_FORMAT
 * 11Mar2013  Arun Jayavel                     Fixed simple date format import
 * 02May2013  Chris Eller					
 * 07Aug2013  Kamran Anwar						Implemented sequences for CLUSTER_MEMBER_SID
 * 												CLUSTER_ID and FRAME_RECORD_ID    
 * 07Aug2013  Kamran Anwar						Implemented sequence for _NEXT_CLASSIFIED_INDUSTRY_SID_SQL
 * 
 * 23Jun2014  Kamran Anwar						Implemented sequence for _NEXT_REQUESTED_ITEM_SID_SQL
 * 
 * 24Jun2014  Arun Jayavel    Admin(Sprint2)	Implemented getDateStr with format
 * 
 * 28July2014 Jessy Joseph  Admin(Sprint4)	Implemented sequence for _NEXT_SU_NOTIFICATION_SID_SQL
 * 
 * 22Sep2014  Arun Jayavel    Admin(Sprint5)	Added getStringFromTimestamp 
 * 
 * 21Oct2014  Kamran Anwar		Sprint6(FI)		Modified getNextSIDSQLByName method to add implementation for fetching
 * 												sequence SIDs for SurveyUnitSCG table (Support For Survey Unit Split Functionality)
 * 
 * 13Feb2015  Kamran Anwar		Sprint10(FI)	Modified getNextSIDSqlByName TO ADD IMPLEMENTATION for fetching NEXT_INIT_ITEM_SID
 * 
 * 03Mar2015  Selva Sathiah		Sprint11(Admin)	Added method isOnlySpace.
 * 
 * 14May2015	Hari			Sprint13(FI)	Modified getNextSIDSqlByName to fetch _NEXT_INIT_SU_INIT_METHOD_SID
 * 20NOV2015	Hari			Sprint19(FI)	Modified getNextSIDSqlByName to fetch _NEXT_INIT_PERSON_PROGRAM_TYPE_SID_SQL
 * 25Feb2016	Hari			Sprint22(FI)	Modified to add _INIT_TERMS_OF_TRAN_SID_SEQUENCE_NAME implementation
 * 
 * 11Mar2016	Hari			Sprint23(FI)	Modified to add _INIT_RPC_CYCLE_CODE_SID_SEQUENCE_NAME implementation
 * 
 * 24Mar2016	Hari			Sprint23(FI)	Modified to add _INIT_PRICE_SID_SEQUENCE_NAME implementation
 * 
 * 7/22/15	    Vijay			Sprint 27(Init) Modified getStringFromTimestamp() to append timestamp if no time in date.
 * 08/24/2016	Kamran anwar	ICR(Sprint28)	Modified to utilize resourceBundle within getNextSIDSQLByName method
 * 02/17/2022	Ashok Yeddula	 NOBeta			Modified getDBMetaData() to fix the DB label in the Header.
 * 03/29/2022   Ram Velkur       NoBeta         Modified getDBMetaData() method and added finally block to close the DB connection.
 *  
 *  Copyright 2013 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.components.util;


import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import gov.bls.ips.shared.dbcon.components.util.DBConnection;
import gov.bls.ips.shared.dbcon.components.util.DBConnectionFactory;
import gov.bls.ips.shared.logger.components.IPSLogger;


public class DBUtil {
	public static String _CLASS_NAME=ClassFromStatic.getCurrentClass().getName();
	
	
	 /**
	 * This method returns database name.
	 * @return String database name.
	 * @throws Exception
	 */
	public static String getDBMetaData(String dataSourceName) throws Exception {
		IPSLogger.logDebug(_CLASS_NAME, "getDBMetaData - Start");		 
		String dbMDDisplay = ""; 
		DBConnection cObj=null;
		try {
			cObj = DBConnectionFactory.getConnection(dataSourceName);
			DatabaseMetaData md = cObj.getConnection().getMetaData();
			String url = md.getURL();
			String server = "";
			if (url != null) {
				if (url.lastIndexOf("/") != -1) {
					int index = url.lastIndexOf("/");
					server = url.substring(index + 1, url.indexOf(".", index + 1));
				}else if(url.lastIndexOf(":") != -1) {
					server = url.substring(url.lastIndexOf(":") + 1);
				}
			}

			dbMDDisplay = server.toUpperCase() + ":" + md.getUserName();
		} catch (SQLException e) {
			e.printStackTrace();
			IPSLogger.logError(_CLASS_NAME, e);
			throw new Exception("getDBMetaData - SQLException:"
					+ e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			IPSLogger.logError(_CLASS_NAME, ex);
			throw new Exception("getDBMetaData - Exception:"
					+ ex.getMessage());
		} finally {
			if (cObj != null)
				cObj.close();
			cObj = null;
		}

		IPSLogger.logDebug(_CLASS_NAME, "getDBMetaData - End");
		return dbMDDisplay;
	}
}
