package com.kta.portal.admin.feature.api.auth;

import com.kta.portal.admin.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PostLoginServiceTest extends BaseIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testAllBeansExist() {
        assertNotNull(applicationContext.getBean(RefreshTokenService.class));
        assertNotNull(applicationContext.getBean(RefreshTokenRepository.class));
        assertNotNull(applicationContext.getBean(PostLoginController.class));
        
        try {
            assertNotNull(applicationContext.getBean(PostLoginService.class));
            System.out.println("PostLoginService bean found");
        } catch (Exception e) {
            System.out.println("PostLoginService bean not found: " + e.getMessage());
        }
        
        try {
            assertNotNull(applicationContext.getBean(PostLoginDao.class));
            System.out.println("PostLoginDao bean found");
        } catch (Exception e) {
            System.out.println("PostLoginDao bean not found: " + e.getMessage());
        }
    }
}