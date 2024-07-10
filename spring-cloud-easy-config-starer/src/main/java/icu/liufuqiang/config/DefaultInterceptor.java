package icu.liufuqiang.config;


/**
 * @author liufuqiang
 * @Date 2024-07-10 11:02:33
 */
public class DefaultInterceptor implements ConfigInterceptor{

    @Override
    public String configSql(String dataId) {
        return String.format("select content from config where data_id = '%s'", dataId);
    }

}
