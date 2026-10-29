package gov.bls.ips.shared.comments.components.ejbs;

import gov.bls.ips.shared.comments.components.valueobjects.*;
import gov.bls.ips.shared.comments.exceptions.ApplicationException;

import javax.ejb.Remote;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Remote
public interface CommentsRemote {	
	
	public List<Integer> getRefMthList() throws ApplicationException;
	
	public List<Integer> getEditableRefMthList() throws ApplicationException;

    public Map<Integer, List<RefMthCommentVO>> getRefMthComments(int elementSID, String elementType, int startRefMth, int endRefMth) 
    	throws ApplicationException;
    
    public void addRefMthComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, 
    		RefMthCommentVO commentVO, List<Integer> mthsToSaveForList) 
			throws ApplicationException;

    public void editRefMthComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, 
    		RefMthCommentVO commentVO, List<Integer> mthsToSaveForList) 
			throws ApplicationException;
    
    public void deleteRefMthComments(int loggedInUserSID, String loggedInRoleCode, RefMthCommentVO commentVO)
    		throws ApplicationException;
    
    public String getStaticComments(int elementSID, String elementType, String subSystem) throws ApplicationException;
    
    public void editStaticComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, StaticCommentVO commentVO) 
			throws ApplicationException;
}
