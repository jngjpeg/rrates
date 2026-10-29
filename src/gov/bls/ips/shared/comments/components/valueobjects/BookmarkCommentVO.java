package gov.bls.ips.shared.comments.components.valueobjects;

public class BookmarkCommentVO extends CommentVO implements java.io.Serializable {
	
	private String duration; 
	private int calcInRefMth;
	
	public BookmarkCommentVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BookmarkCommentVO(int commentSID, int elementSID, String commentType,
			String elementType, String elementCode, String subSystem,
			int userSID, String userLanName, String createDate,
			String roleCode, String commentText, boolean isEditable, String duration, int calcInRefMth) {
		super(commentSID, elementSID, commentType, elementType, elementCode, subSystem,
				userSID, userLanName, createDate, roleCode, commentText, isEditable);
		this.duration = duration;
		this.calcInRefMth = calcInRefMth;	
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public int getCalcInRefMth() {
		return calcInRefMth;
	}

	public void setCalcInRefMth(int calcInRefMth) {
		this.calcInRefMth = calcInRefMth;
	}

	@Override
	public String toString() {
		return "BookmarkComments [duration=" + duration + ", calcInRefMth="
				+ calcInRefMth + ", getCommentSID()=" + getCommentSID()
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
