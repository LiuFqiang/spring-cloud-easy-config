package icu.liufuqiang.parser;

/**
 * @author liufuqiang
 * @Date 2024-07-08 16:19:36
 */
public class ParseFactory {

	private AbstractConfigDataParser parser;

	private static class ParseFactoryHolder {

		private static final AbstractConfigDataParser INSTANCE = new ConfigDataYamlParser();

	}

	public static AbstractConfigDataParser getInstance() {
		return ParseFactoryHolder.INSTANCE;
	}

}
