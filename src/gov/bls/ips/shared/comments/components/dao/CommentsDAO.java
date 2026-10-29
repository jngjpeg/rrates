package gov.bls.ips.shared.comments.components.dao;

//need to change this to use shared ones
//import gov.bls.ipp.estimation.ixrev.components.utils.ConnectionPool;
import gov.bls.ips.shared.dbcon.components.util.*;
import gov.bls.ips.shared.comments.exceptions.ApplicationException;
//import gov.bls.ipp.estimation.ixrev.components.utils.StringUtil;
import gov.bls.ips.shared.comments.components.util.CommentsUtil;
//import gov.bls.ips.shared.comments.components.util.constants.CommentElementTypeIF;
import gov.bls.ips.shared.comments.components.util.constants.CommentTypeIF;
import gov.bls.ips.shared.comments.components.util.constants.SubsystemIF;
import gov.bls.ips.shared.comments.components.valueobjects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

/*
import gov.bls.ips.shared.components.util.ClassFromStatic;
import gov.bls.ips.shared.logger.components.IPSLogger;
*/
		  

public class CommentsDAO {
	
	/*public static final String _CLASS_NAME = ClassFromStatic.getCurrentClass()
              .getName();  */
	  
	private static CommentsDAO obj = null;
    private static Object monitor = new Object();
    private String dataSourceName = "IPSTxDataSource";
    //todo: setup a different dataSource for initiation comments
    
    private CommentsDAO(){
    }

    public static CommentsDAO getObject(){
        if(obj == null){
            synchronized(monitor){
                if(obj == null){
                    obj = new CommentsDAO();
                }
            }
        }
        return obj;
    }
    
    private int getRefMth()
    		throws ApplicationException {
    		
    		DBConnection cObj = null;
    		PreparedStatement pstmt = null;		
    		ResultSet rs = null;
    		String strSql = null;
    		
    		int refMth = 0;
    		
    		try {
    			
    			cObj = DBConnectionFactory.getConnection(dataSourceName);
    			strSql = "select param_value " + 
    						"from ips_udb_ic.PRODUCTION_PARAM where param_name = 'ESTIMATION_REFMONTH'";	
    			
    		//	IPSLogger.logDebug(_CLASS_NAME,"getRefMth() called");					
    			//System.out.println("strSql == " + strSql);
    			pstmt = cObj.createPreparedStatement(strSql);
    			rs = cObj.executeQuery(pstmt);
    			
    			while (rs.next()) {
    				String refMthStr = rs.getString("param_value");
    				if (refMthStr != null) {
    					refMth = Integer.valueOf(refMthStr);
    				}
    			}
    			
    		 } catch(SQLException se){
    	 		 throw new ApplicationException(se.getMessage(), se);
    		 } catch (Exception ex){
    			 throw new ApplicationException(ex.getMessage(), ex);
    			 } finally { 		 
    				 closeResources(rs, pstmt, cObj);
    			 }
    			 
    		return refMth;
    	}	
    
