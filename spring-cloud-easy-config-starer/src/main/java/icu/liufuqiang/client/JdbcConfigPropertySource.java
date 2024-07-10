package icu.liufuqiang.client;

import org.springframework.core.env.MapPropertySource;

import java.util.Map;

/**
 * @author liufuqiang
 * @Date 2024-07-08 15:56:27
 */
public class JdbcConfigPropertySource extends MapPropertySource {

	private String dataId;

	public JdbcConfigPropertySource(String name, Map<String, Object> source) {
		super(name, source);
		this.dataId = name;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

}
