package com.kta.portal.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@EnableJdbcRepositories(basePackages = "com.kta.portal.admin.feature.repository")
public class DataConfiguration {
}