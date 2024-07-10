package icu.liufuqiang.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import icu.liufuqiang.client.ClientWorker;
import icu.liufuqiang.listener.Listener;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author liufuqiang
 * @Date 2024-07-08 17:34:54
 */
public class JdbcConfigService implements ConfigService {

	private static final Logger log = LoggerFactory.getLogger(JdbcConfigService.class);

	private DataSource dataSource;

	private ClientWorker worker;

	private ConfigInterceptor configInterceptor;

	private ApplicationContext context;

	public JdbcConfigService(DataSource dataSource) {
		this.dataSource = dataSource;
		worker = new ClientWorker();
	}

	@Override
	public String getConfig(String dataId) {
		try (Connection connection = dataSource.getConnection()) {
			Statement statement = connection.createStatement();
			if (configInterceptor == null) {
				Set<Class<? extends ConfigInterceptor>> interceptors =
						new Reflections().getSubTypesOf(ConfigInterceptor.class);
				Optional<Class<? extends ConfigInterceptor>> cusInterceptor = interceptors.stream()
						.filter(interceptor -> !interceptor.equals(DefaultInterceptor.class)).findFirst();
				if (cusInterceptor.isPresent()) {
					configInterceptor = cusInterceptor.get().newInstance();
				}
			}
			if (configInterceptor == null) {
				configInterceptor = ConfigInterceptor.DEFAULT;
			}

			ResultSet resultSet = statement.executeQuery(configInterceptor.configSql(dataId));
			if (resultSet.next()) {
				return resultSet.getString("content");
			}
		}
		catch (SQLException | InstantiationException | IllegalAccessException e) {
			log.error("load jdbc config error", e);
		}
		return null;
	}

	@Override
	public boolean addListener(String dataId, Listener listener) {
		ArrayList<Listener> Listeners = new ArrayList<>();
		Listeners.add(listener);
		worker.addTenantListeners(dataId, Listeners);
		return true;
	}
}
