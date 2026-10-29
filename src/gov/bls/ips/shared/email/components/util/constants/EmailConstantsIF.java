package gov.bls.ips.shared.email.components.util.constants;

public interface EmailConstantsIF {
	
	public static final String EMAIL_PROTOCOL_PARAM = "mail.transport.protocol";
	public static final String EMAIL_HOST = "mail.smtp.host";
	public static final String MIME_TYPE= "mimeType";
	public static final String MIME_TYPE_PLAIN= "text/plain";
	public static final String MIME_TYPE_HTML= "text/html";
	 
	/**
	 * Index Review subsystem and tasks that require email
	 */
	public static final String Index_Review = "IXREV"; 
	public static final String Finalize_Indexes = "FIDX";
	public static final String Un_Finalize_Indexes = "UN_FIDX";
	
	/**
	 * Scheduler subsystem and tasks that require email
	 */
	public static final String Scheduler = "SCHD"; 
	public static final String IC_Scheduler = "IC_SCHD";
	
	/**
	 * BE subsystem and tasks that require email
	 */
	public static final String BE = "BE"; 
	public static final String BE_MAPPINGS = "BE_MAPPINGS";
	
	
}
