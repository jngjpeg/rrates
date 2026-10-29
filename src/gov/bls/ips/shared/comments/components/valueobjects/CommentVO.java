package gov.bls.ips.shared.comments.components.valueobjects;

public class CommentVO implements java.io.Serializable {
	
	private int commentSID;
	private int elementSID;
	private String commentType; 
	private String elementType; 
	private String elementCode;
	private String subSystem; 	
	private int userSID; 
	private String userLanName;
	private String createDate; //use Calendar??
	private String roleCode; 		
	private String commentText;
	private boolean editableFlag; //depends on mth / user/ stageofproc /role
	
	public CommentVO() {
		super();
	}
	
	public CommentVO(int commentSID, int elementSID, String commentType,
			String elementType, String elementCode, String subSystem, int userSID,
			String userLanName, String createDate, String roleCode,
			String commentText, boolean editableFlag) {
		super();
		this.commentSID = commentSID;
		this.elementSID = elementSID;
		this.commentType = commentType;
		this.elementType = elementType;
		this.elementCode = elementCode;
		this.subSystem = subSystem;
		this.userSID = userSID;
		this.userLanName = userLanName;
		this.createDate = createDate;
		this.roleCode = roleCode;
		this.commentText = commentText;
		this.editableFlag = editableFlag;
	}

	public int getCommentSID() {
		return commentSID;
	}

	public void setCommentSID(int commentSID) {
		this.commentSID = commentSID;
	}

	public int getElementSID() {
		return elementSID;
	}

	public void setElementSID(int elementSID) {
		this.elementSID = elementSID;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}
	
	public String getElementCode() {
		return elementCode;
	}

	public void setElementCode(String elementCode) {
		this.elementCode = elementCode;
	}

	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	public int getUserSID() {
		return userSID;
	}

	public void setUserSID(int userSID) {
		this.userSID = userSID;
	}

	public String getUserLanName() {
		return userLanName;
	}

	public void setUserLanName(String userLanName) {
		this.userLanName = userLanName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public boolean getEditableFlag() {
		return editableFlag;
	}

	public void setEditableFlag(boolean editableFlag) {
		this.editableFlag = editableFlag;
	}

	@Override
	public String toString() {
		return "CommentVO [commentSID=" + commentSID + ", elementSID="
				+ elementSID + ", commentType=" + commentType
				+ ", elementType=" + elementType + ", elementCode="
				+ elementCode + ", subSystem=" + subSystem + ", userSID="
				+ userSID + ", userLanName=" + userLanName + ", createDate="
				+ createDate + ", roleCode=" + roleCode + ", commentText="
				+ commentText + ", editableFlag=" + editableFlag + "]";
	}

	
	public CommentVO clone() {
		CommentVO copy = new CommentVO();
		copy.commentSID = this.commentSID;
		copy.elementSID = this.elementSID;
		copy.commentType = this.commentType;
		copy.elementType = this.elementType;
		copy.elementCode = this.elementCode;
		copy.subSystem = this.subSystem;
		copy.userSID = this.userSID;
		copy.userLanName = this.userLanName;
		copy.createDate = this.createDate;
		copy.roleCode = this.roleCode;
		copy.commentText = this.commentText;
		copy.editableFlag = this.editableFlag;
		return copy;
	}
	

}
