package com.kta.portal.admin;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.crypto.SecretKey;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

@SpringBootTest
@SpringJUnitConfig
@Import(TestContainerConfiguration.class)
public abstract class BaseIntegrationTest {

    @Autowired
    private DataSource dataSource;
    
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.access.token.validity}")
    private Long accessTokenValidity;
    
    @Value("${jwt.issuer}")
    private String issuer;

    @BeforeEach
    void setUp() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            // Execute init SQL script (schema + basic test data)
            try {
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("db/init.sql"));
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
    
    // JWT Token Helper Methods
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    protected String generateTestToken(Long id, String userid, String name, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .claim("id", id)
                .claim("userid", userid)
                .claim("name", name)
                .claim("role", role)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }
    
    protected String generateAdminToken() {
        return generateTestToken(1L, "admin", "관리자", "ADMIN");
    }
    
    protected String generateUserToken() {
        return generateTestToken(2L, "user", "사용자", "USER");
    }
    
    // Helper method to add JWT Authorization header to MockMvc requests
    protected MockHttpServletRequestBuilder withAdminAuth(MockHttpServletRequestBuilder requestBuilder) {
        return requestBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + generateAdminToken());
    }
    
    protected MockHttpServletRequestBuilder withUserAuth(MockHttpServletRequestBuilder requestBuilder) {
        return requestBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + generateUserToken());
    }
}