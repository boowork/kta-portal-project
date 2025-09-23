package com.kta.aidt.admin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.kta.aidt.admin.feature.auth")
public class AuthConfiguration {
}