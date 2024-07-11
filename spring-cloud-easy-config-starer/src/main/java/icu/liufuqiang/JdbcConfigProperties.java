package icu.liufuqiang;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liufuqiang
 * @Date 2024-07-08 14:20:44
 */
@ConfigurationProperties(prefix = JdbcConfigProperties.PREFIX)
public class JdbcConfigProperties {

	public static final String PREFIX = "spring.cloud.easy-config";

	private String url;

	private String username;

	private String password;

	private String driverClassName;

	private String dataId;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
}
