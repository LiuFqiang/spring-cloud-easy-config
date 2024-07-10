package icu.liufuqiang.config;

/**
 * @author liufuqiang
 * @Date 2024-07-10 11:01:24
 */
public interface ConfigInterceptor {

    ConfigInterceptor DEFAULT = new DefaultInterceptor();

    String configSql(String dataId);

}
