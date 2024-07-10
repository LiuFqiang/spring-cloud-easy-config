package icu.liufuqiang;

import icu.liufuqiang.refresh.JdbcDataConfigRefresh;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author liufuqiang
 * @Date 2024-07-09 14:43:58
 */
@Configuration
@ConditionalOnProperty(prefix = JdbcConfigProperties.PREFIX, name = "enabled", havingValue = "true")
public class JdbcConfigAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public JdbcDataConfigRefresh jdbcDataConfigRefresh() {
		return new JdbcDataConfigRefresh();
	}

}
