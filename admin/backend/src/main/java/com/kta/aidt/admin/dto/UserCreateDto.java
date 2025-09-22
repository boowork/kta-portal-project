package com.kta.aidt.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserCreateDto {
    @NotBlank(message = "사용자 ID는 필수입니다")
    private String userid;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
    
    @NotBlank(message = "사용자 이름은 필수입니다")
    private String name;
    
    @NotBlank(message = "사용자 역할은 필수입니다")
    @Pattern(regexp = "^(USER|ADMIN)$", message = "사용자 역할은 USER 또는 ADMIN이어야 합니다")
    private String role;
}