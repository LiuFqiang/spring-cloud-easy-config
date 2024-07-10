package icu.liufuqiang.parser;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ByteArrayResource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liufuqiang
 * @Date 2024-07-08 16:18:07
 */
public class ConfigDataYamlParser extends AbstractConfigDataParser {

	public ConfigDataYamlParser() {
		super(",yml,yaml,");
	}

	@Override
	protected Map<String, Object> doParse(String data) {
		YamlMapFactoryBean yamlFactory = new YamlMapFactoryBean();
		yamlFactory.setResources(new ByteArrayResource(data.getBytes()));

		Map<String, Object> result = new LinkedHashMap<String, Object>();
		flattenedMap(result, yamlFactory.getObject(), EMPTY_STRING);
		return result;
	}

}
