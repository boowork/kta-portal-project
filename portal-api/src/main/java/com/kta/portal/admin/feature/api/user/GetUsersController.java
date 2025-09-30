package com.kta.portal.admin.feature.api.user;

import com.kta.portal.admin.dto.ResponseDto;
import com.kta.portal.admin.feature.repository.UserRepository;
import com.kta.portal.admin.feature.repository.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GetUsersController {
    
    private final GetUsersService getUsersService;
    
    @GetMapping("/api/users")
    public ResponseEntity<ResponseDto<GetUsersPageResponseDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        GetUsersPageResponseDto users = getUsersService.getAllUsers(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ResponseDto.success(users));
    }
}

@Service
@RequiredArgsConstructor
class GetUsersService {
    
    private final UserRepository userRepository;
    
    public GetUsersPageResponseDto getAllUsers(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<User> userPage = userRepository.findAll(pageable);
        
        List<GetUsersHttpResponseDto> users = userPage.getContent().stream()
                .map(this::convertToHttpResponseDto)
                .collect(Collectors.toList());
        
        GetUsersPageResponseDto response = new GetUsersPageResponseDto();
        response.setContent(users);
        response.setPage(userPage.getNumber());
        response.setSize(userPage.getSize());
        response.setTotalElements(userPage.getTotalElements());
        response.setTotalPages(userPage.getTotalPages());
        response.setFirst(userPage.isFirst());
        response.setLast(userPage.isLast());
        
        return response;
    }
    
    private GetUsersHttpResponseDto convertToHttpResponseDto(User user) {
        GetUsersHttpResponseDto dto = new GetUsersHttpResponseDto();
        dto.setId(user.getId());
        dto.setUserid(user.getUserid());
        dto.setName(user.getName());
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

@Data
class GetUsersPageResponseDto {
    private List<GetUsersHttpResponseDto> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
}