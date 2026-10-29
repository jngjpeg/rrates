package gov.bls.responseRates.properties;

public class PropertyFactory {

	public PropertyFactory() {
	}

	public static EnvInfo getEnvInfo() throws Exception {
		return PropertyBean.getInstance();
	}

	public static void refresh() throws Exception {
		PropertyBean.getInstance().refresh();
	}

	/*-End-*/
}