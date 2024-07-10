package icu.liufuqiang.client;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author liufuqiang
 * @Date 2024-07-08 16:29:16
 */
public class JdbcConfigPropertySourceRepository {

	private final static Map<String, JdbcConfigPropertySource> PROPERTY_SOURCE_MAP = new ConcurrentHashMap<String, JdbcConfigPropertySource>();

	public static void collectPropertySource(JdbcConfigPropertySource propertySource) {
		PROPERTY_SOURCE_MAP.putIfAbsent(propertySource.getDataId(), propertySource);
	}

	public static List<JdbcConfigPropertySource> getAll() {
		return PROPERTY_SOURCE_MAP.values().stream().collect(Collectors.toList());
	}

}
