package com.kta.portal.admin.feature.api.user;

import com.kta.portal.admin.dto.ResponseDto;
import com.kta.portal.admin.exception.ResourceNotFoundException;
import com.kta.portal.admin.feature.repository.UserRepository;
import com.kta.portal.admin.feature.repository.model.User;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PutUserController {
    
    private final PutUserService putUserService;
    
    @PutMapping("/api/users/{id}")
    public ResponseEntity<ResponseDto<PutUserHttpResponseDto>> updateUser(@PathVariable Long id, @Valid @RequestBody PutUserHttpRequestDto requestDto) {
        PutUserHttpResponseDto user = putUserService.updateUser(id, requestDto);
        return ResponseEntity.ok(ResponseDto.success(user));
    }
}

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class PutUserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public PutUserHttpResponseDto updateUser(Long id, PutUserHttpRequestDto requestDto) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        
        User user = userOpt.get();
        if (requestDto.getName() != null) {
            user.setName(requestDto.getName());
        }
        if (requestDto.getPassword() != null && !requestDto.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }
        user.setUpdatedAt(LocalDateTime.now());
        
        User savedUser = userRepository.save(user);
        return convertToHttpResponseDto(savedUser);
    }
    
    private PutUserHttpResponseDto convertToHttpResponseDto(User user) {
        PutUserHttpResponseDto dto = new PutUserHttpResponseDto();
        dto.setId(user.getId());
        dto.setUserid(user.getUserid());
        dto.setName(user.getName());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}

@Data
class PutUserHttpRequestDto {
    private String name;
    private String password;
}

@Data
class PutUserHttpResponseDto {
    private Long id;
    private String userid;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}