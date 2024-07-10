package icu.liufuqiang.refresh;

import icu.liufuqiang.client.JdbcConfigPropertySource;
import icu.liufuqiang.client.JdbcConfigPropertySourceRepository;
import icu.liufuqiang.config.ConfigFactory;
import icu.liufuqiang.config.ConfigService;
import icu.liufuqiang.listener.AbstractConfigListener;
import icu.liufuqiang.listener.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author liufuqiang
 * @Date 2024-07-08 17:44:00
 */
public class JdbcDataConfigRefresh
		implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware {

	private final static Logger log = LoggerFactory
			.getLogger(JdbcDataConfigRefresh.class);

	private ApplicationContext applicationContext;

	private AtomicBoolean ready = new AtomicBoolean(false);

	private Map<String, Listener> listenerMap = new ConcurrentHashMap<>(16);

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		if (ready.compareAndSet(false, true)) {
			this.registerJdbcListener();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	private void registerJdbcListener() {
		for (JdbcConfigPropertySource propertySource : JdbcConfigPropertySourceRepository
				.getAll()) {
			final String dataId = propertySource.getDataId();
			ConfigService configService = ConfigFactory.getConfigService();
			Listener refreshListener = listenerMap.computeIfAbsent(dataId,
					listener -> new AbstractConfigListener() {
						@Override
						public void innerReceiveConfigInfo(String dataId, String configInfo) {
							applicationContext.publishEvent(
									new RefreshEvent(this, null, "jdbc config refresh"));
							log.info("receive change jdbc config info:{}", configInfo);
						}
					});
			configService.addListener(dataId, refreshListener);
		}
	}

}
