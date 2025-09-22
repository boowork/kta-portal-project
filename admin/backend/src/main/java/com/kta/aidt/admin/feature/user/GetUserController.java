package com.kta.aidt.admin.feature.user;

import com.kta.aidt.admin.dto.ResponseDto;
import com.kta.aidt.admin.exception.ResourceNotFoundException;
import com.kta.aidt.admin.feature.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class GetUserController {
    
    private final GetUserService getUserService;
    
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<GetUserHttpResponseDto>> getUserById(@PathVariable Long id) {
        GetUserHttpResponseDto user = getUserService.getUserById(id);
        return ResponseEntity.ok(ResponseDto.success(user));
    }
}

@Service
@RequiredArgsConstructor
class GetUserService {
    
    private final GetUserDao getUserDao;
    
    public GetUserHttpResponseDto getUserById(Long id) {
        GetUserDaoResponseDto daoUser = getUserDao.findUserById(id);
        return convertToHttpResponseDto(daoUser);
    }
    
    private GetUserHttpResponseDto convertToHttpResponseDto(GetUserDaoResponseDto daoUser) {
        GetUserHttpResponseDto dto = new GetUserHttpResponseDto();
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
class GetUserDao {
    
    private final GetUserRepository getUserRepository;
    
    public GetUserDaoResponseDto findUserById(Long id) {
        Optional<User> userOpt = getUserRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return convertToDaoResponseDto(userOpt.get());
    }
    
    private GetUserDaoResponseDto convertToDaoResponseDto(User user) {
        GetUserDaoResponseDto dto = new GetUserDaoResponseDto();
        dto.setId(user.getId());
        dto.setUserid(user.getUserid());
        dto.setName(user.getName());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}

interface GetUserRepository extends CrudRepository<User, Long> {
}

class GetUserHttpResponseDto {
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

class GetUserDaoResponseDto {
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