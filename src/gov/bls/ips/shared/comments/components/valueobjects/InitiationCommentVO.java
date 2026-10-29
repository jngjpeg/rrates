package gov.bls.ips.shared.comments.components.valueobjects;

public class InitiationCommentVO extends CommentVO implements java.io.Serializable {
	
	private String programType;	

	public InitiationCommentVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InitiationCommentVO(int commentSID, int elementSID, String commentType,
			String elementType, String elementCode, String subSystem,
			int userSID, String userLanName, String createDate,
			String roleCode, String commentText, boolean isEditable, String programType) {
		super(commentSID, elementSID, commentType, elementType, elementCode, subSystem,
				userSID, userLanName, createDate, roleCode, commentText, isEditable);
		this.programType = programType;
	}

	@Override
	public String toString() {
		return "InitiationCommentVO [programType=" + programType + ", getCommentSID()="
				+ getCommentSID() + ", getElementSID()=" + getElementSID()
				+ ", getCommentType()=" + getCommentType()
				+ ", getElementType()=" + getElementType()
				+ ", getElementCode()=" + getElementCode()
				+ ", getSubSystem()=" + getSubSystem() + ", getUserSID()="
				+ getUserSID() + ", getUserLanName()=" + getUserLanName()
				+ ", getCreateDate()=" + getCreateDate() + ", getRoleCode()="
				+ getRoleCode() + ", getCommentText()=" + getCommentText()
				+ ", getEditableFlag()=" + getEditableFlag() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	} 
	
	

}