    public List<Integer> getRefMthList() throws ApplicationException {
    	
    	DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		String sql = null;
		List<Integer> mthList = new ArrayList<Integer>();		
		
		try {
			/*IPS4.1: Uncommented code for retrieving all the ref months from the comments table */
			con = DBConnectionFactory.getConnection(dataSourceName);
			sql = "select distinct calc_for_refmonth from monthly_comment order by calc_for_refmonth desc";						
			
			//System.out.println("getRefMthList sql == " + sql);
			pstmt = con.createPreparedStatement(sql);
			rs = con.executeQuery(pstmt);
			
			while (rs.next()) {
				mthList.add(rs.getInt("calc_for_refmonth"));
			}	
			
			if (mthList.size()==0) {
				int erm = getRefMth();
				
				if (erm!=0) {
					mthList.add(erm);
					mthList.add(CommentsUtil.getDecrementedPeriod(erm, 1));
					mthList.add(CommentsUtil.getDecrementedPeriod(erm, 2));
					mthList.add(CommentsUtil.getDecrementedPeriod(erm, 3));
				}				
				
			}
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in getRefMthList: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in getRefMthList: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
		
		
		//if (mthList.size()==0) { mthList = getEditableRefMthList(); }
		return mthList;
	}    	
    
    
    public List<Integer> getEditableRefMthList() throws ApplicationException {
    	
    	DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		String sql = null;
		//List<Integer> mthList = getRefMthList();
		List<Integer> mthList = new ArrayList<Integer>();
		int erm = getRefMth();
		/*IPS4.1: Changes related to retrieving all the ref months from the comments table */
		if (erm!=0) {
			mthList.add(erm);
			mthList.add(CommentsUtil.getDecrementedPeriod(erm, 1));
			mthList.add(CommentsUtil.getDecrementedPeriod(erm, 2));
			mthList.add(CommentsUtil.getDecrementedPeriod(erm, 3));
		}				
		/*		
				new ArrayList<Integer>();		
		int mth = 0;
		
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);
			sql = "select param_value t, param_value-1 t1, param_value-2 t2, param_value-3 t3 from production_param where param_name = 'ESTIMATION_REFMONTH'";			
			
			//System.out.println("getEditableRefMthList sql == " + sql);
			
			pstmt = con.createPreparedStatement(sql);
			rs = con.executeQuery(pstmt);
		
			while (rs.next()) {
				mth = rs.getInt("t");
				mthList.add(mth);
				mth = rs.getInt("t1");
				mthList.add(mth);
				mth = rs.getInt("t2");
				mthList.add(mth);
				mth = rs.getInt("t3");
				mthList.add(mth);
			}
			
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in getRefMthList: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in getRefMthList: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	*/
		
		return mthList;
	}    	

    public Map<Integer, List<RefMthCommentVO>> getRefMthComments(int elementSID, String elementType, int startRefMth, int endRefMth) 
        	throws ApplicationException {
        	
        	Map commentVOMap = new TreeMap<String, List<RefMthCommentVO>>();        	
        	
        	List<RefMthCommentVO> tempCommentVOList = null;
        	Map tempCommentVOMap = new TreeMap<Integer, List<RefMthCommentVO>>();
        	RefMthCommentVO tempCommentVO = null;
        	
    		List<RefMthCommentVO> commentVOList = null;
    		RefMthCommentVO commentVO = null;
    		
    		DBConnection con = null;
    		PreparedStatement pstmt = null;		
    		ResultSet rs = null;
    		StringBuffer buf = new StringBuffer();
    		int tempRefMth = 0;
    		int tempCalcForRefmth = 0;
    		int currRefMth = 0;
    		int currCalcForRefmth = 0;	
    		String commentText = "";
    		
    		int refMth = getRefMth();
    		
    		//short term fix. Need to fix ui
    		if (endRefMth==0) {
    			endRefMth=refMth;
    		}    		
    		
    		if (startRefMth==0) {
    			startRefMth=CommentsUtil.getDecrementedPeriod(endRefMth, 3);
    		}
    		
    		try {
    			con = DBConnectionFactory.getConnection(dataSourceName);
    			buf.append("select monthly_comment_sid, refmonth_, calc_for_refmonth, business_object_id, user_lan_name, created_date, subsystem_code, ");
    			buf.append("comment_txt from monthly_comment ");
    			buf.append("where business_object_sid = ? and calc_for_refmonth between ? and ?");
    		//	buf.append(" order by refmonth_ desc, calc_for_refmonth desc, created_date desc"); 			
    			buf.append(" order by calc_for_refmonth desc, refmonth_ desc, created_date desc");
    			
    			String sql = buf.toString();
    			//System.out.println("get ref mth comments sql == " + sql);
    			pstmt = con.createPreparedStatement(sql);
    			pstmt.setInt(1,  elementSID);
    			pstmt.setInt(2,  startRefMth);
    			pstmt.setInt(3,  endRefMth);
    			rs = pstmt.executeQuery();
    			String newCommentTxt = null;
    			
    			while (rs.next()) {
    				currRefMth = rs.getInt("refmonth_");
    				currCalcForRefmth = rs.getInt("calc_for_refmonth");
    				
    				if (currCalcForRefmth!=tempCalcForRefmth) {					
    					//tempCalcForMap = new TreeMap<Integer, RefMthCommentVO>();	
    					tempCommentVOList = new ArrayList<RefMthCommentVO>();
    					tempCommentVOMap.put(currCalcForRefmth, tempCommentVOList);
    				}
    				
    				//if (currCalcForRefmth!=tempCalcForRefmth) { 
    				commentVO = new RefMthCommentVO();
    					
    				commentVO.setCommentSID(rs.getInt("monthly_comment_sid"));
    				commentVO.setCalcInRefMth(currRefMth);
    				commentVO.setCalcForRefMth(currCalcForRefmth);
    				commentVO.setElementCode(rs.getString("business_object_id"));
    				commentVO.setElementSID(elementSID);
    				commentVO.setElementType(elementType);
    				commentVO.setCommentType(CommentTypeIF.REF_MTH);
    				
    				newCommentTxt = rs.getString("comment_txt");
    				if (newCommentTxt!=null && newCommentTxt.trim().length()>0) {
    					newCommentTxt = newCommentTxt.replaceAll("\"", "'");
    					commentVO.setCommentText(newCommentTxt);
    				}    				
    				//commentVO.setEditable(true);
    				//commentVOList.add(commentVO);
    					
    				tempCommentVOList.add(commentVO);
    				tempCalcForRefmth = currCalcForRefmth;				
    				
    			}
    			
    			//System.out.println("tempCommentVOMap == " + tempCommentVOMap);
    			
    			Set entrySet = tempCommentVOMap.entrySet();
    			Iterator inIt = entrySet.iterator();
    			Map.Entry entry = null;
    			Integer calcForMth = 0;    	
    			List<RefMthCommentVO> tempCommentVOList2 = null;
    			
    			RefMthCommentVO tempConcatCommentVO = null;
    			Iterator tempCommentListIT = null;
    			String concatenatedCommment = "";
    			boolean calcForRefMthForUIFlag = false;
    			
    			//if (tempCalcInMap.containsKey(refMth)) { //need editable blocks
    				
    				while (inIt.hasNext()) {
    					entry = (Map.Entry) inIt.next();
    					calcForMth = (Integer) entry.getKey();
    					//System.out.println("calcForMth == " + calcForMth);
    					
    					tempCommentVOList2 = (List) entry.getValue();
    					tempCommentListIT = tempCommentVOList2.iterator();
    					
    					commentVOList = new ArrayList<RefMthCommentVO>();
    					commentVOMap.put(calcForMth, commentVOList);
    					
    					tempConcatCommentVO = null;
    					concatenatedCommment = "";
    					calcForRefMthForUIFlag = false;
    					
    					while (tempCommentListIT.hasNext()) {
    				
    						tempCommentVO = (RefMthCommentVO)tempCommentListIT.next();
    						
    						//System.out.println("tempCommentVO == " + tempCommentVO);
    						
    						if (refMth==tempCommentVO.getCalcInRefMth()) { //editable
    							tempCommentVO.setEditableFlag(true);
    							tempCommentVO.setCalcForRefMthForUI(String.valueOf(tempCommentVO.getCalcForRefMth()));
    							commentVOList.add(tempCommentVO);    		
    							calcForRefMthForUIFlag = true;
    							//System.out.println("refMth==calcForMth == ");
    						} else { //non-editable - have to be concatenated
    							
    							if (tempConcatCommentVO==null) {
    								tempConcatCommentVO = tempCommentVO.clone();
    							}
    							if (concatenatedCommment.length()==0) {
    								concatenatedCommment = concatenatedCommment + tempCommentVO.getCommentText();
    							} else {
    								concatenatedCommment = concatenatedCommment + "\n " + tempCommentVO.getCommentText() ;
    							}							
    							
    							//System.out.println("concatenatedCommment == " + concatenatedCommment);
    						}
    					}
    					if (concatenatedCommment.length()>0) {
    						//System.out.println("concatenatedCommment added to list == " + concatenatedCommment);
    						tempConcatCommentVO.setCommentText(concatenatedCommment);
    						tempConcatCommentVO.setEditableFlag(false);
    						
    						if (calcForRefMthForUIFlag==false) {
    							tempConcatCommentVO.setCalcForRefMthForUI(String.valueOf(tempConcatCommentVO.getCalcForRefMth()));
    						}
    						commentVOList.add(tempConcatCommentVO);
    					}
    					
    				}
    		//	}

    		 } catch(SQLException se){
    			 se.printStackTrace();
    	 		 throw new ApplicationException("Exception in getRefMthComments: "+se.getMessage(), se);
    		 } catch (Exception ex){
    			 ex.printStackTrace();
    			 throw new ApplicationException("Exception in getRefMthComments: "+ex.getMessage(), ex);
    		 } finally { 		 
    			 closeResources(rs, pstmt, con);
    		 }			
    		
    		//201407
    		/*CommentVO commentVO = new RefMthCommentVO(1, 11, CommentTypeIF.REF_MTH, CommentElementTypeIF.STRATUM,
    				"R1234", SubsystemIF.IXREV, 100539,
    				"cerritos_g", null, "IA",
    				"201407 comments not editable blah blah blah", false, 201407, 201407);*/
    		
    	//	System.out.println("commentVOMap == " + commentVOMap);
    		return commentVOMap;
    		
        }   
        
    
    public Map<String, List<RefMthCommentVO>> getRefMthCommentsOld(int elementSID, String elementType, int startRefMth, int endRefMth) 
    	throws ApplicationException {
    	
    	Map commentVOMap = new TreeMap<String, List<RefMthCommentVO>>();
    	Map tempCalcForMap = null;
    	Map tempCalcInMap = new TreeMap<Integer, Map<Integer, List<RefMthCommentVO>>>();
    	
		List<RefMthCommentVO> commentVOList = null;
		RefMthCommentVO commentVO = null;
		
		DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = new StringBuffer();
		int tempRefMth = 0;
		int tempCalcForRefmth = 0;
		int currRefMth = 0;
		int currCalcForRefmth = 0;	
		String commentText = "";
		
		int refMth = getRefMth();
		
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);
			buf.append("select monthly_comment_sid, refmonth_, calc_for_refmonth, business_object_id, user_lan_name, created_date, subsystem_code, ");
			buf.append("comment_txt from monthly_comment ");
			buf.append("where business_object_sid = " + elementSID +  " and calc_for_refmonth between " + startRefMth + " and " + endRefMth);
		//	buf.append(" order by refmonth_ desc, calc_for_refmonth desc, created_date desc"); 			
			buf.append(" order by calc_for_refmonth desc, refmonth_ desc, created_date desc");
			
			String sql = buf.toString();
			//System.out.println("get ref mth comments sql == " + sql);
			
			pstmt = con.createPreparedStatement(sql);
			rs = con.executeQuery(pstmt);			
						
			while (rs.next()) {
				currRefMth = rs.getInt("refmonth_");
				currCalcForRefmth = rs.getInt("calc_for_refmonth");
				
				if (currRefMth!=tempRefMth) {					
					//tempCalcForMap = new TreeMap<Integer, RefMthCommentVO>();					
					tempCalcInMap.put(currRefMth, tempCalcForMap);
				}
				
				//if (currCalcForRefmth!=tempCalcForRefmth) { 
				commentVO = new RefMthCommentVO();
					
				commentVO.setCommentSID(rs.getInt("monthly_comment_sid"));
				commentVO.setCalcInRefMth(currRefMth);
				commentVO.setCalcForRefMth(currCalcForRefmth);
				commentVO.setElementCode(rs.getString("business_object_id"));
				commentVO.setElementSID(elementSID);
				commentVO.setElementType(elementType);
				commentVO.setCommentType(CommentTypeIF.REF_MTH);
				commentVO.setCommentText(rs.getString("comment_txt"));
				//commentVO.setEditable(true);
				//commentVOList.add(commentVO);
				tempCalcForMap.put(currCalcForRefmth, commentVO);				
				
				tempRefMth = currRefMth;				
				
			}
			
			//System.out.println("tempCalcInMap == " + tempCalcInMap);
			
			Set entrySet = tempCalcInMap.entrySet();
			Iterator inIt = entrySet.iterator();
			Map.Entry entry = null;
			Integer calcInMth = 0;
			
			Set entrySetFor = null;
			Iterator itFor = null;
			Map.Entry entryFor = null;
			Integer calcForMth = 0;
			
			Map calcForMap = null;
			Map calcForCurrMap = null;
			RefMthCommentVO tempCommentVO = null;
			RefMthCommentVO tempConcatCommentVO = null;
			String concatenatedCommment = null;
			String calcInMthStr = null;
			
			//if (tempCalcInMap.containsKey(refMth)) { //need editable blocks
				
				while (inIt.hasNext()) {
					entry = (Map.Entry) inIt.next();
					calcInMth = (Integer) entry.getKey();
					//System.out.println("calcInMth == " + calcInMth);
					calcForMap = (Map) entry.getValue();
					
				/*	if (calcInMth==refMth) { //keep separate to get editable blocks
						calcForCurrMap = calcForMap;
					}*/
					
					commentVOList = new ArrayList<RefMthCommentVO>();
					calcInMthStr = String.valueOf(calcInMth);
					commentVOMap.put(calcInMthStr, commentVOList);
					
					entrySetFor = calcForMap.entrySet();
					itFor = entrySetFor.iterator();
					entryFor = null;		
					concatenatedCommment = "";
					tempConcatCommentVO = null;
					
					while (itFor.hasNext()) {
						entryFor = (Map.Entry) itFor.next();
						calcForMth = (Integer) entryFor.getKey();
						//System.out.println("calcForMth == " + calcForMth);
						tempCommentVO = (RefMthCommentVO) entryFor.getValue();
						
						//System.out.println("tempCommentVO == " + tempCommentVO);
						
						if (refMth==calcForMth) { //editable
							tempCommentVO.setEditableFlag(true);
							commentVOList.add(tempCommentVO);
							//System.out.println("refMth==calcForMth == ");
						} else { //non-editable - have to be concatenated
							
							if (tempConcatCommentVO==null) {
								tempConcatCommentVO = tempCommentVO.clone();
							}
							if (concatenatedCommment.length()>0) {
								concatenatedCommment = concatenatedCommment + tempCommentVO.getCommentText();
							} else {
								concatenatedCommment = concatenatedCommment + "\n " + tempCommentVO.getCommentText() ;
							}							
							
							//System.out.println("concatenatedCommment == " + concatenatedCommment);
						}
					}
					if (concatenatedCommment.length()>0) {
						//System.out.println("concatenatedCommment added to list == " + concatenatedCommment);
						tempConcatCommentVO.setCommentText(concatenatedCommment);
						tempConcatCommentVO.setEditableFlag(false);
						commentVOList.add(tempConcatCommentVO);
					}
					
				}
		//	}

		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in getRefMthComments: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in getRefMthComments: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }			
		
