package com.kta.aidt.admin.feature.user;

import com.kta.aidt.admin.dto.ResponseDto;
import com.kta.aidt.admin.exception.ResourceNotFoundException;
import com.kta.aidt.admin.feature.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class DeleteUserController {
    
    private final DeleteUserService deleteUserService;
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteUser(@PathVariable Long id) {
        deleteUserService.deleteUser(id);
        return ResponseEntity.ok(ResponseDto.success(null));
    }
}

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class DeleteUserService {
    
    private final DeleteUserDao deleteUserDao;
    
    @Transactional
    public void deleteUser(Long id) {
        DeleteUserDaoRequestDto daoRequest = new DeleteUserDaoRequestDto();
        daoRequest.setId(id);
        deleteUserDao.deleteUser(daoRequest);
    }
}

@Repository
@RequiredArgsConstructor
class DeleteUserDao {
    
    private final DeleteUserRepository deleteUserRepository;
    
    public void deleteUser(DeleteUserDaoRequestDto requestDto) {
        Optional<User> userOpt = deleteUserRepository.findById(requestDto.getId());
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        deleteUserRepository.delete(userOpt.get());
    }
}

interface DeleteUserRepository extends CrudRepository<User, Long> {
}

class DeleteUserDaoRequestDto {
    private Long id;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}