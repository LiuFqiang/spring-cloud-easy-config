package icu.liufuqiang.client;

import icu.liufuqiang.parser.ParseFactory;
import icu.liufuqiang.cache.LocalFileCache;
import icu.liufuqiang.config.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liufuqiang
 * @Date 2024-07-08 15:53:51
 */
public class JdbcConfigPropertySourceBuilder {

	private static final Logger log = LoggerFactory
			.getLogger(JdbcConfigPropertySourceBuilder.class);

	private static final Map<String, Object> EMPTY_MAP = new LinkedHashMap();

	private ConfigService configService;

	private DataSource dataSource;

	public JdbcConfigPropertySourceBuilder(ConfigService configService,
			DataSource dataSource) {
		this.configService = configService;
		this.dataSource = dataSource;
	}

	public JdbcConfigPropertySource build(String dataId) {
		Map<String, Object> configData = loadDbConfigData(dataId);
		JdbcConfigPropertySource propertySource = new JdbcConfigPropertySource(dataId,
				configData);
		JdbcConfigPropertySourceRepository.collectPropertySource(propertySource);
		return propertySource;
	}

	private Map<String, Object> loadDbConfigData(String dataId) {
		String data = LocalFileCache.getContentFromDisk(dataId);
		String config = configService.getConfig(dataId);
		if (config != null) {
			data = config;
		}
		if (StringUtils.isEmpty(data)) {
			log.warn("Ignore empty jdbc configuration of dataId: {}", dataId);
			return EMPTY_MAP;
		}
		else {
			log.info("load remote jdbc data dataId: {} {}", dataId, data);
			Map<String, Object> dataMap = null;
			try {
				dataMap = ParseFactory.getInstance().parseData(data, "yml");
			} catch (IOException e) {
				log.error("parse yaml error.");
			}
			LocalFileCache.saveSnapshotFile(dataId, data);
			return dataMap == null ? EMPTY_MAP : dataMap;
		}
	}

}
