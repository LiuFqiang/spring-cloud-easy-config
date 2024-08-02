package com.example.jdbcconfigsample.config;

import icu.liufuqiang.config.ConfigInterceptor;
import org.springframework.stereotype.Component;

/**
 * @author liufuqiang
 * @Date 2024-08-01 11:17:47
 */
public class CustomConfigInterceptor implements ConfigInterceptor {
    @Override
    public String configSql(String dataId) {
        return String.format("select content from config where data_id = '%s'", dataId);
    }
}
