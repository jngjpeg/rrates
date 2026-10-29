/*******************************************************************************
*
* FILE : IPSConfigUtil.java
*
* DESCRIPTION :
*
* REVISION HISTORY :
*
* Date         Name            Release             Description
* -------  -----------------   ----------     ---------------------------------
* 03Oct2017  Selva Sathiah     Admin Sprint 42     original version
*
*  Copyright 2011 by All IPS-BLS .
* All rights reserved.
******************************************************************************/
package gov.bls.ips.shared.components.util;

import gov.bls.ips.shared.logger.components.IPSLogger;
import gov.bls.ips.shared.logger.components.config.IPSLoggerConfig;

public class IPSConfigUtil {
	public static String _CLASS_NAME = ClassFromStatic.getCurrentClass().getName();

	public static String getDataSourceNameBySubsystem(String subsystemCode) {
		String datasourceName = "";
		try {
			IPSLogger.logDebug(_CLASS_NAME, "getDataSourceNameBySubsystem:" + subsystemCode);
			datasourceName = IPSLoggerConfig._IPS_CONFIG_PROPERTIES.getString("_DATASOURCE_NAME_" + subsystemCode);
			IPSLogger.logDebug(_CLASS_NAME, "DatasourceName from IPSConfig_en.properties for Subsystem " + subsystemCode
					+ " :" + datasourceName);
		} catch (Exception e) {
			IPSLogger.logError(_CLASS_NAME,
					"Exception: getDataSourceNameBySubsystem - datasource name not available in IPSConfig.properties for subsystem:"
							+ subsystemCode,
					e);
		}
		return datasourceName;
	}

}
