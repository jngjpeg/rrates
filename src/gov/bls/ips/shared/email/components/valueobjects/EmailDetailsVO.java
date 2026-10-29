package gov.bls.ips.shared.email.components.valueobjects;

public class EmailDetailsVO {
	
	private String subsystemCode;
	private String featureCode;
	private String recipientGroupCode;
	private String recipientEmailAddresses;
	private String senderEmailAddress;
	private String subject;
	private String bodyText;
	private String contentType;
	
	public EmailDetailsVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailDetailsVO(String subsystemCode, String featureCode,
			String recipientGroupCode, String recipientEmailAddresses,
			String senderEmailAddress, String subject, String bodyText,
			String contentType) {
		super();
		this.subsystemCode = subsystemCode;
		this.featureCode = featureCode;
		this.recipientGroupCode = recipientGroupCode;
		this.recipientEmailAddresses = recipientEmailAddresses;
		this.senderEmailAddress = senderEmailAddress;
		this.subject = subject;
		this.bodyText = bodyText;
		this.contentType = contentType;
	}

	public String getSubsystemCode() {
		return subsystemCode;
	}

	public void setSubsystemCode(String subsystemCode) {
		this.subsystemCode = subsystemCode;
	}

	public String getFeatureCode() {
		return featureCode;
	}

	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}

	public String getRecipientGroupCode() {
		return recipientGroupCode;
	}

	public void setRecipientGroupCode(String recipientGroupCode) {
		this.recipientGroupCode = recipientGroupCode;
	}

	public String getRecipientEmailAddresses() {
		return recipientEmailAddresses;
	}

	public void setRecipientEmailAddresses(String recipientEmailAddresses) {
		this.recipientEmailAddresses = recipientEmailAddresses;
	}

	public String getSenderEmailAddress() {
		return senderEmailAddress;
	}

	public void setSenderEmailAddress(String senderEmailAddress) {
		this.senderEmailAddress = senderEmailAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "EmailDetailsVO [subsystemCode=" + subsystemCode
				+ ", featureCode=" + featureCode + ", recipientGroupCode="
				+ recipientGroupCode + ", recipientEmailAddresses="
				+ recipientEmailAddresses + ", senderEmailAddress="
				+ senderEmailAddress + ", subject=" + subject + ", bodyText="
				+ bodyText + ", contentType=" + contentType + "]";
	}
}
