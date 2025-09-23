package com.kta.aidt.admin.feature.user;

import com.kta.aidt.admin.dto.ResponseDto;
import com.kta.aidt.admin.exception.BadRequestException;
import com.kta.aidt.admin.feature.user.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class PostUserController {
    
    private final PostUserService postUserService;
    
    @PostMapping
    public ResponseEntity<ResponseDto<PostUserHttpResponseDto>> createUser(@Valid @RequestBody PostUserHttpRequestDto requestDto) {
        PostUserHttpResponseDto user = postUserService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(user));
    }
}

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class PostUserService {
    
    private final PostUserDao postUserDao;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public PostUserHttpResponseDto createUser(PostUserHttpRequestDto requestDto) {
        PostUserDaoRequestDto daoRequest = convertToDaoRequestDto(requestDto);
        PostUserDaoResponseDto daoUser = postUserDao.createUser(daoRequest);
        return convertToHttpResponseDto(daoUser);
    }
    
    private PostUserDaoRequestDto convertToDaoRequestDto(PostUserHttpRequestDto requestDto) {
        PostUserDaoRequestDto dto = new PostUserDaoRequestDto();
        dto.setUserid(requestDto.getUserid());
        dto.setPassword(passwordEncoder.encode(requestDto.getPassword())); // 비밀번호 인코딩
        dto.setName(requestDto.getName());
        dto.setRole(requestDto.getRole());
        return dto;
    }
    
    private PostUserHttpResponseDto convertToHttpResponseDto(PostUserDaoResponseDto daoUser) {
        PostUserHttpResponseDto dto = new PostUserHttpResponseDto();
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
class PostUserDao {
    
    private final PostUserRepository postUserRepository;
    
    public PostUserDaoResponseDto createUser(PostUserDaoRequestDto requestDto) {
        if (postUserRepository.existsByUserid(requestDto.getUserid())) {
            throw new BadRequestException("User with this userid already exists");
        }
        
        User user = new User();
        user.setUserid(requestDto.getUserid());
        user.setPassword(requestDto.getPassword());
        user.setName(requestDto.getName());
        user.setRole(requestDto.getRole());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        User savedUser = postUserRepository.save(user);
        return convertToDaoResponseDto(savedUser);
    }
    
    private PostUserDaoResponseDto convertToDaoResponseDto(User user) {
        PostUserDaoResponseDto dto = new PostUserDaoResponseDto();
        dto.setId(user.getId());
        dto.setUserid(user.getUserid());
        dto.setName(user.getName());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}

interface PostUserRepository extends CrudRepository<User, Long> {
    boolean existsByUserid(String userid);
}

class PostUserHttpRequestDto {
    @NotBlank(message = "사용자 ID는 필수입니다")
    private String userid;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
    
    @NotBlank(message = "사용자 이름은 필수입니다")
    private String name;
    
    @NotBlank(message = "사용자 역할은 필수입니다")
    @Pattern(regexp = "^(USER|ADMIN)$", message = "사용자 역할은 USER 또는 ADMIN이어야 합니다")
    private String role;
    
    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

class PostUserHttpResponseDto {
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

class PostUserDaoRequestDto {
    private String userid;
    private String password;
    private String name;
    private String role;
    
    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

class PostUserDaoResponseDto {
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