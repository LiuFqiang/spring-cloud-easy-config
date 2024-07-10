package icu.liufuqiang.client;

import icu.liufuqiang.cache.CacheData;
import icu.liufuqiang.config.ConfigFactory;
import icu.liufuqiang.listener.Listener;
import icu.liufuqiang.config.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author liufuqiang
 * @Date 2024-07-09 09:15:59
 */
public class ClientWorker {

	private static final Logger log = LoggerFactory.getLogger(ClientWorker.class);

	private ScheduledExecutorService executor;

	private Map<String, CacheData> cacheMap = new ConcurrentHashMap<>();

	public ClientWorker() {
		executor = Executors.newScheduledThreadPool(10, r -> {
			Thread t = new Thread(r);
			t.setName("jdbc-config-thread");
			t.setDaemon(true);
			return t;
		});

		executor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					checkConfig();
				}
				catch (Exception e) {
					log.error("check config error", e);
				}
			}
		}, 1L, 10L, TimeUnit.MILLISECONDS);
	}

	private void checkConfig() {
		for (CacheData cacheData : cacheMap.values()) {
			ConfigService configService = ConfigFactory.getConfigService();
			String content = configService.getConfig(cacheData.getDataId());
			cacheData.setContent(content);
			cacheData.checkListenerMd5();
		}
	}

	public void addTenantListeners(String dataId, List<Listener> listenerList) {
		synchronized (cacheMap) {
			CacheData cacheData = new CacheData(dataId);
			cacheMap.putIfAbsent(dataId, cacheData);
			listenerList.forEach(listener -> cacheData.addListener(listener));
		}
	}

}
