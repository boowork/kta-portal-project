package com.kta.aidt.admin.feature.user;

import com.kta.aidt.admin.dto.ResponseDto;
import com.kta.aidt.admin.feature.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class GetUsersController {
    
    private final GetUsersService getUsersService;
    
    @GetMapping
    public ResponseEntity<ResponseDto<List<GetUsersHttpResponseDto>>> getAllUsers() {
        List<GetUsersHttpResponseDto> users = getUsersService.getAllUsers();
        return ResponseEntity.ok(ResponseDto.success(users));
    }
}

@Service
@RequiredArgsConstructor
class GetUsersService {
    
    private final GetUsersDao getUsersDao;
    
    public List<GetUsersHttpResponseDto> getAllUsers() {
        List<GetUsersDaoResponseDto> daoUsers = getUsersDao.findAllUsers();
        return daoUsers.stream()
                .map(this::convertToHttpResponseDto)
                .collect(Collectors.toList());
    }
    
    private GetUsersHttpResponseDto convertToHttpResponseDto(GetUsersDaoResponseDto daoUser) {
        GetUsersHttpResponseDto dto = new GetUsersHttpResponseDto();
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
class GetUsersDao {
    
    private final GetUsersRepository getUsersRepository;
    
    public List<GetUsersDaoResponseDto> findAllUsers() {
        return getUsersRepository.findAll().stream()
                .map(this::convertToDaoResponseDto)
                .collect(Collectors.toList());
    }
    
    private GetUsersDaoResponseDto convertToDaoResponseDto(User user) {
        GetUsersDaoResponseDto dto = new GetUsersDaoResponseDto();
        dto.setId(user.getId());
        dto.setUserid(user.getUserid());
        dto.setName(user.getName());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}

interface GetUsersRepository extends CrudRepository<User, Long> {
    List<User> findAll();
}

class GetUsersHttpResponseDto {
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

class GetUsersDaoResponseDto {
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