package com.kta.portal.admin.feature.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {
    @Id
    private Long id;
    private String userid;
    private String password;
    private String name;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}