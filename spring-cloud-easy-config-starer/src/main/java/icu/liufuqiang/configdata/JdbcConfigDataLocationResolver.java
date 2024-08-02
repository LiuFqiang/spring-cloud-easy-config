package icu.liufuqiang.configdata;

import icu.liufuqiang.JdbcConfigProperties;
import org.apache.commons.logging.Log;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.config.*;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author liufuqiang
 * @Date 2024-07-30 16:04:49
 */
@AutoConfigureBefore(StandardConfigDataLocationResolver.class)
public class JdbcConfigDataLocationResolver implements ConfigDataLocationResolver<JdbcConfigDataResource>, Ordered {

    private final Log log;

    private static final String PREFIX = "jdbc";

    public JdbcConfigDataLocationResolver(DeferredLogFactory logFactory) {
        this.log = logFactory.getLog(getClass());
    }

    @Override
    public boolean isResolvable(ConfigDataLocationResolverContext context, ConfigDataLocation location) {
        if (!location.hasPrefix(PREFIX)) {
            return false;
        }
        return context.getBinder().bind("spring.cloud.easy-config.enabled", Boolean.class)
                .orElse(false);
    }

    @Override
    public List<JdbcConfigDataResource> resolve(ConfigDataLocationResolverContext context, ConfigDataLocation location)
            throws ConfigDataLocationNotFoundException, ConfigDataResourceNotFoundException {
        return Collections.emptyList();
    }

    @Override
    public List<JdbcConfigDataResource> resolveProfileSpecific(ConfigDataLocationResolverContext context, ConfigDataLocation location, Profiles profiles)
            throws ConfigDataLocationNotFoundException {
        JdbcConfigProperties properties = loadJdbcConfigProperties(context);

        ConfigurableBootstrapContext contextBootstrapContext = context
                .getBootstrapContext();
        contextBootstrapContext.registerIfAbsent(JdbcConfigProperties.class,
                BootstrapRegistry.InstanceSupplier.of(properties));

        ArrayList<JdbcConfigDataResource> resources = new ArrayList<>();
        resources.add(new JdbcConfigDataResource(location.isOptional(), properties, log));
        return resources;
    }

    private JdbcConfigProperties loadJdbcConfigProperties(ConfigDataLocationResolverContext context) {
        Binder binder = context.getBinder();
        BindHandler bindHandler = context.getBootstrapContext().getOrElse(BindHandler.class, null);

        JdbcConfigProperties properties;
        if (context.getBootstrapContext().isRegistered(JdbcConfigProperties.class)) {
            properties = context.getBootstrapContext()
                    .get(JdbcConfigProperties.class);
        }
        else {
            properties = binder
                .bind(JdbcConfigProperties.PREFIX, Bindable.of(JdbcConfigProperties.class), bindHandler)
                .orElseGet(JdbcConfigProperties::new);

        }
        return properties;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
