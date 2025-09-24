package com.kta.portal.admin.feature.api.user;

import com.kta.portal.admin.BaseIntegrationTest;
import com.kta.portal.admin.annotation.TestDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class GetUsersControllerWithDataSetTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @TestDataSet("users-basic-dataset.sql")
    void testGetAllUsers_WithBasicDataSet_ShouldReturnSpecificUsers() throws Exception {
        // Given: TestDataSet annotation이 users-basic-dataset.sql을 로드
        // 테스트 데이터는 src/test/resources/datasets/users-basic-dataset.sql에 정의
        
        // When & Then
        mockMvc.perform(withAdminAuth(get("/api/users")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(3)) // 정확히 3명의 사용자
                .andExpect(jsonPath("$.data[0].userid").value("testadmin"))
                .andExpect(jsonPath("$.data[0].name").value("테스트 관리자"))
                .andExpect(jsonPath("$.data[0].role").value("ADMIN"))
                .andExpect(jsonPath("$.data[1].userid").value("testuser"))
                .andExpect(jsonPath("$.data[1].name").value("테스트 사용자"))
                .andExpect(jsonPath("$.data[1].role").value("USER"));
    }

    @Test
    @TestDataSet(value = {"users-advanced-dataset.sql", "roles-dataset.sql"})
    void testGetAllUsers_WithMultipleDataSets_ShouldReturnUsersWithRoles() throws Exception {
        // Given: 여러 데이터셋을 함께 로드
        // users-advanced-dataset.sql + roles-dataset.sql
        
        // When & Then
        mockMvc.perform(withAdminAuth(get("/api/users")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(5)) // 5명의 사용자
                .andExpect(jsonPath("$.data[0].userid").value("manager"))
                .andExpect(jsonPath("$.data[0].role").value("MANAGER"))
                .andExpect(jsonPath("$.data[1].userid").value("analyst"))
                .andExpect(jsonPath("$.data[1].role").value("ANALYST"));
    }

    @Test
    @TestDataSet(value = "users-empty-dataset.sql", cleanInsert = false)
    void testGetAllUsers_WithEmptyDataSet_ShouldReturnEmptyList() throws Exception {
        // Given: cleanInsert = false로 설정하여 기본 데이터 유지 + 추가 데이터
        // 빈 데이터셋으로 테스트
        
        // When & Then
        mockMvc.perform(withAdminAuth(get("/api/users")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0)); // 빈 배열
    }

    @Test
    @TestDataSet("users-large-dataset.sql")
    void testGetAllUsers_WithLargeDataSet_ShouldHandlePerformance() throws Exception {
        // Given: 대용량 테스트 데이터 (100명 사용자)
        
        // When & Then
        mockMvc.perform(withAdminAuth(get("/api/users")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(100))
                .andExpect(jsonPath("$.data[0].userid").value("user001"))
                .andExpect(jsonPath("$.data[99].userid").value("user100"));
    }
}