package com.kta.portal.admin.feature.api.user;

import com.kta.portal.admin.dto.ResponseDto;
import com.kta.portal.admin.feature.repository.UserRepository;
import com.kta.portal.admin.feature.repository.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GetUsersController {
    
    private final GetUsersService getUsersService;
    
    @GetMapping("/api/users")
    public ResponseEntity<ResponseDto<List<GetUsersHttpResponseDto>>> getAllUsers() {
        List<GetUsersHttpResponseDto> users = getUsersService.getAllUsers();
        return ResponseEntity.ok(ResponseDto.success(users));
    }
}

@Service
@RequiredArgsConstructor
class GetUsersService {
    
    private final UserRepository userRepository;
    
    public List<GetUsersHttpResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToHttpResponseDto)
                .collect(Collectors.toList());
    }
    
    private GetUsersHttpResponseDto convertToHttpResponseDto(User user) {
        GetUsersHttpResponseDto dto = new GetUsersHttpResponseDto();
        dto.setId(user.getId());
        dto.setUserid(user.getUserid());
        dto.setName(user.getName());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}

@Data
class GetUsersHttpResponseDto {
    private Long id;
    private String userid;
    private String name;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}