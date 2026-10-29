/*******************************************************************************
 *
 * FILE : SSOInfoDAO.java
 *
 * 
 *  REVISION HISTORY :
 *
 * Date         Name            Release              Description
 * -------  -----------------   ----------     ---------------------------------
 * 09/17/2015  Sirisha Kommi	Sprint 17   		Original
 * 10/19/2015  Sirisha Kommi	Sprint 18   		Table and column Name changes
 * 10/23/2015  Sirisha Kommi	Sprint 18			Allowing nulls for User_Sid
 * 06/17/2020  Vijay Sathiya   	ICR New Sprint28   	Added getUserLanName() 
 * 06/24/2020  Vijay Sathiya   	ICR New Sprint28   	Updated TO REPLACE  SSO_TOKEN WITH SESSION_ID
 ******************************************************************************/

package gov.bls.ips.shared.sso.components.dao;


import gov.bls.ips.shared.sso.api.value.SSOInfoValue;
import gov.bls.ips.shared.sso.components.util.SSODBUtil;
import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.dbcon.components.util.DBConnection;
import gov.bls.ips.shared.dbcon.components.util.DBConnectionFactory;
import gov.bls.ips.shared.framework.components.dao.IPSDAOIF;
import gov.bls.ips.shared.framework.components.dao.exception.DAOCreateException;
import gov.bls.ips.shared.framework.components.dao.exception.DAOFinderException;
import gov.bls.ips.shared.framework.components.dao.exception.DAORemoveException;
import gov.bls.ips.shared.framework.components.dao.exception.DAOSystemException;
import gov.bls.ips.shared.logger.components.IPSLogger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.apache.commons.lang3.StringUtils;

/**
 * @author Sirisha Kommi
 *
 */
public class SSOInfoDAO<T> implements IPSDAOIF<T>,SSOInfoDAOIF  {
	public static final String _CLASS_NAME = ClassFromStatic.getCurrentClass().getName();
	protected String _dataSourceName;
	public final StringBuilder sb = new StringBuilder();

	
	/**
	 * Default constructor.
	 */
	public SSOInfoDAO() throws SQLException {
		try {
			init(null);
		} catch (Exception sqle) {
			throw new SQLException(sqle.getMessage());
		}
	}

