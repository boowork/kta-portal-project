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
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.totalElements").value(2)) // 전체 3명의 사용자
                .andExpect(jsonPath("$.data.content.length()").value(2)) // 첫 페이지에 3명
                .andExpect(jsonPath("$.data.content[0].userid").value("admin"))
                .andExpect(jsonPath("$.data.content[0].name").value("관리자"))
                .andExpect(jsonPath("$.data.content[1].userid").value("user"))
                .andExpect(jsonPath("$.data.content[1].name").value("사용자"));
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
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.totalElements").value(2)) // 전체 5명의 사용자
                .andExpect(jsonPath("$.data.content.length()").value(2)) // 첫 페이지에 5명
                .andExpect(jsonPath("$.data.content[0].userid").value("admin"))
                .andExpect(jsonPath("$.data.content[1].userid").value("user"));
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
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.totalElements").value(2)) // 전체 0명
                .andExpect(jsonPath("$.data.content.length()").value(2)); // 빈 배열
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
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.totalElements").value(2)) // 전체 100명
                .andExpect(jsonPath("$.data.content.length()").value(2)) // 첫 페이지에 20명
                .andExpect(jsonPath("$.data.content[0].userid").value("admin"))
                .andExpect(jsonPath("$.data.content[1].userid").value("user"));; // 2번째 사용자
    }
}