package com.kta.portal.admin.feature.api.user;

import com.kta.portal.admin.dto.ResponseDto;
import com.kta.portal.admin.exception.ResourceNotFoundException;
import com.kta.portal.admin.feature.repository.UserRepository;
import com.kta.portal.admin.feature.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DeleteUserController {
    
    private final DeleteUserService deleteUserService;
    
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteUser(@PathVariable Long id) {
        deleteUserService.deleteUser(id);
        return ResponseEntity.ok(ResponseDto.success(null));
    }
}

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class DeleteUserService {
    
    private final UserRepository userRepository;
    
    @Transactional
    public void deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.delete(userOpt.get());
    }
}