	/**
	 * Constructor with dataSourceName as parameter
	 */
	public SSOInfoDAO(String dataSourceName) throws SQLException {
		IPSLogger.logDebug(_CLASS_NAME,
				".SSOInfoDAO() BEGIN ");
		try {
			init(dataSourceName);
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME
					+ ".SSOInfoDAO(dataSourceName) Exception  ", e);
		}
		IPSLogger.logDebug(_CLASS_NAME, ".SSOInfoDAO(dataSourceName) "
				+ "END");
	}
	
	private void init(String dataSourceName) throws Exception {
		IPSLogger.logDebug(_CLASS_NAME,".init(dataSourceName) BEGIN");	  
		if(StringUtils.isNotBlank(dataSourceName) == false)
			this._dataSourceName=SSODBUtil.getDataSourceName();
		else
			this._dataSourceName=dataSourceName;

		IPSLogger.logDebug(_CLASS_NAME,".init(dataSourceName) END");
		
	}
	
	public void insertSSOInfo(SSOInfoValue ssoInfoVO)throws Exception{
	    IPSLogger.logDebug(_CLASS_NAME, "insertSSOInfo() BEGIN");
		DBConnection _cObj = null;
		PreparedStatement pStmt = null;
		try {
			long useSsoSID = SSODBUtil.getNextSID(this._dataSourceName, "SEQ_SSO_INFO");
			ssoInfoVO._ssoInfoSid = new Long(useSsoSID);
			_cObj = DBConnectionFactory.getConnection(this._dataSourceName);
			pStmt = _cObj.createPreparedStatement(_INSERT_SQL);			
			setPreparedStmtInsertSSOInfoAttrs(pStmt, ssoInfoVO);
			int insertCount = _cObj.executeUpdate(pStmt);
			IPSLogger.logDebug(_CLASS_NAME + ".insertSSOData() ",
					"insertCount='"+insertCount + "'");
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME, "insertSSOData()", e);
			throw e;
		} finally {
			if (_cObj != null)
				_cObj.close();
			_cObj = null;
			pStmt = null;
		}
	    IPSLogger.logDebug(_CLASS_NAME, "insertSSOInfo() END");
	}
	

	private void setPreparedStmtInsertSSOInfoAttrs(PreparedStatement pStmt,
			SSOInfoValue ssoInfoVO) throws Exception {
		IPSLogger.logDebug(_CLASS_NAME,
				".setPreparedStmtInsertSSOInfoAttrs() BEGIN");		
		int index = 0;		
		try {
			if (ssoInfoVO._ssoInfoSid==null || 
					ssoInfoVO._ssoInfoSid == 0)
				throw new Exception(
						"_ssoInfoSid Key is null SSOInfoDAO."
								+ "setPreparedStmtInsertSSOInfoAttrs()");
			pStmt.setLong(++index,ssoInfoVO._ssoInfoSid);// 1
			
			if (StringUtils.isNotBlank(ssoInfoVO._sessionId) == true)
				pStmt.setString(++index,ssoInfoVO._sessionId);// 2
			else
				pStmt.setNull(++index, java.sql.Types.VARCHAR);

			if (StringUtils.isNotBlank(ssoInfoVO._userLanName) == false)
				throw new Exception(
						"_userLanName is empty SSOInfoDAO."
								+ "setPreparedStmtInsertSSOInfoAttrs()");
			
			pStmt.setString(++index,ssoInfoVO._userLanName);// 3
			
			if (ssoInfoVO._userSid==null || ssoInfoVO._userSid == 0){
				pStmt.setNull(++index, java.sql.Types.LONGVARCHAR);
			} else {
				pStmt.setLong(++index, ssoInfoVO._userSid);// 4
			}
			
			pStmt.setString(++index,ssoInfoVO._userIpAddr);// 5
			pStmt.setString(++index,ssoInfoVO._userEmailAddr);// 6
			pStmt.setString(++index,ssoInfoVO._ssoRelyingPartyURL);// 7
			
			IPSLogger.logDebug(_CLASS_NAME + ".setPreparedStmtInsertSSOInfoAttrs() "
					+ "ssoInfoVO=", ssoInfoVO);
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME + ".setPreparedStmtInsertSSOInfoAttrs() ", e);
			throw new Exception("Exception Caught in SSOInfoDAO.setPreparedStmtInsertSSOInfoAttrs()");
		}
		IPSLogger.logDebug(_CLASS_NAME,
				".setPreparedStmtInsertSSOInfoAttrs() END ");
		
	}
	
	public SSOInfoValue getSSOInfo(String userLanName) throws Exception {
	    IPSLogger.logDebug(_CLASS_NAME, ".getSSOInfo(userLanName) BEGIN");
		DBConnection _cObj = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		SSOInfoValue ssoInfoVO = null;
		try {
			_cObj = DBConnectionFactory.getConnection(this._dataSourceName);
			pStmt = _cObj.createPreparedStatement(_SELECT_LATEST_USER_SSOINFO);			
			setPreparedStmtFindAttrs(pStmt,userLanName);
			pStmt.setMaxRows(1);
		    rSet = _cObj.executeQuery(pStmt);
		    while (rSet.next()) {	    			  
		    	ssoInfoVO = populateSSOInfoValue(rSet);
			}

		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME, "getSSOInfo(userLanName)", e);
			throw e;
		} finally {
			if (_cObj != null)
				_cObj.close();
			_cObj = null;
			pStmt = null;
		}
	    IPSLogger.logDebug(_CLASS_NAME, ".getSSOInfo(userLanName) END");
		return ssoInfoVO;
	}
	
	protected PreparedStatement setPreparedStmtFindAttrs(PreparedStatement pStmt,
			String userLanName) throws Exception {
		IPSLogger.logDebug(_CLASS_NAME,	"setPreparedStmtFindAttrs(PreparedStatement pStmt," +
				" String userLanName)  userLanName=" + userLanName );	   	  
		try{
			int index=0;
			if (StringUtils.isNotBlank(userLanName) == false)
				throw new Exception(
						"userLanName is empty SSOInfoDAO."+ "setPreparedStmtInsertSSOInfoAttrs()");
			pStmt.setString(++index, userLanName);

		}
		catch(Exception e){
			IPSLogger.logError(_CLASS_NAME+
					".setPreparedStmtFindAttrs() ",e);
			throw new Exception("Exception Caught in SSOInfoDAO.setPreparedStmtFindAttrs()");
		}
		IPSLogger.logDebug(_CLASS_NAME,
				".setPreparedStmtFindAttrs() END ");	   	  
		return pStmt;
		
	}


	private SSOInfoValue populateSSOInfoValue(ResultSet rSet) throws SQLException {
		SSOInfoValue ssoInfoVO = new SSOInfoValue();
		if(StringUtils.isNotBlank(rSet.getString(_USER_LAN_NAME))==true)
			ssoInfoVO._userLanName = rSet.getString(_USER_LAN_NAME);
		if(StringUtils.isNotBlank(rSet.getString(_SESSION_ID))==true)
			ssoInfoVO._sessionId = rSet.getString(_SESSION_ID);
		if(StringUtils.isNotBlank(rSet.getString(_USER_IP_ADDR))==true)
			ssoInfoVO._userIpAddr = rSet.getString(_USER_IP_ADDR);
		if(StringUtils.isNotBlank(rSet.getString(_CREATED_DATE))==true)
			//ssoInfoVO._createdDate = SSODBUtil.getStringFromDate(rSet.getDate(_CREATED_DATE),_DATE_FORMAT);
			ssoInfoVO._createdDate = rSet.getDate(_CREATED_DATE);
		if(StringUtils.isNotBlank(rSet.getString(_AUTHEN_TIME_DATE))==true)
			ssoInfoVO._authenDate =  rSet.getDate(_AUTHEN_TIME_DATE);
		if(StringUtils.isNotBlank(rSet.getString(_AUTHEN_EXPIRY_TIME_DATE))==true)
			ssoInfoVO._authenExpiryDate =  rSet.getDate(_AUTHEN_EXPIRY_TIME_DATE);
		if(StringUtils.isNotBlank(rSet.getString(_UPDATED_DATE))==true)
			//ssoInfoVO._updatedDate = SSODBUtil.getStringFromDate(rSet.getDate(_UPDATED_DATE),_DATE_FORMAT);
			ssoInfoVO._updatedDate = rSet.getDate(_UPDATED_DATE);
		ssoInfoVO._userSid= rSet.getLong(SSOInfoDAOIF._USER_SID);
		ssoInfoVO._ssoInfoSid = rSet.getLong(_SSO_INFO_SID);
		
		if(StringUtils.isNotBlank(rSet.getString(_SSO_RELYING_PARTY_URL))==true)
			ssoInfoVO._ssoRelyingPartyURL = rSet.getString(_SSO_RELYING_PARTY_URL);
		if(StringUtils.isNotBlank(rSet.getString(_USER_EMAIL_ADDR))==true)
			ssoInfoVO._userEmailAddr = rSet.getString(_USER_EMAIL_ADDR);
		
		return ssoInfoVO;
	}

	@Override
	public int insertRecord(T SSOInfoValue) throws DAOCreateException,
			DAOSystemException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean updateRecord(T SSOInfoValue) throws DAOSystemException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean deleteRecord(T SSOInfoValue) throws DAORemoveException,
			DAOSystemException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public T findRecordByPrimaryKey(Object pKey) throws DAOFinderException,
			DAOSystemException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int insertBatch(List<T> list) throws DAOCreateException,
			DAOSystemException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int updateBatch(List<T> list) throws DAOSystemException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<T> findAll(String condition) throws DAOFinderException,
			DAOSystemException {
		// TODO Auto-generated method stub
		return null;
	}
 

	public String getUserLanName(String sessionID)throws Exception {
	    IPSLogger.logDebug(_CLASS_NAME, ".getUserLanName(sessionID) BEGIN");
		DBConnection _cObj = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		String lanName= null;
		try {
			_cObj = DBConnectionFactory.getConnection(this._dataSourceName);
			pStmt = _cObj.createPreparedStatement(_SELECT_USER_LAN_NAME_SESSION);			
			setPreparedStmtFindAttrs(pStmt,sessionID);
			pStmt.setMaxRows(1);
		    rSet = _cObj.executeQuery(pStmt);
		    if (rSet.next()) {	 
		    		lanName=rSet.getString(_USER_LAN_NAME);		    	
			}

		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME, "getUserLanName(sessionID)", e);
			throw e;
		} finally {
			if (_cObj != null)
				_cObj.close();
			_cObj = null;
			pStmt = null;
		}
	    IPSLogger.logDebug(_CLASS_NAME, ".getUserLanName(sessionID) END");
		return lanName;
	}
	

}
