package com.kta.portal.admin.feature.api.user;

import com.kta.portal.admin.dto.ResponseDto;
import com.kta.portal.admin.exception.BadRequestException;
import com.kta.portal.admin.feature.repository.UserRepository;
import com.kta.portal.admin.feature.repository.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PostUserController {
    
    private final PostUserService postUserService;
    
    @PostMapping("/api/users")
    public ResponseEntity<ResponseDto<PostUserHttpResponseDto>> createUser(@Valid @RequestBody PostUserHttpRequestDto requestDto) {
        PostUserHttpResponseDto user = postUserService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(user));
    }
}

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class PostUserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public PostUserHttpResponseDto createUser(PostUserHttpRequestDto requestDto) {
        if (userRepository.existsByUserid(requestDto.getUserid())) {
            throw new BadRequestException("User with this userid already exists");
        }
        
        User user = new User();
        user.setUserid(requestDto.getUserid());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setName(requestDto.getName());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        User savedUser = userRepository.save(user);
        return convertToHttpResponseDto(savedUser);
    }
    
    private PostUserHttpResponseDto convertToHttpResponseDto(User user) {
        PostUserHttpResponseDto dto = new PostUserHttpResponseDto();
        dto.setId(user.getId());
        dto.setUserid(user.getUserid());
        dto.setName(user.getName());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}

@Data
class PostUserHttpRequestDto {
    @NotBlank(message = "사용자 ID는 필수입니다")
    private String userid;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
    
    @NotBlank(message = "사용자 이름은 필수입니다")
    private String name;
}

@Data
class PostUserHttpResponseDto {
    private UUID id;
    private String userid;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

