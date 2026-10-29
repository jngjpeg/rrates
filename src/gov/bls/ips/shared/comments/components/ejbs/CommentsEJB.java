package gov.bls.ips.shared.comments.components.ejbs;

import java.util.List;
import java.util.Map;

import gov.bls.ips.shared.comments.exceptions.ApplicationException;
import gov.bls.ips.shared.comments.components.ejbs.CommentsRemote;
import gov.bls.ips.shared.comments.components.valueobjects.CommentVO;
import gov.bls.ips.shared.comments.components.valueobjects.RefMthCommentVO;
import gov.bls.ips.shared.comments.components.valueobjects.StaticCommentVO;
import gov.bls.ips.shared.comments.components.dao.*;

import javax.ejb.Stateless;

@Stateless(mappedName = "IPS_CommentsEJB")
public class CommentsEJB implements CommentsRemote {
	
	public List<Integer> getRefMthList() throws ApplicationException {
		 
		 CommentsDAO dao = CommentsDAO.getObject();
		 return dao.getRefMthList();
	}
	
	public List<Integer> getEditableRefMthList() throws ApplicationException {
		 
		 CommentsDAO dao = CommentsDAO.getObject();
		 return dao.getEditableRefMthList();
	}

	public Map<Integer, List<RefMthCommentVO>> getRefMthComments(int elementSID, String elementType, int startRefMth, int endRefMth) 
		    	throws ApplicationException {
		 
		 CommentsDAO dao = CommentsDAO.getObject();
		 return dao.getRefMthComments(elementSID, elementType, startRefMth, endRefMth);
	}
		    
    public void addRefMthComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, 
    		RefMthCommentVO commentVO, List<Integer> mthsToSaveForList) 
			throws ApplicationException {
    	
    	CommentsDAO dao = CommentsDAO.getObject();
		dao.addRefMthComments(loggedInUserSID, loggedInUserLanName, loggedInRoleCode, commentVO, mthsToSaveForList);
    }

	public void editRefMthComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, 
		    		RefMthCommentVO commentVO, List<Integer> mthsToSaveForList) 
					throws ApplicationException {
		CommentsDAO dao = CommentsDAO.getObject();
		dao.editRefMthComments(loggedInUserSID, loggedInUserLanName, loggedInRoleCode, commentVO, mthsToSaveForList);
	}
		    
    public void deleteRefMthComments(int loggedInUserSID, String loggedInRoleCode, RefMthCommentVO commentVO)
    		throws ApplicationException {
    	
    	CommentsDAO dao = CommentsDAO.getObject();
    	dao.deleteRefMthComments(loggedInUserSID, loggedInRoleCode, commentVO);
    }
		    
    public String getStaticComments(int elementSID, String elementType, String subSystem) throws ApplicationException {
    	
    	CommentsDAO dao = CommentsDAO.getObject();
    	return dao.getStaticComments(elementSID, elementType, subSystem);
    }
    
    public void editStaticComments(int loggedInUserSID, String loggedInUserLanName, String loggedInRoleCode, StaticCommentVO commentVO) 
			throws ApplicationException {
    	CommentsDAO dao = CommentsDAO.getObject();
    	dao.editStaticComments(loggedInUserSID, loggedInUserLanName, loggedInRoleCode, commentVO);
    }
}
