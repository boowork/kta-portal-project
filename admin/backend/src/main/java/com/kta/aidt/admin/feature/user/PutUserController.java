package com.kta.aidt.admin.feature.user;

import com.kta.aidt.admin.dto.ResponseDto;
import com.kta.aidt.admin.exception.ResourceNotFoundException;
import com.kta.aidt.admin.feature.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class PutUserController {
    
    private final PutUserService putUserService;
    
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<PutUserHttpResponseDto>> updateUser(@PathVariable Long id, @Valid @RequestBody PutUserHttpRequestDto requestDto) {
        PutUserHttpResponseDto user = putUserService.updateUser(id, requestDto);
        return ResponseEntity.ok(ResponseDto.success(user));
    }
}

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class PutUserService {
    
    private final PutUserDao putUserDao;
    
    @Transactional
    public PutUserHttpResponseDto updateUser(Long id, PutUserHttpRequestDto requestDto) {
        PutUserDaoRequestDto daoRequest = convertToDaoRequestDto(id, requestDto);
        PutUserDaoResponseDto daoUser = putUserDao.updateUser(daoRequest);
        return convertToHttpResponseDto(daoUser);
    }
    
    private PutUserDaoRequestDto convertToDaoRequestDto(Long id, PutUserHttpRequestDto requestDto) {
        PutUserDaoRequestDto dto = new PutUserDaoRequestDto();
        dto.setId(id);
        dto.setName(requestDto.getName());
        dto.setRole(requestDto.getRole());
        return dto;
    }
    
    private PutUserHttpResponseDto convertToHttpResponseDto(PutUserDaoResponseDto daoUser) {
        PutUserHttpResponseDto dto = new PutUserHttpResponseDto();
        dto.setId(daoUser.getId());
        dto.setUserid(daoUser.getUserid());
        dto.setName(daoUser.getName());
        dto.setRole(daoUser.getRole());
        dto.setCreatedAt(daoUser.getCreatedAt());
        dto.setUpdatedAt(daoUser.getUpdatedAt());
        return dto;
    }
}

@Repository
@RequiredArgsConstructor
class PutUserDao {
    
    private final PutUserRepository putUserRepository;
    
    public PutUserDaoResponseDto updateUser(PutUserDaoRequestDto requestDto) {
        Optional<User> userOpt = putUserRepository.findById(requestDto.getId());
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        
        User user = userOpt.get();
        if (requestDto.getName() != null) {
            user.setName(requestDto.getName());
        }
        if (requestDto.getRole() != null) {
            user.setRole(requestDto.getRole());
        }
        user.setUpdatedAt(LocalDateTime.now());
        
        User savedUser = putUserRepository.save(user);
        return convertToDaoResponseDto(savedUser);
    }
    
    private PutUserDaoResponseDto convertToDaoResponseDto(User user) {
        PutUserDaoResponseDto dto = new PutUserDaoResponseDto();
        dto.setId(user.getId());
        dto.setUserid(user.getUserid());
        dto.setName(user.getName());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}

interface PutUserRepository extends CrudRepository<User, Long> {
}

class PutUserHttpRequestDto {
    private String name;
    private String role;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

class PutUserHttpResponseDto {
    private Long id;
    private String userid;
    private String name;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

class PutUserDaoRequestDto {
    private Long id;
    private String name;
    private String role;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

class PutUserDaoResponseDto {
    private Long id;
    private String userid;
    private String name;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}