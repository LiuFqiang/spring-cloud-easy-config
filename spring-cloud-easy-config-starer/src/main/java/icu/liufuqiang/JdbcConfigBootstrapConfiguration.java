package icu.liufuqiang;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liufuqiang
 * @Date 2024-07-08 14:12:07
 */
@Configuration
@ConditionalOnProperty(prefix = JdbcConfigProperties.PREFIX, name = "enabled", havingValue = "true")
public class JdbcConfigBootstrapConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public JdbcConfigProperties jdbcConfigProperties() {
		return new JdbcConfigProperties();
	}

	@Bean
	@ConditionalOnMissingBean
	public JdbcConfigPropertySourceLocator jdbcConfigPropertySourceLocator(
			JdbcConfigProperties properties) {
		return new JdbcConfigPropertySourceLocator(properties);
	}

}
