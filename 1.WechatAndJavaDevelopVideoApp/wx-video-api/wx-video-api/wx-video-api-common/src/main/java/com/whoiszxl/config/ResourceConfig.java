package com.whoiszxl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "com.whoiszxl")
@PropertySource("classpath:resource.properties")
public class ResourceConfig {

}
