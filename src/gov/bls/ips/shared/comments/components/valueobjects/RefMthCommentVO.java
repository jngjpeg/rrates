package gov.bls.ips.shared.comments.components.valueobjects;

public class RefMthCommentVO extends CommentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int calcInRefMth;
	private int calcForRefMth;
	private String calcForRefMthForUI;
	
	public RefMthCommentVO(int calcInRefMth, int calcForRefMth, String calcForRefMthForUI) {
		super();
		this.calcInRefMth = calcInRefMth;
		this.calcForRefMth = calcForRefMth;
		this.calcForRefMthForUI = calcForRefMthForUI;
	}

	public RefMthCommentVO() {
		super();		
	}
	
	public RefMthCommentVO(int commentSID, int elementSID, String commentType,
			String elementType, String elementCode, String subSystem, int userSID,
			String userLanName, String createDate, String roleCode,
			String commentText, boolean isEditable, int calcInRefMth, int calcForRefMth) {
		super(commentSID, elementSID, commentType, elementType, elementCode, subSystem, userSID,
				userLanName, createDate, roleCode, commentText, isEditable);
		this.calcInRefMth = calcInRefMth;
		this.calcForRefMth = calcForRefMth;		
	}

	public int getCalcInRefMth() {
		return calcInRefMth;
	}

	public String getCalcForRefMthForUI() {
		return calcForRefMthForUI;
	}

	public void setCalcForRefMthForUI(String calcForRefMthForUI) {
		this.calcForRefMthForUI = calcForRefMthForUI;
	}

	public void setCalcInRefMth(int calcInRefMth) {
		this.calcInRefMth = calcInRefMth;
	}

	public int getCalcForRefMth() {
		return calcForRefMth;
	}

	public void setCalcForRefMth(int calcForRefMth) {
		this.calcForRefMth = calcForRefMth;
	}


	@Override
	public String toString() {
		return "RefMthCommentVO [calcInRefMth=" + calcInRefMth
				+ ", calcForRefMth=" + calcForRefMth + ", calcForRefMthForUI="
				+ calcForRefMthForUI + ", getCommentSID()=" + getCommentSID()
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

	public RefMthCommentVO clone() {
		
		RefMthCommentVO copy = new RefMthCommentVO();
		copy.setCommentSID(super.getCommentSID());
		copy.setElementSID(super.getElementSID());
		copy.setCommentType(super.getCommentType());
		copy.setElementType(super.getElementType());
		copy.setElementCode(super.getElementCode());
		copy.setSubSystem(super.getSubSystem());
		copy.setUserSID(super.getUserSID());
		copy.setUserLanName(super.getUserLanName());
		copy.setCreateDate(super.getCreateDate());
		copy.setRoleCode(super.getRoleCode());
		copy.setCommentText(super.getCommentText());
		copy.setEditableFlag(super.getEditableFlag());
		copy.setCalcInRefMth(this.calcInRefMth);
		copy.setCalcForRefMth(this.calcForRefMth);
		//copy.setCalcForRefMthForUI(this.calcForRefMthForUI);
		return copy;
	}
	
}
