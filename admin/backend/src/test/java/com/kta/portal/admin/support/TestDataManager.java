package com.kta.portal.admin.support;

import com.kta.portal.admin.annotation.TestDataSet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Manages test data setup and cleanup for integration tests
 */
@Component
public class TestDataManager {

    private final DataSource dataSource;

    public TestDataManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Set up test data based on @TestDataSet annotation
     */
    public void setupTestData(Method testMethod) throws SQLException {
        TestDataSet testDataSet = findTestDataSetAnnotation(testMethod);
        if (testDataSet != null) {
            setupTestData(testDataSet);
        }
    }

    /**
     * Set up test data based on @TestDataSet annotation on class
     */
    public void setupTestData(Class<?> testClass) throws SQLException {
        TestDataSet testDataSet = testClass.getAnnotation(TestDataSet.class);
        if (testDataSet != null) {
            setupTestData(testDataSet);
        }
    }

    private TestDataSet findTestDataSetAnnotation(Method testMethod) {
        // First check method level annotation
        TestDataSet methodAnnotation = testMethod.getAnnotation(TestDataSet.class);
        if (methodAnnotation != null) {
            return methodAnnotation;
        }
        
        // Then check class level annotation
        return testMethod.getDeclaringClass().getAnnotation(TestDataSet.class);
    }

    private void setupTestData(TestDataSet testDataSet) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            // Execute schema initialization if needed
            if (testDataSet.executeSchema()) {
                executeSchemaScript(connection);
            }

            // Clean insert if specified
            if (testDataSet.cleanInsert()) {
                cleanDatabase(connection);
            }

            // Execute test data scripts
            for (String script : testDataSet.value()) {
                try {
                    String resourcePath = script.startsWith("datasets/") ? script : "datasets/" + script;
                    ScriptUtils.executeSqlScript(connection, new ClassPathResource(resourcePath));
                } catch (Exception e) {
                    System.out.println("Warning: Error executing test data script " + script + ": " + e.getMessage());
                }
            }
        }
    }

    private void executeSchemaScript(Connection connection) {
        try {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("db/schema.sql"));
        } catch (Exception e) {
            System.out.println("Warning: Error executing schema script: " + e.getMessage());
        }
    }

    private void cleanDatabase(Connection connection) {
        try {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("db/clean.sql"));
        } catch (Exception e) {
            System.out.println("Warning: Error cleaning database: " + e.getMessage());
        }
    }
}