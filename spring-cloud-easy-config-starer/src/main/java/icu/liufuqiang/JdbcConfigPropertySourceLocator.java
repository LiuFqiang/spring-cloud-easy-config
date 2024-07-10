package icu.liufuqiang;

import icu.liufuqiang.client.ClientManager;
import icu.liufuqiang.client.JdbcConfigPropertySourceBuilder;
import icu.liufuqiang.client.JdbcConfigPropertySource;
import icu.liufuqiang.config.ConfigFactory;
import icu.liufuqiang.config.ConfigService;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import javax.sql.DataSource;

/**
 * @author liufuqiang
 * @Date 2024-07-08 14:25:08
 */
public class JdbcConfigPropertySourceLocator implements PropertySourceLocator {

	private DataSource dataSource;

	private ConfigService configService;

	private JdbcConfigProperties properties;

	public JdbcConfigPropertySourceLocator(JdbcConfigProperties properties) {
		this.properties = properties;
	}

	@Override
	public PropertySource<?> locate(Environment environment) {
		dataSource = ClientManager.getDataSource(properties);
		if (configService == null) {
			configService = ConfigFactory.createConfigService(dataSource);
		}

		JdbcConfigPropertySourceBuilder builder = new JdbcConfigPropertySourceBuilder(
				configService, dataSource);
		CompositePropertySource composite = new CompositePropertySource(
				"jdbc-config-property-source");
		JdbcConfigPropertySource propertySource = builder.build("dataId");
		if (propertySource.getSource().isEmpty()) {
			return composite;
		}
		composite.addFirstPropertySource(propertySource);
		return composite;
	}

}
