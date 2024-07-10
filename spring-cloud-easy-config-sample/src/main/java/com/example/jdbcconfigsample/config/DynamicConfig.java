package com.example.jdbcconfigsample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author liufuqiang
 * @Date 2024-07-10 10:34:23
 */
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "test")
public class DynamicConfig {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DynamicConfig{" +
                "name='" + name + '\'' +
                '}';
    }
}
