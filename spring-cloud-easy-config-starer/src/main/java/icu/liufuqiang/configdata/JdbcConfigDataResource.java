package icu.liufuqiang.configdata;

import icu.liufuqiang.JdbcConfigProperties;
import org.apache.commons.logging.Log;
import org.springframework.boot.context.config.ConfigDataResource;

/**
 * @author liufuqiang
 * @Date 2024-07-30 15:32:50
 */
public class JdbcConfigDataResource extends ConfigDataResource {

    private JdbcConfigProperties properties;

    private Log log;

    public JdbcConfigDataResource(boolean optional, JdbcConfigProperties properties, Log log) {
        super(optional);
        this.properties = properties;
        this.log = log;
    }

    public JdbcConfigProperties getProperties() {
        return properties;
    }
}
