package gov.bls.ips.shared.comments.components.valueobjects;

public class StaticCommentVO extends CommentVO implements java.io.Serializable {

	public StaticCommentVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StaticCommentVO(int commentSID, int elementSID, String commentType,
			String elementType, String elementCode, String subSystem,
			int userSID, String userLanName, String createDate,
			String roleCode, String commentText, boolean isEditable) {
		super(commentSID, elementSID, commentType, elementType, elementCode, subSystem,
				userSID, userLanName, createDate, roleCode, commentText, isEditable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "StaticCommentVO [getCommentSID()=" + getCommentSID()
				+ ", getElementSID()=" + getElementSID()
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
	
	

}
