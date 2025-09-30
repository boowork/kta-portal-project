package com.kta.portal.admin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.kta.portal.admin.feature.api.v1.auth")
public class AuthConfiguration {
}