		//201407
		/*CommentVO commentVO = new RefMthCommentVO(1, 11, CommentTypeIF.REF_MTH, CommentElementTypeIF.STRATUM,
				"R1234", SubsystemIF.IXREV, 100539,
				"cerritos_g", null, "IA",
				"201407 comments not editable blah blah blah", false, 201407, 201407);*/
		
		//System.out.println("commentVOMap == " + commentVOMap);
		return commentVOMap;
		
    }   
    
    
    public Map getRefMthComments(String elementSIDStr, String elementType, String calcForRefMthStr, boolean withMonth) throws ApplicationException {
    	
    	if (elementSIDStr==null || elementSIDStr.isEmpty()==true) return null;
    	
    	DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;
		String commentTxt = null;
		String concatenatedCommment = "";
		
		Map refMthCommentMap = new HashMap();	
		
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);
			buf = new StringBuffer("select business_object_sid, refmonth_, comment_txt from monthly_comment ");  
			buf.append("where refmonth_ in (" + calcForRefMthStr + ")"); //always in/for the same ERM
			buf.append(" and calc_for_refmonth = refmonth_ ");
			buf.append(" and business_object_sid in (" + elementSIDStr + ")");
			buf.append(" and business_object_code = '" + elementType + "' ");
					
			String sql = buf.toString();
			//System.out.println("getRefMthComments sql == " + sql);
			
			pstmt = con.createPreparedStatement(sql);
			rs = con.executeQuery(pstmt);
								
			while (rs.next()) {				
				commentTxt = rs.getString("comment_txt");		
				
				if (commentTxt!=null && commentTxt.trim().length()>0) {
					commentTxt = commentTxt.replaceAll("\"", "'");					
				}
				
				if (withMonth==true) {
					refMthCommentMap.put(rs.getInt("business_object_sid")+"-"+rs.getInt("refmonth_"), commentTxt);
				} else {
					refMthCommentMap.put(rs.getInt("business_object_sid"), commentTxt);
				}
				//System.out.println("commentTxt == " + commentTxt);
			}			
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in getStaticComments: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in getStaticComments: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
		
		return refMthCommentMap;
	}
    
    
    public String getRefMthComments(int elementSID, String elementType, int calcForRefMth) throws ApplicationException {
    	DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;
		String commentTxt = null;
		String concatenatedCommment = "";
		
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);
			buf = new StringBuffer("select comment_txt from monthly_comment ");  
			buf.append("where refmonth_ = ?"); //always for current ERM
			buf.append(" and calc_for_refmonth = ?");
			buf.append(" and business_object_sid = ?");
			buf.append(" and business_object_code = ?");
					
			String sql = buf.toString();
			//System.out.println("getRefMthComments sql == " + sql);
			
			pstmt = con.createPreparedStatement(sql);
			pstmt.setInt(1, calcForRefMth);
			pstmt.setInt(2, calcForRefMth);
			pstmt.setInt(3, elementSID);
			pstmt.setString(4, elementType);
			rs = pstmt.executeQuery();
								
			while (rs.next()) {				
				commentTxt = rs.getString("comment_txt");		
				
				if (commentTxt!=null && commentTxt.trim().length()>0) {
					commentTxt = commentTxt.replaceAll("\"", "'");					
				}
				
				//System.out.println("commentTxt == " + commentTxt);
			}			
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in getStaticComments: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in getStaticComments: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
		
		return commentTxt;
	}
    
    
    public String getStaticComments(int elementSID, String elementType, String subSystem) throws ApplicationException {
    	DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;
		String commentTxt = null;
		
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);
			buf = new StringBuffer("select comment_lob from static_comment ");
					buf.append("where business_object_sid = " + elementSID + " and business_object_code = '" + elementType + "'");
					//buf.append(" and subsystem_code = '" + subSystem + "'");
					
			if ("ITEM".equals(elementType)) {
				buf.append(" and subsystem_code in ('RPC', 'ERRM', 'ICR')");
			} else {
				buf.append(" and subsystem_code = 'IXREV'");
			}
					
			String sql = buf.toString();
			//System.out.println("getStaticComments sql 1 == " + sql);
			
			pstmt = con.createPreparedStatement(sql);
			rs = con.executeQuery(pstmt);			
			
			while (rs.next()) {				
				commentTxt = rs.getString("comment_lob");			
				if (commentTxt!=null && commentTxt.trim().length()>0) {
					commentTxt = commentTxt.replaceAll("\"", "'");
				}
			}			
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in getStaticComments: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in getStaticComments: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
		
		return commentTxt;
	}
    
    
    public Map getStaticComments(String elementSIDStr, String elementType, String subSystem) throws ApplicationException {
    	DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;
		String commentTxt = null;
		
		if (elementSIDStr==null || elementSIDStr.isEmpty()) return null;
		
		
		Map staticCommentMap = new HashMap(); 
		
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);
			buf = new StringBuffer("select business_object_sid, comment_lob from static_comment ");
					buf.append("where business_object_sid in (" + elementSIDStr + ") and business_object_code = '" + elementType + "'");
					//buf.append(" and subsystem_code = '" + subSystem + "'");
					
			if ("ITEM".equals(elementType)) {
				//buf.append(" and subsystem_code = 'RPC'");
				buf.append(" and subsystem_code in ('RPC', 'ERRM','ICR')");
			} else {
				buf.append(" and subsystem_code = 'IXREV'");
			}
					
					
			String sql = buf.toString();
			//System.out.println("getStaticComments sql 1 == " + sql);
			
			pstmt = con.createPreparedStatement(sql);
			rs = con.executeQuery(pstmt);			
			
			while (rs.next()) {				
				commentTxt = rs.getString("comment_lob");			
				if (commentTxt!=null && commentTxt.trim().length()>0) {
					commentTxt = commentTxt.replaceAll("\"", "'");
				}
				
				staticCommentMap.put(rs.getInt("business_object_sid"), commentTxt);
			}			
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in getStaticComments: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in getStaticComments: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
		
		return staticCommentMap;
	}
    

	public void addRefMthComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, RefMthCommentVO commentVO, List<Integer> mthsToSaveForList) 
			throws ApplicationException {
		
		//System.out.println("commentVO == "+commentVO.getCommentText());	
		
		addRefMthCommentsForMth(loggedInUserSID, loggedInUserLanName, loggedInRoleCode, commentVO, commentVO.getCalcForRefMth()); 
		if (mthsToSaveForList!=null && mthsToSaveForList.size()>0) {
			
			int mthsToAppendTo = 0;
			
			Iterator it = mthsToSaveForList.iterator();
			while (it.hasNext()) {
				mthsToAppendTo = (Integer) it.next();
				addRefMthCommentsForMth(loggedInUserSID, loggedInUserLanName, loggedInRoleCode, commentVO, mthsToAppendTo); 
			}
		}		
		
	}
	
	   public Map getBookmarkComments(String elementSIDStr, String elementType, int loggedInUserSID, 
			   String loggedInUserRoleCode, String subSystem, String refMthStr, boolean withMonth) throws ApplicationException {
		   
		   if (elementSIDStr==null || elementSIDStr.isEmpty()==true) return null;
		   
	    	DBConnection con = null;
			PreparedStatement pstmt = null;		
			ResultSet rs = null;
			StringBuffer buf = null;
			BookmarkCommentVO commentVO = null;
			
			Map bookmarkCommentMap = new HashMap();
			
			try {
				con = DBConnectionFactory.getConnection(dataSourceName);
				buf = new StringBuffer("select bookmark_comment_sid, business_object_sid, duration_, refmonth_, business_object_id, comment_txt, subsystem_code from bookmark_comment ");
						buf.append("where business_object_sid in (" + elementSIDStr + ") and business_object_code = '" + elementType + "' and user_sid = " + loggedInUserSID);
						buf.append(" and subsystem_code = 'IXREV'");
						buf.append(" and ((duration_ = 'I') ");
						buf.append("or (duration_ = 'C' and refmonth_ in (" + refMthStr + ")))");
						
				String sql = buf.toString();
			//	System.out.println("getBookmarkComments sql == " + sql);
				
				pstmt = con.createPreparedStatement(sql);
				rs = con.executeQuery(pstmt);
				
				String newCommentTxt = null;				
						
				while (rs.next()) {		
					commentVO = new BookmarkCommentVO();
					commentVO.setCommentSID(rs.getInt("bookmark_comment_sid"));
					commentVO.setDuration(rs.getString("duration_"));
					commentVO.setCalcInRefMth(rs.getInt("refmonth_"));
					commentVO.setElementCode(rs.getString("business_object_id"));
					
					newCommentTxt = rs.getString("comment_txt");
    				if (newCommentTxt!=null && newCommentTxt.trim().length()>0) {
    					newCommentTxt = newCommentTxt.replaceAll("\"", "'");
    					commentVO.setCommentText(newCommentTxt);
    				}					
						
					commentVO.setUserSID(loggedInUserSID);
					commentVO.setElementSID(rs.getInt("business_object_sid"));
					commentVO.setElementType(elementType);
					commentVO.setSubSystem(subSystem);
					commentVO.setRoleCode(loggedInUserRoleCode);
					
					if (withMonth==true) {
						bookmarkCommentMap.put(commentVO.getElementSID()+"-"+commentVO.getCalcInRefMth(), commentVO);
					} else {
						bookmarkCommentMap.put(commentVO.getElementSID(), commentVO);
					}
				}			
			 } catch(SQLException se){
				 se.printStackTrace();
		 		 throw new ApplicationException("Exception in getStaticComments: "+se.getMessage(), se);
			 } catch (Exception ex){
				 ex.printStackTrace();
				 throw new ApplicationException("Exception in getStaticComments: "+ex.getMessage(), ex);
			 } finally { 		 
				 closeResources(rs, pstmt, con);
			 }	
			
			return bookmarkCommentMap;
		}	
	   
	   
	   public BookmarkCommentVO getBookmarkComments(int elementSID, String elementType, int loggedInUserSID, String loggedInUserRoleCode, String subSystem) throws ApplicationException {
	    	DBConnection con = null;
			PreparedStatement pstmt = null;		
			ResultSet rs = null;
			StringBuffer buf = null;
			BookmarkCommentVO commentVO = null;
			
			try {
				con = DBConnectionFactory.getConnection(dataSourceName);
				buf = new StringBuffer("select bookmark_comment_sid, duration_, refmonth_, business_object_id, comment_txt, subsystem_code from bookmark_comment ");
						buf.append("where business_object_sid = ? and business_object_code = ? and user_sid = ?");
						buf.append(" and subsystem_code = 'IXREV'");
					
						
				String sql = buf.toString();
				//System.out.println("getBookmarkComments sql == " + sql);
				
				pstmt = con.createPreparedStatement(sql);
				pstmt.setInt(1, elementSID);
				pstmt.setString(2, elementType);
				pstmt.setInt(3, loggedInUserSID);
				rs = pstmt.executeQuery();
				
				String newCommentTxt = null;				
						
				while (rs.next()) {		
					commentVO = new BookmarkCommentVO();
					commentVO.setCommentSID(rs.getInt("bookmark_comment_sid"));
					commentVO.setDuration(rs.getString("duration_"));
					commentVO.setCalcInRefMth(rs.getInt("refmonth_"));
					commentVO.setElementCode(rs.getString("business_object_id"));
					
					newCommentTxt = rs.getString("comment_txt");
   				if (newCommentTxt!=null && newCommentTxt.trim().length()>0) {
   					newCommentTxt = newCommentTxt.replaceAll("\"", "'");
   					commentVO.setCommentText(newCommentTxt);
   				}					
						
					commentVO.setUserSID(loggedInUserSID);
					commentVO.setElementSID(elementSID);
					commentVO.setElementType(elementType);
					commentVO.setSubSystem(subSystem);
					commentVO.setRoleCode(loggedInUserRoleCode);
				}			
			 } catch(SQLException se){
				 se.printStackTrace();
		 		 throw new ApplicationException("Exception in getStaticComments: "+se.getMessage(), se);
			 } catch (Exception ex){
				 ex.printStackTrace();
				 throw new ApplicationException("Exception in getStaticComments: "+ex.getMessage(), ex);
			 } finally { 		 
				 closeResources(rs, pstmt, con);
			 }	
			
			return commentVO;
		}	
	
	public void editBookmarkComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, BookmarkCommentVO commentVO) 
			throws ApplicationException {
		
		DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;
				
		int refMth = getRefMth();		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss aa");
		Date date = new Date();
		String dateTime = sdf.format(date);		
		String newCommentTxt = "";		
		
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);
			
			buf = new StringBuffer("select duration_, comment_txt from bookmark_comment ");
			buf.append("where business_object_sid = " + commentVO.getElementSID());
			buf.append(" and business_object_code = '" + commentVO.getElementType() + "' ");
			buf.append("and user_sid = " + loggedInUserSID); 
			buf.append(" and ((duration_ = 'I') ");
			buf.append("or (duration_ = 'C' and refmonth_='" + refMth + "'))");			
					
			String sql = buf.toString();
			//System.out.println("addBookmarkComments sql 1 == " + sql);
			
			pstmt = con.createPreparedStatement(sql);
			rs = con.executeQuery(pstmt);
					
			String dbBookMarkType = null;
			String dbCommentTxt = null;
			
			while (rs.next()) {
				dbBookMarkType = rs.getString("duration_");					
				dbCommentTxt = rs.getString("comment_txt");
				if(dbCommentTxt != null && dbCommentTxt.length() > 0) {
					dbCommentTxt = dbCommentTxt.replaceAll("\"", "'");
				}
			}
			
			rs.close();
			pstmt.close();
				
			if (commentVO.getDuration()!=null 
					&& commentVO.getDuration().length()>0) {
			
				if (dbBookMarkType==null) {
					
					//insert
					buf = new StringBuffer("insert into bookmark_comment ");
							buf.append("(bookmark_comment_sid, business_object_sid, business_object_code, duration_, refmonth_, business_object_id, ");
							buf.append("user_sid, user_lan_name, business_role_code, created_date, subsystem_code, comment_txt) values (");
							buf.append("SEQ_BOOKMARK_COMMENT.nextval, " + commentVO.getElementSID() + ", '" + commentVO.getElementType() + "', '");
							buf.append(commentVO.getDuration() + "', " + commentVO.getCalcInRefMth() + ", '" + commentVO.getElementCode() + "', ");
							buf.append(loggedInUserSID + ", '" +  loggedInUserLanName + "', '" + loggedInRoleCode + "', sysdate, '" + commentVO.getSubSystem() + "',");
							
					if (commentVO.getCommentText()!=null) {						
							
						String txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";
						newCommentTxt = commentVO.getCommentText() + " " + txtToAppend;
						newCommentTxt = newCommentTxt.replaceAll("'", "\"");
						buf.append("'" + newCommentTxt + "')");
					} else {
						buf.append("NULL)");
					}
					
					sql = buf.toString();
					//System.out.println("editBookmarkComments insert sql == " + sql);
					
					pstmt = con.createPreparedStatement(sql);
					con.executeUpdate(pstmt);
			
					
				} else if  (commentVO.getCommentText()==null || commentVO.getCommentText().trim().length()==0) { 
					
				
					buf = new StringBuffer("update bookmark_comment "); 
							buf.append("set duration_ = '" + commentVO.getDuration() + "', ");
							buf.append("created_date = sysdate, ");
							
							/*if ("I".equals(commentVO.getDuration())) {
								buf.append("refmonth_ = NULL");
							} else {*/
								buf.append("refmonth_ = " + commentVO.getCalcInRefMth());
							//}										
							
							//if (!(dbCommentTxt.equals(commentVO.getCommentText()))) {
								
								//if (commentVO.getCommentText()==null || commentVO.getCommentText().trim().length()==0) {
									buf.append(", comment_txt = NULL");
								/*} else {
									String txtToAppend = "--Edited on " + dateTime + " by " + loggedInUserLanName;						
									newCommentTxt = commentVO.getCommentText() + txtToAppend;						
									buf.append(", comment_txt = '" + newCommentTxt + "'");
								}
							}*/
							
							buf.append(" where business_object_sid = " + commentVO.getElementSID()); 
							buf.append(" and user_sid = " + loggedInUserSID);
							buf.append(" and business_object_code = '" + commentVO.getElementType() + "' ");						
						
						sql = buf.toString();
						//System.out.println("editBookmarkComments update sql == " + sql);
						
						pstmt = con.createPreparedStatement(sql);
						con.executeUpdate(pstmt);
										
						
				} else if ((!(dbBookMarkType.equals(commentVO.getDuration()))) || (!(commentVO.getCommentText().equals(dbCommentTxt)))) {
					buf = new StringBuffer("update bookmark_comment "); 
					buf.append("set duration_ = '" + commentVO.getDuration() + "', ");
					buf.append("created_date = sysdate, ");
					
					/*if ("I".equals(commentVO.getDuration())) {
						buf.append("refmonth_ = NULL");
					} else {*/
						buf.append("refmonth_ = " + commentVO.getCalcInRefMth());
					//}										
					
					if (dbCommentTxt==null) {
						String txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";						
						newCommentTxt = commentVO.getCommentText() + " " + txtToAppend;	
						newCommentTxt = newCommentTxt.replaceAll("'", "\"");
						
						buf.append(", comment_txt = '" + newCommentTxt + "'");
						
					} else if(dbCommentTxt!=null && (!(dbCommentTxt.equals(commentVO.getCommentText())))) {
						
						String txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";						
						newCommentTxt = commentVO.getCommentText() + " " + txtToAppend;			
						newCommentTxt = newCommentTxt.replaceAll("'", "\"");
						buf.append(", comment_txt = '" + newCommentTxt + "'");
						
					}
					
					buf.append(" where business_object_sid = " + commentVO.getElementSID()); 
					buf.append(" and user_sid = " + loggedInUserSID);
					buf.append(" and business_object_code = '" + commentVO.getElementType() + "' ");						
				
					sql = buf.toString();
					//System.out.println("editBookmarkComments update sql == " + sql);
					
					pstmt = con.createPreparedStatement(sql);
					con.executeUpdate(pstmt);
				}
		
			} else {
				
				if (dbBookMarkType!=null) {
								
					buf = new StringBuffer("delete bookmark_comment");
											buf.append(" where business_object_sid = " + commentVO.getElementSID()); 
											buf.append(" and user_sid = " + loggedInUserSID);
											buf.append(" and business_object_code = '" + commentVO.getElementType() + "' ");		
					
					sql = buf.toString();
					//System.out.println("editBookmarkComments delete sql == " + sql);
					
					pstmt = con.createPreparedStatement(sql);
					con.executeUpdate(pstmt);
									
				}
			
			}
			
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in editBookmarkComments: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in editBookmarkComments: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
		
	}
	
	
	public void editStaticComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, StaticCommentVO commentVO) 
			throws ApplicationException {
		
		DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;				
			
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss aa");
		Date date = new Date();
		String dateTime = sdf.format(date);		
		String newCommentTxt = "";		
		
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);
			
			buf = new StringBuffer("select static_comment_sid, comment_lob from static_comment ");
			buf.append("where business_object_sid = " + commentVO.getElementSID());
			buf.append(" and business_object_code = '" + commentVO.getElementType() + "'");	
					
			String sql = buf.toString();
			//System.out.println("editStaticComments sql 1 == " + sql);
			
			pstmt = con.createPreparedStatement(sql);
			rs = con.executeQuery(pstmt);
					
			int commentSID = 0;
			String dbCommentTxt = null;
			
			while (rs.next()) {
				commentSID = rs.getInt("static_comment_sid");					
				dbCommentTxt = rs.getString("comment_lob");
				if(dbCommentTxt != null && dbCommentTxt.length() > 0) {
					dbCommentTxt = dbCommentTxt.replaceAll("\"", "'");
				}
			}
			
			rs.close();
			pstmt.close();
				
			if (commentVO.getCommentText()!=null 
					&& commentVO.getCommentText().length()>0) {
			
				if (commentSID==0) {
					
					//insert
					buf = new StringBuffer("insert into static_comment ");
							buf.append("(static_comment_sid, business_object_sid, business_object_code, business_object_id, ");
							buf.append("user_sid, user_lan_name, business_role_code, created_date, subsystem_code, comment_lob) values (");
							buf.append("SEQ_STATIC_COMMENT.nextval, " + commentVO.getElementSID() + ", '" + commentVO.getElementType() + "', '");
							buf.append(commentVO.getElementCode() + "', ");
							buf.append(loggedInUserSID + ", '" +  loggedInUserLanName + "', '" + loggedInRoleCode + "', sysdate, '" + commentVO.getSubSystem() + "',");
							
					
						
					
					String txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";
					newCommentTxt = commentVO.getCommentText() + " " + txtToAppend;
					newCommentTxt = newCommentTxt.replaceAll("'", "\"");
					buf.append("'" + newCommentTxt + "')");
					
					
					sql = buf.toString();
					//System.out.println("editStaticComments insert sql == " + sql);					

					pstmt = con.createPreparedStatement(sql);
					con.executeUpdate(pstmt);			
					
				} else {
					
					buf = new StringBuffer("update static_comment "); 
							buf.append("set user_sid = " + loggedInUserSID + ", user_lan_name = '" + loggedInUserLanName + "', ");
							buf.append("created_date = sysdate, business_role_code = '" + loggedInRoleCode + "'");			
							
							if (!(dbCommentTxt.equals(commentVO.getCommentText()))) {
								String txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";
								
								newCommentTxt = commentVO.getCommentText() + " " + txtToAppend;;
								newCommentTxt = newCommentTxt.replaceAll("'", "\"");
								
								buf.append(", comment_lob = '" + newCommentTxt + "'");
							}
							
							buf.append(" where business_object_sid = " + commentVO.getElementSID());							
							buf.append(" and business_object_code = '" + commentVO.getElementType() + "' ");						
						
					sql = buf.toString();
					//System.out.println("editStaticComments update sql == " + sql);
					pstmt = con.createPreparedStatement(sql);
					con.executeUpdate(pstmt);		
						
				}
		
			} else {
				
				if (commentSID!=0) {
								
					buf = new StringBuffer("delete static_comment");
											buf.append(" where business_object_sid = " + commentVO.getElementSID()); 											
											buf.append(" and business_object_code = '" + commentVO.getElementType() + "' ");		
					
					sql = buf.toString();
					//System.out.println("editStaticComments delete sql == " + sql);
					pstmt = con.createPreparedStatement(sql);
					con.executeUpdate(pstmt);							
				}
			
			}
			
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in editStaticComments: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in editStaticComments: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
		
	}
	
	
	public void deleteStaticComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, int elementSID, String elementType) 
			throws ApplicationException {
		
		DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;				
			
				
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);		
										
			buf = new StringBuffer("delete static_comment");
									buf.append(" where business_object_sid = " + elementSID); 											
									buf.append(" and business_object_code = '" + elementType + "' ");		
			
			String sql = buf.toString();
			//System.out.println("deleteStaticComments delete sql == " + sql);
			pstmt = con.createPreparedStatement(sql);
			con.executeUpdate(pstmt);				
			
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in editStaticComments: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in editStaticComments: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
		
	}
		
	
	
	private void addRefMthCommentsForMth(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, RefMthCommentVO commentVO, int calcForRefMth) 
			throws ApplicationException {
		
		DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;
				
		int refMth = getRefMth();
		String commentTxt = "";
		int commentSID = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss aa");
		Date date = new Date();
		String dateTime = sdf.format(date);		
		String newCommentTxt = "";
		//System.out.println("commentVO == "+commentVO.getCommentText());	
		
		if (commentVO.getCommentText()==null || commentVO.getCommentText().trim().length()==0) return;
		
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);
			buf = new StringBuffer("select monthly_comment_sid, comment_txt from monthly_comment ");
					buf.append("where business_object_sid = " + commentVO.getElementSID() + " and business_object_code = '" + commentVO.getElementType());
					buf.append("' and subsystem_code = '" + SubsystemIF.IXREV + "' and refmonth_ = " + refMth + " and calc_for_refmonth = " + calcForRefMth);
					
			String sql = buf.toString();
			//System.out.println("addRefMthComments sql 1 == " + sql);

			pstmt = con.createPreparedStatement(sql);
			rs = con.executeQuery(pstmt);
			
			while (rs.next()) {
				commentSID = rs.getInt("monthly_comment_sid");			
				commentTxt = rs.getString("comment_txt");
				//System.out.println("commentTxt from db == " + commentTxt);
			}
			
			rs.close();
			//pstmt.close();
			
			if (commentSID == 0) { //insert
				
				//String txtToAppend = "[Created on " + dateTime + " by " + loggedInUserLanName + "]";
				String txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";
				commentTxt = commentVO.getCommentText() + " " + txtToAppend;
				//System.out.println("commentTxt b4 replace == " + commentTxt);
				
				commentTxt = commentTxt.replaceAll("'", "\"");
				//System.out.println("commentTxt aft replace == " + commentTxt);
				
				buf = new StringBuffer("insert into monthly_comment (monthly_comment_sid, business_object_sid, business_object_code, refmonth_ , calc_for_refmonth, business_object_id, ");
				buf.append("user_sid, user_lan_name, business_role_code, created_date, subsystem_code, comment_txt) ");
				buf.append("values (");
				buf.append("SEQ_MONTHLY_COMMENT.nextval, " + commentVO.getElementSID() + ", '" + commentVO.getElementType() + "', ");
				buf.append(refMth + ", " + calcForRefMth + ", '" + commentVO.getElementCode() + "', " + loggedInUserSID + ", '");
				buf.append(loggedInUserLanName + "', '" + loggedInRoleCode + "', sysdate, '" + SubsystemIF.IXREV + "', '" + commentTxt + "')");
						
			/*	(1114, 105979, 'STRAT', 201307, 201307, 'Q00010', 101846, 'kook_n', 'IA', 
				TO_DATE('11-July-13 09.44.41 AM', 'DD-MON-YY HH.MI.SS AM'), 'IXREV', '201307 comment made in 201307');*/
				
				sql = buf.toString();
				//System.out.println("addRefMthComments insert sql == " + sql);

				pstmt = con.createPreparedStatement(sql);
				con.executeUpdate(pstmt);

						
			} else { //update
				
				String txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";
				//commentTxt = commentVO.getCommentText() + txtToAppend + "\n " + commentTxt;
				
				newCommentTxt = commentVO.getCommentText() + " " + txtToAppend + "\n " + commentTxt;
				//System.out.println("newCommentTxt from db == " + newCommentTxt);
				newCommentTxt = newCommentTxt.replaceAll("'", "\"");
				
				buf = new StringBuffer("update monthly_comment ");
				buf.append("set user_sid = " + loggedInUserSID);
				buf.append(", user_lan_name = '" + loggedInUserLanName + "'");
				buf.append(", business_role_code = '" + loggedInRoleCode + "'");
				buf.append(", created_date = sysdate ");
				buf.append(", comment_txt = '" + newCommentTxt  + "'");
				buf.append(" where monthly_comment_sid = " + commentSID);
				
				sql = buf.toString();
				//System.out.println("addRefMthComments update sql == " + sql);
				

				pstmt = con.createPreparedStatement(sql);
				con.executeUpdate(pstmt);								
				
			}			
			
				
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in addRefMthComments: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in addRefMthComments: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
		
	}
	
	
	public void editRefMthComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, RefMthCommentVO commentVO, List<Integer> mthsToSaveForList) 
			throws ApplicationException {
		//System.out.println("****mthsToSaveForList = "+mthsToSaveForList);
		editRefMthCommentsForMth(loggedInUserSID, loggedInUserLanName, loggedInRoleCode, commentVO, commentVO.getCalcForRefMth()); 
		if (mthsToSaveForList!=null && mthsToSaveForList.size()>0) {
			
			int mthsToAppendTo = 0;
			
			Iterator it = mthsToSaveForList.iterator();
			while (it.hasNext()) {
				mthsToAppendTo = (Integer) it.next();
				addRefMthCommentsForMth(loggedInUserSID, loggedInUserLanName, loggedInRoleCode, commentVO, mthsToAppendTo); 
			}
		}		
		
	}
	
	//for IR gen sec
	public void editRefMthCommentsForMth(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, RefMthCommentVO commentVO) 
			throws ApplicationException {
		
		DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;				
		
		String commentTxt = "";		
		int commentSID = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss aa");
		Date cal = new Date();
		String dateTime = sdf.format(cal);		
		String sql = null;	
		
		//System.out.println("commentVO == " + commentVO);
		//if new comment text is blank, delete the comment
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);		
			
			buf = new StringBuffer("select monthly_comment_sid, comment_txt from monthly_comment ");  
			buf.append("where calc_for_refmonth = " + commentVO.getCalcForRefMth());
			buf.append(" and refmonth_ = " + commentVO.getCalcInRefMth());
			buf.append(" and business_object_sid = " + commentVO.getElementSID());
			buf.append(" and business_object_code = '" + commentVO.getElementType() + "' ");
			//buf.append(" order by refmonth_ desc, created_date desc");
					
			sql = buf.toString();
			//System.out.println("getRefMthComments sql == " + sql);
			pstmt = con.createPreparedStatement(sql);
			rs = con.executeQuery(pstmt);
			
			while (rs.next()) {				
				commentSID = rs.getInt("monthly_comment_sid");
				commentTxt = rs.getString("comment_txt");		
				if(commentSID != 0 && commentTxt != null && commentTxt.length() > 0) {
					//replace double quote with single quote.
					commentTxt = commentTxt.replaceAll("\"", "'");
				}
			}			
			
			//System.out.println("commentVO.getCommentText() == " + commentVO.getCommentText());
			
			if (commentSID == 0) { //insert
				
				//System.out.println("commentSID == 0");
				
				if (commentVO.getCommentText()!=null && commentVO.getCommentText().trim().length()>0) {
					//System.out.println("commentSID == 0 n has txt");
									
					String txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";
					commentTxt = commentVO.getCommentText() + " " + txtToAppend;
					commentTxt = commentTxt.replaceAll("'", "\"");
					
					buf = new StringBuffer("insert into monthly_comment (monthly_comment_sid, business_object_sid, business_object_code, refmonth_ , calc_for_refmonth, business_object_id, ");
					buf.append("user_sid, user_lan_name, business_role_code, created_date, subsystem_code, comment_txt) ");
					buf.append("values (");
					buf.append("SEQ_MONTHLY_COMMENT.nextval, " + commentVO.getElementSID() + ", '" + commentVO.getElementType() + "', ");
					buf.append(commentVO.getCalcInRefMth() + ", " + commentVO.getCalcForRefMth() + ", '" + commentVO.getElementCode() + "', " + loggedInUserSID + ", '");
					buf.append(loggedInUserLanName + "', '" + loggedInRoleCode + "', sysdate, '" + SubsystemIF.IXREV + "', '" + commentTxt + "')");
							
					
					sql = buf.toString();
					//System.out.println("addRefMthComments insert sql == " + sql);
	
					pstmt = con.createPreparedStatement(sql);
					con.executeUpdate(pstmt);
				}

						
			} else { 
				//System.out.println("commentSID != 0");
				//delete
				if (commentVO.getCommentText()==null || commentVO.getCommentText().trim().length()==0) {
					//System.out.println("commentSID != 0 n no txt");
					
					buf = new StringBuffer("delete monthly_comment ");				
					buf.append(" where monthly_comment_sid = " + commentSID);
					
					sql = buf.toString();
					//System.out.println("editRefMthComments delete sql == " + sql);

					pstmt = con.createPreparedStatement(sql);
					con.executeUpdate(pstmt);
					
				} else if (!(commentVO.getCommentText().equals(commentTxt))) {
					//System.out.println("commentSID != 0 n has txt");
					//update
					
					String txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";
					//commentTxt = commentVO.getCommentText() + txtToAppend + "\n " + commentTxt;
					//String newCommentTxt = commentVO.getCommentText() + txtToAppend + "\n " + commentTxt;
					String newCommentTxt = commentVO.getCommentText() + " " + txtToAppend ;
					//System.out.println("newCommentTxt from db == " + newCommentTxt);
					
					newCommentTxt = newCommentTxt.replaceAll("'", "\"");
					
					buf = new StringBuffer("update monthly_comment ");
					buf.append("set user_sid = " + loggedInUserSID);
					buf.append(", user_lan_name = '" + loggedInUserLanName + "'");
					buf.append(", business_role_code = '" + loggedInRoleCode + "'");
					buf.append(", created_date = sysdate ");
					buf.append(", comment_txt = '" + newCommentTxt  + "'");
					buf.append(" where monthly_comment_sid = " + commentSID);
					
					sql = buf.toString();
					//System.out.println("addRefMthComments update sql == " + sql);

					pstmt = con.createPreparedStatement(sql);
					con.executeUpdate(pstmt);
				}
			}		
				
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in editRefMthCommentsForMth: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in editRefMthCommentsForMth: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
				
	}
	
	
	
	private void editRefMthCommentsForMth(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, RefMthCommentVO commentVO, int calcForRefMth) 
			throws ApplicationException {
		
		DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;				
		
		String commentTxt = "";		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss aa");
		Date cal = new Date();
		String dateTime = sdf.format(cal);		
		String sql = null;
		
		//if new comment text is blank, delete the comment
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);
			
			
			if (commentVO.getCommentText() == null || commentVO.getCommentText().trim().length() == 0) { //delete
				
				buf = new StringBuffer("delete monthly_comment ");				
				buf.append(" where monthly_comment_sid = " + commentVO.getCommentSID());
				
				sql = buf.toString();
				//System.out.println("editRefMthComments delete sql == " + sql);

				pstmt = con.createPreparedStatement(sql);
				con.executeUpdate(pstmt);
						
			} else { //update
								
				String txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";
				commentTxt = commentVO.getCommentText() + " " + txtToAppend;
				commentTxt = commentTxt.replaceAll("'", "\"");
				
				buf = new StringBuffer("update monthly_comment ");
				buf.append("set user_sid = " + loggedInUserSID);
				buf.append(", user_lan_name = '" + loggedInUserLanName + "' ");
				buf.append(", business_role_code = '" + loggedInRoleCode + "' ");
				buf.append(", created_date = sysdate ");
				buf.append(", comment_txt = '" + commentTxt + "' ");
				buf.append(" where monthly_comment_sid = " + commentVO.getCommentSID());
				
				sql = buf.toString();
				//System.out.println("editRefMthComments update sql == " + sql);

				pstmt = con.createPreparedStatement(sql);
				con.executeUpdate(pstmt);
			}			
			
				
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in editRefMthCommentsForMth: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in editRefMthCommentsForMth: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }			
		
		
	}
	
	
	public void deleteRefMthComments(int loggedInUserSID, String loggedInRoleCode, RefMthCommentVO commentVO)
		throws ApplicationException {
			
			DBConnection con = null;
			PreparedStatement pstmt = null;		
			ResultSet rs = null;
			StringBuffer buf = null;			
			
			String sql = null;
			
			try {
				con = DBConnectionFactory.getConnection(dataSourceName);			
				buf = new StringBuffer("delete monthly_comment ");				
				buf.append(" where monthly_comment_sid = " + commentVO.getCommentSID());
				
				sql = buf.toString();
				//System.out.println("deleteRefMthComments sql == " + sql);

				pstmt = con.createPreparedStatement(sql);
				con.executeUpdate(pstmt);
							
					
			 } catch(SQLException se){
				 se.printStackTrace();
		 		 throw new ApplicationException("Exception in deleteRefMthComments: "+se.getMessage(), se);
			 } catch (Exception ex){
				 ex.printStackTrace();
				 throw new ApplicationException("Exception in deleteRefMthComments: "+ex.getMessage(), ex);
			 } finally { 		 
				 closeResources(rs, pstmt, con);
			 }	
			
	}
	
	//have to keep editable block separate from non-editable block
	public List<InitiationCommentVO> getInitiationComments(int elementSID, String elementType, String programType, String loggedInSubSystem, String forSubSystem, int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode) 
			throws ApplicationException {
		
		DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;				
		
		String commentTxt = "";
		int commentSID = 0;
		String origSubsytemCode = null;
		String origRoleCode = null;		
		String elementCode = null;
		
		boolean editableFlag = false;
			
		InitiationCommentVO commentVO = null;
		List<InitiationCommentVO> initCommentList = new ArrayList<InitiationCommentVO>(); //at most 2 entries, one editable and one not, non-edit can have multiple sids, edit can only have 1

		String nonEditableCommentTxt = "";		
		
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);
			buf = new StringBuffer("select init_comment_sid, business_object_id, subsystem_code, business_role_code, comment_lob from init_comment ");
					buf.append("where business_object_sid = " + elementSID + " and business_object_code = '" + elementType + "' ");
					buf.append("and program_type_code = '" + programType + "' order by init_comment_sid asc");
					
									
			String sql = buf.toString();
			//System.out.println("getInitiationComments sql 1 == " + sql);
			pstmt = con.createPreparedStatement(sql);
			rs = con.executeQuery(pstmt);
			
			while (rs.next()) {
				commentSID = rs.getInt("init_comment_sid");			
				elementCode = rs.getString("business_object_id"); 
				origSubsytemCode = rs.getString("subsystem_code");
				origRoleCode = rs.getString("business_role_code");				
				commentTxt = rs.getString("comment_lob");
				//System.out.println("commentTxt from db == " + commentTxt);
				
				editableFlag = CommentsUtil.canEditSameSID(elementType, programType, origSubsytemCode, loggedInSubSystem, origRoleCode, loggedInRoleCode);
				
				if (editableFlag==false) { //bundle them together
					if (nonEditableCommentTxt.equals("")) {
						nonEditableCommentTxt = nonEditableCommentTxt + commentTxt;
					} else {
						nonEditableCommentTxt = nonEditableCommentTxt + "\n " + commentTxt;
					}					
				} else {
					//todo : separate out fi comment from pi comment for su and rptr
					
					//not sure what attributes are required for displaying comments
					commentVO = new InitiationCommentVO(); //editable - only one, so create
					commentVO.setCommentSID(commentSID);
					commentVO.setCommentText(commentTxt);
					commentVO.setEditableFlag(true);
					commentVO.setElementSID(elementSID);
					commentVO.setElementType(elementType);
					commentVO.setElementCode(elementCode);
					commentVO.setProgramType(programType);
					commentVO.setSubSystem(origSubsytemCode);
					commentVO.setRoleCode(origRoleCode);	
					initCommentList.add(commentVO);
				}
				
			}
			
			if (nonEditableCommentTxt.length()>0) {
				//not sure what attributes are required for displaying comments
				commentVO = new InitiationCommentVO(); 			
				commentVO.setCommentText(nonEditableCommentTxt);
				commentVO.setEditableFlag(false);
				commentVO.setElementSID(elementSID);
				commentVO.setElementType(elementType);
				commentVO.setElementCode(elementCode);				
				initCommentList.add(commentVO);
			}
			
		
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in getInitiationComments: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in getInitiationComments: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
		
		return initCommentList;
	}
	

	public void addInitiationComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, InitiationCommentVO commentVO) 
			throws ApplicationException {
		
		DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;				
		
		String commentTxt = "";
		int commentSID = 0;
		String origSubsytemCode = null;
		String origRoleCode = null;
		String programType = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss aa");
		Date date = new Date();
		String dateTime = sdf.format(date);		
		String newCommentTxt = "";
		
		boolean canAddToSameSID = false;
		String txtToAppend = "";

		
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);
			buf = new StringBuffer("select init_comment_sid, program_type_code, subsystem_code, business_role_code, comment_lob from init_comment ");
					buf.append("where business_object_sid = " + commentVO.getElementSID() + " and business_object_code = '" + commentVO.getElementType() + "'");
									
			String sql = buf.toString();
			//System.out.println("addInitiationComments sql 1 == " + sql);
			pstmt = con.createPreparedStatement(sql);
			rs = con.executeQuery(pstmt);
			
			while (rs.next()) {
				commentSID = rs.getInt("init_comment_sid");			
				programType = rs.getString("program_type_code");
				origSubsytemCode = rs.getString("subsystem_code");
				origRoleCode = rs.getString("business_role_code");				
				commentTxt = rs.getString("comment_lob");
				//System.out.println("commentTxt from db == " + commentTxt);
			}
			
			rs.close();
			//pstmt.close();
			
			if (commentSID == 0) { //insert
				
				txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";
				commentTxt = commentVO.getCommentText() + " " + txtToAppend;
				
				buf = new StringBuffer("insert into init_comment (init_comment_sid, business_object_sid, business_object_code, program_type_code, subsystem_code, business_role_code, ");				
				buf.append("business_object_id, user_sid, user_lan_name, created_date, comment_lob) ");
				buf.append("values (");
				buf.append("SEQ_INIT_COMMENT.nextval, " + commentVO.getElementSID() + ", '" + commentVO.getElementType() + "', '");
				buf.append(commentVO.getProgramType() + "', '" + commentVO.getSubSystem() + "', '" + loggedInRoleCode + "', " + loggedInUserSID + ", '");
				buf.append(loggedInUserLanName + "', sysdate, '" + commentTxt + "')");
						
				sql = buf.toString();
				//System.out.println("addInitiationComments insert sql == " + sql);
				pstmt = con.createPreparedStatement(sql);
				con.executeUpdate(pstmt);

						
			} else { //update
				
				canAddToSameSID = CommentsUtil.canAddToSameSID(commentVO.getElementType(), commentVO.getProgramType(), origSubsytemCode, commentVO.getSubSystem(), origRoleCode, loggedInRoleCode);
				
				if (canAddToSameSID==false) {
				
					txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";
					commentTxt = commentVO.getCommentText() + " " + txtToAppend;
					
					buf = new StringBuffer("insert into init_comment (init_comment_sid, business_object_sid, business_object_code, program_type_code, subsystem_code, business_role_code, ");				
					buf.append("business_object_id, user_sid, user_lan_name, created_date, comment_lob) ");
					buf.append("values (");
					buf.append("SEQ_INIT_COMMENT.nextval, " + commentVO.getElementSID() + ", '" + commentVO.getElementType() + "', '");
					buf.append(commentVO.getProgramType() + "', '" + commentVO.getSubSystem() + "', '" + loggedInRoleCode + "', " + loggedInUserSID + ", '");
					buf.append(loggedInUserLanName + "', sysdate, '" + commentTxt + "')");
							
					sql = buf.toString();
					//System.out.println("addInitiationComments insert sql == " + sql);
					pstmt = con.createPreparedStatement(sql);
					con.executeUpdate(pstmt);
					
				} else{				
				
					txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";
					//newCommentTxt = commentVO.getCommentText() + txtToAppend + "\n " + commentTxt;
					
					newCommentTxt = commentTxt + " " + txtToAppend + "\n " + commentVO.getCommentText();
					
					//System.out.println("newCommentTxt == " + newCommentTxt);
					
					buf = new StringBuffer("update init_comment ");
					buf.append("set user_sid = " + loggedInUserSID);
					buf.append(", user_lan_name = '" + loggedInUserLanName + "'");
					buf.append(", business_role_code = '" + loggedInRoleCode + "'");
					buf.append(", program_type_code = '" + commentVO.getProgramType() + "'");
					buf.append(", subsystem_code = '" + commentVO.getSubSystem() + "'");
					buf.append(", created_date = sysdate ");
					buf.append(", comment_lob = '" + newCommentTxt  + "'");
					buf.append(" where init_comment_sid = " + commentSID);
					
					sql = buf.toString();
					//System.out.println("addInitiationComments update sql == " + sql);
					pstmt = con.createPreparedStatement(sql);
					con.executeUpdate(pstmt);
				}
			}			
			
				
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in addInitiationComments: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in addInitiationComments: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
		
	}
	

	public void editInitiationComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, InitiationCommentVO commentVO) 
			throws ApplicationException {
		
		DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss aa");
		Date date = new Date();
		String dateTime = sdf.format(date);		
		String newCommentTxt = "";
		String txtToAppend = "";
		
		try {
			con = DBConnectionFactory.getConnection(dataSourceName);			
				
			txtToAppend = "[" + loggedInUserLanName + " " + dateTime + "]";			
			newCommentTxt = commentVO.getCommentText() + " " + txtToAppend;
			
			//System.out.println("newCommentTxt == " + newCommentTxt);
			
			buf = new StringBuffer("update init_comment ");
			buf.append("set user_sid = " + loggedInUserSID);
			buf.append(", user_lan_name = '" + loggedInUserLanName + "'");
			buf.append(", business_role_code = '" + loggedInRoleCode + "'");
			buf.append(", program_type_code = '" + commentVO.getProgramType() + "'");
			buf.append(", subsystem_code = '" + commentVO.getSubSystem() + "'");
			buf.append(", created_date = sysdate ");
			buf.append(", comment_lob = '" + newCommentTxt  + "'");
			buf.append(" where init_comment_sid = " + commentVO.getCommentSID());
			
			String sql = buf.toString();
			//System.out.println("editInitiationComments update sql == " + sql);
			pstmt = con.createPreparedStatement(sql);
			con.executeUpdate(pstmt);
				
			
				
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in editInitiationComments: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in editInitiationComments: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
		
	}
	
	public void deleteInitiationComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, InitiationCommentVO commentVO) 
			throws ApplicationException {
		
		DBConnection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		StringBuffer buf = null;	

		try {
			con = DBConnectionFactory.getConnection(dataSourceName);				
			
			buf = new StringBuffer("delete init_comment ");
			buf.append("where init_comment_sid = " + commentVO.getCommentSID());			
			String sql = buf.toString();
			//System.out.println("deleteInitiationComments delete sql == " + sql);
			pstmt = con.createPreparedStatement(sql);
			con.executeUpdate(pstmt);		
				
		 } catch(SQLException se){
			 se.printStackTrace();
	 		 throw new ApplicationException("Exception in deleteInitiationComments: "+se.getMessage(), se);
		 } catch (Exception ex){
			 ex.printStackTrace();
			 throw new ApplicationException("Exception in deleteInitiationComments: "+ex.getMessage(), ex);
		 } finally { 		 
			 closeResources(rs, pstmt, con);
		 }	
		
	}
	
	
    
    private static void closeResources(ResultSet rs, Statement stmt, DBConnection conn) {	
	       if (rs != null) {
             try {
                 rs.close();
             } catch (SQLException e) { 
             	e.printStackTrace(); 
             }
         }
        
	       if (stmt != null) {
             try {
                 stmt.close();
             } catch (SQLException e) { 
             	e.printStackTrace(); 
             }
         }
        
	       if (conn != null) {             
                 conn.close();
	       }
            
        
	}

}
