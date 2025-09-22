package com.kta.aidt.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleSqlTest extends BaseIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testDatabaseConnection() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertEquals(1, result);
    }

    @Test
    void testSimpleQuery() {
        String result = jdbcTemplate.queryForObject("SELECT 'Hello TestContainers'", String.class);
        assertEquals("Hello TestContainers", result);
    }
}