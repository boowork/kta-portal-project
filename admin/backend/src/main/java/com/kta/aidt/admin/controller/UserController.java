package com.kta.aidt.admin.controller;

import com.kta.aidt.admin.dto.ResponseDto;
import com.kta.aidt.admin.dto.UserCreateDto;
import com.kta.aidt.admin.dto.UserResponseDto;
import com.kta.aidt.admin.dto.UserUpdateDto;
import com.kta.aidt.admin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping
    public ResponseEntity<ResponseDto<List<UserResponseDto>>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(ResponseDto.success(users));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UserResponseDto>> getUserById(@PathVariable Long id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(ResponseDto.success(user));
    }
    
    @PostMapping
    public ResponseEntity<ResponseDto<UserResponseDto>> createUser(@Valid @RequestBody UserCreateDto createDto) {
        UserResponseDto user = userService.createUser(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(user));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<UserResponseDto>> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDto updateDto) {
        UserResponseDto user = userService.updateUser(id, updateDto);
        return ResponseEntity.ok(ResponseDto.success(user));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ResponseDto.success(null));
    }
}