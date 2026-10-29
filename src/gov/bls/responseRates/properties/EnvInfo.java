package gov.bls.responseRates.properties;

//Core imports
import java.io.Serializable;

public interface EnvInfo extends Serializable {
	public abstract String getEnableNonSsoLogin();
}
