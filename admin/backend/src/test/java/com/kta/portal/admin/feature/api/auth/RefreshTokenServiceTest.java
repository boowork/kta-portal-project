package com.kta.portal.admin.feature.api.auth;

import com.kta.portal.admin.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RefreshTokenServiceTest extends BaseIntegrationTest {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Test
    void testRefreshTokenServiceExists() {
        assertNotNull(refreshTokenService);
    }
}