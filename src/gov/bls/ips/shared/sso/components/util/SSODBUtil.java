/*******************************************************************************
 *
 * FILE : SSODBUtil.java
 *
 *
 * DESCRIPTION : Util class for various Date formats and db name etc
 *   
 * 
 * REVISION HISTORY :
 *
 * Date         Name            	Release             Description
 * -------  	-----------------   ----------     		---------------------------------
 * 09/17/2015  	Sirisha Kommi                           original version
 * 10/19/2015   Sirisha Kommi		Sprint 18   		SEQ Name change
 * 
 *  Copyright 2013 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
package gov.bls.ips.shared.sso.components.util;


import gov.bls.ips.shared.sso.api.util.SSOConstants;
import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.dbcon.components.util.DBConnection;
import gov.bls.ips.shared.dbcon.components.util.DBConnectionFactory;
import gov.bls.ips.shared.framework.components.dao.exception.DAOSystemException;

import gov.bls.ips.shared.logger.components.IPSLogger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import org.apache.commons.lang3.StringUtils;

public class SSODBUtil implements SSODBUtilIF {
	public static String _CLASS_NAME = ClassFromStatic.getCurrentClass()
			.getName();
	public static volatile Long _CURR_ITEM_GROUP_SEARCH_SID;
	public static volatile Long _LATEST_CURR_ITEM_GROUP_SEARCH_SID;
	public static String _DATE_FORMAT = "MM-dd-yyyy:HH:mm:ss.SSS";
	public static SimpleDateFormat _SDF = new SimpleDateFormat(
			_DATE_FORMAT);


	public synchronized static long getNextSID(String dataSourceName,
			String sequenceName) throws DAOSystemException {

		IPSLogger.logDebug(_CLASS_NAME, ".getNextSID() BEGIN");
		DBConnection cObj = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		long returnSid;
		String sql;
		try {
			sql = getNextSIDSQLByName(sequenceName);
			if (sql == null) {
				throw new DAOSystemException("sequenceName Not available");
			}
			if(StringUtils.isNotBlank(dataSourceName)==false){
				dataSourceName=getDataSourceName();
			}
			cObj = DBConnectionFactory.getConnection(dataSourceName);
			pStmt = cObj.createPreparedStatement(sql);
			rSet = cObj.executeQuery(pStmt);
			if (rSet.next()) {
				returnSid = rSet.getLong(1);
			} else {
				throw new DAOSystemException("Next Val Not found");
			}
		} catch (SQLException se) {
			IPSLogger.logError(_CLASS_NAME
					+ ".getNextSID() Sql Exception Caught =", se.getMessage());
			throw new DAOSystemException(se.getMessage());
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME
					+ ".getNextSID() Exception Caught key=", e.getMessage());
			throw new DAOSystemException(e.getMessage());
		} finally {
			if (cObj != null)
				cObj.close();
			cObj = null;
			pStmt = null;
			rSet = null;
		}
		IPSLogger.logDebug(_CLASS_NAME, ".getNextSID() END");
		return returnSid;
	}

	public static String getNextSIDSQLByName(String sequenceName)
			throws DAOSystemException {
		IPSLogger.logDebug(_CLASS_NAME, ".getNextSIDSQLByName() BEGIN");
		String sequenceSQL = null;

		try {
			if (StringUtils.equalsIgnoreCase(_SSO_INFO_SID_SEQUENCE_NAME,
					sequenceName) == true) {
				sequenceSQL = _NEXT_SSO_INFO_SID_SQL;
			}
			else{
				IPSLogger.logError(_CLASS_NAME
						+ ".getNextSIDSQLByName() -"+sequenceName, "-Sequence Not found");
			}
			
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME
					+ ".getNextSIDSQLByName() Exception Caught", e);
			throw new DAOSystemException(e.getMessage());
		}
		IPSLogger.logDebug(_CLASS_NAME, ".getNextSIDSQLByName() END");
		return sequenceSQL;
	}


	public static String getCurrentDateStr() {
		IPSLogger.logDebug(_CLASS_NAME, ".getCurrentDateStr() BEGIN");
		Date currentDate = null;
		String currentDateStr = null;
		try {
			currentDate = new java.sql.Date(System.currentTimeMillis());
			IPSLogger.logDebug(_CLASS_NAME, ".getCurrentDateStr() "	+ "currentDate=" + currentDate.toString());
			if (_DATE_FORMAT != null && _SDF != null) {
				IPSLogger.logDebug(_CLASS_NAME, ".getCurrentDateStr() "	+ "format =" + _DATE_FORMAT);
				currentDateStr = _SDF.format(currentDate);
				IPSLogger.logDebug(_CLASS_NAME, ".getCurrentDateStr() "	+ "After format currentDateStr=" + currentDateStr);
			} else
				currentDateStr = currentDate.toString();
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME
					+ ".getStringFromDate() Exception Caught key=",
					e.getMessage());
		}
		IPSLogger.logDebug(_CLASS_NAME, ".getCurrentDateStr() END");
		return currentDateStr;
	}

	public static String getStringFromDate(Date date) {
		IPSLogger.logDebug(_CLASS_NAME, ".getStringFromDate() BEGIN");
		String dateStr = null;
		try {
			if (date != null) {
				IPSLogger.logDebug(_CLASS_NAME, ".getStringFromDate() "
						+ "currentDate=" + date.toString());
				if (_DATE_FORMAT != null && _SDF != null) {
					dateStr = _SDF.format(date);
				} else
					dateStr = date.toString();

				IPSLogger.logDebug(_CLASS_NAME, ".getStringFromDate() "
						+ "dateStr=" + dateStr);
			}
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME
					+ ".getStringFromDate() Exception Caught key=",
					e.getMessage());
		}
		IPSLogger.logDebug(_CLASS_NAME, ".getStringFromDate() END");
		return dateStr;
	}

	public static String getStringFromDate(Date date, String format) {
		IPSLogger.logDebug(_CLASS_NAME, ".getStringFromDate() BEGIN");
		String dateStr = null;
		SimpleDateFormat dateFormat = null;
		try {
			if (date != null && StringUtils.isNotBlank(format) == true) {
				IPSLogger.logDebug(_CLASS_NAME, ".getStringFromDate() "
						+ "currentDate=" + date.toString());
				dateFormat = new SimpleDateFormat(format);
				dateStr = dateFormat.format(date);
				IPSLogger.logDebug(_CLASS_NAME, ".getStringFromDate() "
						+ "dateStr=" + dateStr);
			} else if (date != null && StringUtils.isNotBlank(format) == true) {
				dateStr = getStringFromDate(date);
			}
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME
					+ ".getStringFromDate() Exception Caught key=",
					e.getMessage());
		}
		IPSLogger.logDebug(_CLASS_NAME, ".getStringFromDate() END");
		return dateStr;
	}

	public static Date getDateFromString(String dateStr) {
		IPSLogger.logDebug(_CLASS_NAME, ".getDateFromString() BEGIN");
		java.sql.Date returnDate = new java.sql.Date(System.currentTimeMillis());
		try {
			IPSLogger.logDebug(_CLASS_NAME, ".getDateFromString() dateStr="
					+ dateStr);
			if (StringUtils.isNotBlank(dateStr)) {
				if (_DATE_FORMAT != null && _SDF != null)
					returnDate = new java.sql.Date(_SDF.parse(
							dateStr).getTime());
				IPSLogger.logDebug(_CLASS_NAME, ".getDateFromString() "
						+ "returnDate =" + returnDate.toString());
			}
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME + ".getDateFromString() Exception "
					+ "Caught key=", e.getMessage());
		}
		IPSLogger.logDebug(_CLASS_NAME, ".getDateFromString() END");
		return returnDate;
	}

	public static Timestamp getTimestampFromString(String timestampStr) {
		IPSLogger.logDebug(_CLASS_NAME, ".getTimestampFromString() BEGIN");
		Timestamp returnTimestamp = null;
		try {
			IPSLogger.logDebug(_CLASS_NAME,
					".getTimestampFromString() timestampStr=" + timestampStr);
			if (StringUtils.isNotBlank(timestampStr)) {
				returnTimestamp = Timestamp.valueOf(timestampStr);
				IPSLogger.logDebug(_CLASS_NAME, ".getDateFromString() "
						+ "returnDate =" + returnTimestamp.toString());
			}
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME
					+ ".getTimestampFromString() Exception " + "Caught key=",
					e.getMessage());
		}
		IPSLogger.logDebug(_CLASS_NAME, ".getTimestampFromString() END");
		return returnTimestamp;
	}

	public synchronized static Timestamp getTimestamp() {
		IPSLogger.logDebug(_CLASS_NAME, ".getTimestamp() BEGIN");
		Timestamp returnTimeStamp = null;
		try {
			//returnTimeStamp = new Timestamp(Calendar.getInstance().getTime().getTime());
			
			java.util.Date today = new java.util.Date();
			returnTimeStamp= new java.sql.Timestamp(today.getTime());

		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME
					+ ".getTimestamp() Exception Caught key=", e.getMessage());
		}
		IPSLogger.logDebug(_CLASS_NAME, ".getTimestamp() END");
		return returnTimeStamp;
	}

	public synchronized static Timestamp getExtendedDate(int extendbyhours) {
		IPSLogger.logDebug(_CLASS_NAME, ".getExtendedDate() BEGIN");
		Timestamp extended = null;
		try {
			Calendar c = Calendar.getInstance();
			java.util.Date now = c.getTime();
			c.setTimeInMillis(now.getTime());
			c.add(Calendar.HOUR, extendbyhours);
			extended = new Timestamp(c.getTime().getTime());
			IPSLogger.logDebug(_CLASS_NAME, "extended date =" + extended.toString());
			
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME
					+ ".getExtendedDate() Exception Caught key=", e.getMessage());
		}
		IPSLogger.logDebug(_CLASS_NAME, ".getExtendedDate() END");
		return extended;
	}
	
	public static String getStringFromTimestamp(Timestamp timestamp,
			String format) {
		IPSLogger.logDebug(_CLASS_NAME, ".getStringFromDate() BEGIN");
		String timestampString = null;
		SimpleDateFormat timestampFormat = null;
		try {
			if (timestamp != null && StringUtils.isNotBlank(format) == true) {
				IPSLogger.logDebug(_CLASS_NAME, ".getStringFromDate() "
						+ "currentDate=" + timestamp.toString());
				timestampFormat = new SimpleDateFormat(format);
				timestampString = timestampFormat.format(timestamp);
				IPSLogger.logDebug(_CLASS_NAME, ".getStringFromDate() "
						+ "dateStr=" + timestampString);
			} else if (timestamp != null
					&& StringUtils.isNotBlank(format) == true) {
				timestampString = timestamp.toString();
			}
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME
					+ ".getStringFromDate() Exception Caught key=",
					e.getMessage());
		}
		IPSLogger.logDebug(_CLASS_NAME, ".getStringFromDate() END");
		return timestampString;
	}

	public static boolean isOnlySpace(String str) {
		if (str != null && str.length() > 0 && str.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	public static String getDataSourceName() {
		IPSLogger.logDebug(_CLASS_NAME, "getDataSourceName - Start");
		String dataSourceName = SSOConstants._SSO_DATA_SOURCE;
		
		if (dataSourceName == null || dataSourceName.isEmpty()) {
			dataSourceName = SSOConstants._SSO_DEFAULT_DATA_SOURCE;
		}
		IPSLogger.logDebug(_CLASS_NAME, "getDataSourceName - End");
		return dataSourceName;
	}


}
