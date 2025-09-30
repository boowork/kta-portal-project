package com.kta.portal.admin.feature.api.user;

import com.kta.portal.admin.dto.ResponseDto;
import com.kta.portal.admin.exception.ResourceNotFoundException;
import com.kta.portal.admin.feature.repository.UserRepository;
import com.kta.portal.admin.feature.repository.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GetUserController {
    
    private final GetUserService getUserService;
    
    @GetMapping("/api/users/{id}")
    public ResponseEntity<ResponseDto<GetUserHttpResponseDto>> getUserById(@PathVariable UUID id) {
        GetUserHttpResponseDto user = getUserService.getUserById(id);
        return ResponseEntity.ok(ResponseDto.success(user));
    }
}

@Service
@RequiredArgsConstructor
class GetUserService {
    
    private final UserRepository userRepository;
    
    public GetUserHttpResponseDto getUserById(UUID id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return convertToHttpResponseDto(userOpt.get());
    }
    
    private GetUserHttpResponseDto convertToHttpResponseDto(User user) {
        GetUserHttpResponseDto dto = new GetUserHttpResponseDto();
        dto.setId(user.getId());
        dto.setUserid(user.getUserid());
        dto.setName(user.getName());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}

@Data
class GetUserHttpResponseDto {
    private UUID id;
    private String userid;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}