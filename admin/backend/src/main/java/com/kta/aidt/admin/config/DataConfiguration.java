package com.kta.aidt.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@EnableJdbcRepositories(basePackages = "com.kta.aidt.admin.feature.user")
public class DataConfiguration {
}