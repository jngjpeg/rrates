package gov.bls.responseRates.properties;

public class PropertyBean implements EnvInfo {
	private static PropertyBean propBean;
	private String usedFileName;

	private String enableNonSsoLogin;

	private PropertyBean() {

	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public static PropertyBean getInstance() throws Exception {
		if (propBean == null) {
			propBean = new PropertyBean();
			propBean.load();
		}
		return propBean;
	}

	/**
	 * load the Environment Bean with the OfomrProperty values
	 * 
	 * @returns PropertyBean
	 * @throws Exception
	 */
	public PropertyBean load() throws Exception {
		ResponseRatesProperty props = new ResponseRatesProperty();
		try {
			if (usedFileName != null)
				props.load(usedFileName);
			else
				usedFileName = props.load();
		} catch (Exception ex) {
			System.out.println("Exception in PropertyBean.load():" + ex.getMessage());
			throw new Exception(ex.getMessage());
		}
		return load(props);
	}

	/**
	 * Load the Environment Bean with the OfomrProperty Object
	 * 
	 * @param props - the OfomrProperty object
	 * @returns PropertyBean
	 * @throws Exception
	 */
	private PropertyBean load(ResponseRatesProperty props) throws Exception {
		loadEnvProperties(props);
		return getInstance();
	}

	/**
	 * loadEnvProperties
	 * 
	 * @param props - the OfomrProperty object
	 * @throws Exception
	 */
	private void loadEnvProperties(ResponseRatesProperty props) throws Exception {
		System.out.println("\n>>>>loading properties ...    \n");
		
		setEnableNonSsoLogin(props.getProperty("ENABLE_NON_SSO_LOGIN"));
		
		System.out.println("Property Load complete.");
	}

	/**
	 * Refresh the Environment Bean
	 * 
	 * @throws Exception
	 */
	public void refresh() throws Exception {
		load();
	}

	// Accessor Methods - To get specific properties
	public String getEnableNonSsoLogin() {
		return enableNonSsoLogin;
	}

	public void setEnableNonSsoLogin(String enableNonSsoLogin) {
		this.enableNonSsoLogin = enableNonSsoLogin;
	}

	/*-End-*/
}