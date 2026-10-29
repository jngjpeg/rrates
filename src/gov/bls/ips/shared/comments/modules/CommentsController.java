package gov.bls.ips.shared.comments.modules;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.naming.NamingException;

import gov.bls.ips.shared.comments.exceptions.ApplicationException;
import gov.bls.ips.shared.comments.exceptions.SystemException;
import gov.bls.ips.shared.comments.components.ejbs.CommentsRemote;
import gov.bls.ips.shared.comments.components.valueobjects.RefMthCommentVO;
import gov.bls.ips.shared.comments.components.valueobjects.StaticCommentVO;
import gov.bls.ips.shared.components.util.IPSServiceLocator;

public class CommentsController {
	
	private static final String commentsEJBJndiName = "IPS_CommentsEJB#gov.bls.ips.shared.comments.components.ejbs.CommentsRemote";
	

	public CommentsController() {}
	
	private CommentsRemote getEJB() throws SystemException { 

		CommentsRemote commentsRemote = null;
		try {			
			commentsRemote = (CommentsRemote) IPSServiceLocator.getEJBByJNDIName(commentsEJBJndiName);					
			return commentsRemote;

		} catch (Exception ae) {
			ae.printStackTrace();
			throw new SystemException(ae.getMessage(), ae);
		}
	}	
	

	public List<Integer> getRefMthList() throws ApplicationException, SystemException {
		List<Integer> refMthList = new ArrayList<Integer>();
																
		CommentsRemote commentsEJB = getEJB();
		
		try {
			refMthList = commentsEJB.getRefMthList();
		} catch(ApplicationException ae) {
			throw new ApplicationException("GetRefMthList :Error while retrieving ref mths - " + ae.getMessage());
		}
		
		return refMthList;
		
	}
		
	public List<Integer> getEditableRefMthList()  throws ApplicationException, SystemException {
		List<Integer> refMthList = new ArrayList<Integer>();
																	
		CommentsRemote commentsEJB = getEJB();
		
		try {
			refMthList = commentsEJB.getEditableRefMthList();
		} catch(ApplicationException ae) {
			throw new ApplicationException("getEditableRefMthList :Error while retrieving ref mths - " + ae.getMessage());
		}		
	
		return refMthList;
		
	}
	
	
	public Map<Integer, List<RefMthCommentVO>> getRefMthComments(int elementSID, String elementType, int startRefMth, int endRefMth) 
		throws ApplicationException, SystemException {
		
		Map commentVOMap = null;				
					
		CommentsRemote commentsEJB = getEJB();
		
		try {
			commentVOMap = commentsEJB.getRefMthComments(elementSID, elementType, startRefMth, endRefMth);
		} catch(ApplicationException ae) {
			throw new ApplicationException("GetRefMthComments:Error while retrieving ref mth comments - " + ae.getMessage());
		}
		
		return commentVOMap;
		
	}
	
	public String getStaticComments(int elementSID, String elementType, String subSystem) throws ApplicationException, SystemException {
		
		String staticComment = null;
						
		if (elementSID==0 || elementType==null) {
			throw new ApplicationException("GetStaticComments : Error while retrieving static comments since one of the input parameters is empty");		
		}												
		CommentsRemote commentsEJB = getEJB();
		
		try {
			staticComment = commentsEJB.getStaticComments(elementSID, elementType, subSystem);
		} catch(ApplicationException ae) {
			throw new ApplicationException("GetStaticComments:Error while retrieving static comments - " + ae.getMessage());
		}			
	
		if (staticComment==null) {
			return "";
		} else {
			return staticComment;
		}
		
	}
	
	public void editStaticComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, StaticCommentVO commentVO) 
			throws ApplicationException, SystemException {		
						
		if (loggedInUserSID==0 || loggedInUserLanName==null || loggedInRoleCode==null ||  commentVO==null) {
			throw new ApplicationException("EditStaticComments : Error while saving static comments since one of the input parameters is empty");		
		}												
		CommentsRemote commentsEJB = getEJB();
		
		try {
			commentsEJB.editStaticComments(loggedInUserSID, loggedInUserLanName, loggedInRoleCode, commentVO);
		} catch(ApplicationException ae) {
			throw new ApplicationException("EditStaticComments :Error while retrieving static comments - " + ae.getMessage());
		}
		
		
	}
		

	public void addRefMthComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, 
    		RefMthCommentVO commentVO, List<Integer> mthsToSaveForList) throws ApplicationException, SystemException {		
						
		if (loggedInUserSID==0 || loggedInUserLanName==null || loggedInRoleCode==null || commentVO==null) {
			throw new ApplicationException("AddRefMthComments : Error while adding ref mth comments since one of the input parameters is empty");
		}													
		CommentsRemote commentsEJB = getEJB();
		
		try {
			commentsEJB.addRefMthComments(loggedInUserSID, loggedInUserLanName, loggedInRoleCode, commentVO, mthsToSaveForList);
		} catch(ApplicationException ae) {
			throw new ApplicationException("AddRefMthComments : Error while adding ref mth comments - " + ae.getMessage());
		}
		
		
	}
		
	public void editRefMthComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, 
    		RefMthCommentVO commentVO, List<Integer> mthsToSaveForList) throws ApplicationException, SystemException {				
						
		if (loggedInUserSID==0 || loggedInUserLanName==null || loggedInRoleCode==null || commentVO==null) {
			throw new ApplicationException("EditRefMthComments : Error while editing ref mth comments since one of the input parameters is empty");
		}													
		CommentsRemote commentsEJB = getEJB();
		
		try {
			commentsEJB.editRefMthComments(loggedInUserSID, loggedInUserLanName, loggedInRoleCode, commentVO, mthsToSaveForList);
		} catch(ApplicationException ae) {
			throw new ApplicationException("EditRefMthComments : Error while editing ref mth comments - " + ae.getMessage());
		}		
		
	}	

	
	public void deleteRefMthComments(int loggedInUserSID, String loggedInRoleCode, RefMthCommentVO commentVO) throws ApplicationException, SystemException {				
						
		if (loggedInUserSID==0 || loggedInRoleCode==null || commentVO==null) {
			throw new ApplicationException("DeleteRefMthComments : Error while deleting ref mth comments since one of the input parameters is empty");
		}													
		CommentsRemote commentsEJB = getEJB();
		
		try {
			commentsEJB.deleteRefMthComments(loggedInUserSID, loggedInRoleCode, commentVO);
		} catch(ApplicationException ae) {
			throw new ApplicationException("EditRefMthComments : Error while deleting ref mth comments - " + ae.getMessage());
		}		
		
		}	
	
	
}

