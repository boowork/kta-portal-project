package com.kta.aidt.admin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify test data sets for integration tests
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestDataSet {
    
    /**
     * SQL scripts to execute before the test
     * These are classpath resources under src/test/resources/datasets/
     */
    String[] value() default {};
    
    /**
     * Whether to clean the database before inserting test data
     */
    boolean cleanInsert() default true;
    
    /**
     * Whether to execute schema initialization
     */
    boolean executeSchema() default true;
}