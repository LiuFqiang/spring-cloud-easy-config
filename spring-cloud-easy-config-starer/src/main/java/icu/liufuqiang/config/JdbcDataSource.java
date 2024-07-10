package icu.liufuqiang.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import icu.liufuqiang.JdbcConfigProperties;

import javax.sql.DataSource;

/**
 * @author liufuqiang
 * @Date 2024-07-08 15:42:59
 */
public class JdbcDataSource {

	private JdbcConfigProperties properties;

	public JdbcDataSource(JdbcConfigProperties properties) {
		this.properties = properties;
	}

	public DataSource build() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(properties.getUrl());
		config.setUsername(properties.getUsername());
		config.setPassword(properties.getPassword());

		config.setMaximumPoolSize(2);
		config.setIdleTimeout(30000);
		config.setConnectionTestQuery("SELECT 1");
		return new HikariDataSource(config);
	}

}
