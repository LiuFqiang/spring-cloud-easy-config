package com.example.jdbcconfigsample.config;

import icu.liufuqiang.config.ConfigInterceptor;

/**
 * @author liufuqiang
 * @Date 2024-07-10 14:28:17
 */
public class CustomConfigInterceptor implements ConfigInterceptor {
    @Override
    public String configSql(String dataId) {
        return "select content from custom_table";
    }
}
