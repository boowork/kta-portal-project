package com.kta.portal.admin;

import com.kta.portal.admin.support.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
@SpringJUnitConfig
@Import(TestContainerConfiguration.class)
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            // Execute init SQL script (schema + basic test data)
            try {
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("250930.sql"));
            } catch (Exception e) {
                // Log and continue
                System.out.println("Warning: Error executing init script: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Helper method for inserting additional test data via text blocks
    protected void insertTestData(String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try {
                connection.createStatement().execute(sql);
            } catch (Exception e) {
                System.out.println("Warning: Error inserting test data: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Authentication helper methods using TestUtils

    protected MockHttpServletRequestBuilder withAdminAuth(MockHttpServletRequestBuilder requestBuilder) {
        return TestUtils.withAdminJwtAuth(requestBuilder);
    }

    protected MockHttpServletRequestBuilder withUserAuth(MockHttpServletRequestBuilder requestBuilder) {
        return TestUtils.withUserJwtAuth(requestBuilder);
    }

    protected MockHttpServletRequestBuilder withDevAdminAuth(MockHttpServletRequestBuilder requestBuilder) {
        return TestUtils.withAdminDevAuth(requestBuilder);
    }

    protected MockHttpServletRequestBuilder withDevUserAuth(MockHttpServletRequestBuilder requestBuilder) {
        return TestUtils.withUserDevAuth(requestBuilder);
    }

    // Backward compatibility methods
    protected String generateAdminToken() {
        return TestUtils.generateAdminToken();
    }

    protected String generateUserToken() {
        return TestUtils.generateUserToken();
    }
}