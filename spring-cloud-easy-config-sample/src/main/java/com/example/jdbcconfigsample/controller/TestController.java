package com.example.jdbcconfigsample.controller;

import com.example.jdbcconfigsample.config.DynamicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liufuqiang
 * @Date 2024-07-08 18:20:18
 */
@RequestMapping("/config")
@RestController
public class TestController {

    @Resource
    private DynamicConfig dynamicConfig;

    @Value("${test:123}")
    private String test;

    @GetMapping("/getValue")
    public String test() {
        return dynamicConfig.toString();
    }

}
