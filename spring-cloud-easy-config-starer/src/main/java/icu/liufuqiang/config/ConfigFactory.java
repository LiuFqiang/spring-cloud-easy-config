package icu.liufuqiang.config;

import javax.sql.DataSource;

/**
 * @author liufuqiang
 * @Date 2024-07-08 17:38:18
 */
public class ConfigFactory {

	public static ConfigService configService;

	public static ConfigService createConfigService(DataSource dataSource) {
		if (configService == null) {
			configService = new JdbcConfigService(dataSource);
		}
		return configService;
	}

	public static ConfigService getConfigService() {
		return configService;
	}

}
