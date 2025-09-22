package com.kta.aidt.admin;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest
@SpringJUnitConfig
@Import(TestContainerConfiguration.class)
public abstract class BaseIntegrationTest {
}