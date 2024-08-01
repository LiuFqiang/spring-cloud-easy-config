package icu.liufuqiang.configdata;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import icu.liufuqiang.JdbcConfigProperties;
import icu.liufuqiang.client.ClientManager;
import icu.liufuqiang.client.JdbcConfigPropertySource;
import icu.liufuqiang.client.JdbcConfigPropertySourceBuilder;
import icu.liufuqiang.config.ConfigFactory;
import icu.liufuqiang.config.ConfigService;
import icu.liufuqiang.config.GlobalConfig;
import icu.liufuqiang.config.JdbcConfigService;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigData.Option;
import org.springframework.boot.context.config.ConfigData.Options;
import org.springframework.boot.context.config.ConfigDataLoader;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.env.CompositePropertySource;

import javax.sql.DataSource;

import static org.springframework.boot.context.config.ConfigData.Option.*;

/**
 * @author liufuqiang
 * @Date 2024-07-30 15:32:26
 */
public class JdbcConfigDataLoader implements ConfigDataLoader<JdbcConfigDataResource> {

    private final Log log;

    private JdbcConfigProperties properties;

    private ConfigService configService;

    private DataSource dataSource;

    public JdbcConfigDataLoader(DeferredLogFactory logFactory) {
        log = logFactory.getLog(getClass());
    }


    @Override
    public ConfigData load(ConfigDataLoaderContext context,
                           JdbcConfigDataResource resource)
            throws ConfigDataResourceNotFoundException {
        log.info("Start load jdbc configdata");
        properties = resource.getProperties();
        dataSource = ClientManager.getDataSource(properties);
        if (configService == null) {
            configService = ConfigFactory.createConfigService(dataSource);
        }

//        String appName = environment.getProperty("spring.application.name");
        GlobalConfig.setApplicationName("appName");

        JdbcConfigPropertySourceBuilder builder = new JdbcConfigPropertySourceBuilder(
                configService, dataSource);
        CompositePropertySource composite = new CompositePropertySource(
                "jdbc-config-property-source");
        JdbcConfigPropertySource propertySource =
                builder.build(StrUtil.nullToDefault(properties.getDataId(), ""));
        composite.addFirstPropertySource(propertySource);
        return new ConfigData(Lists.newArrayList(composite), getOptions());

    }

    private Option[] getOptions() {
        return new Option[] {IGNORE_IMPORTS, IGNORE_PROFILES, PROFILE_SPECIFIC};
    }
}
