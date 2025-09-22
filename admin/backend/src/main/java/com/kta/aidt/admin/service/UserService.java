package com.kta.aidt.admin.service;

import com.kta.aidt.admin.dto.UserCreateDto;
import com.kta.aidt.admin.dto.UserResponseDto;
import com.kta.aidt.admin.dto.UserUpdateDto;
import com.kta.aidt.admin.entity.User;
import com.kta.aidt.admin.exception.BadRequestException;
import com.kta.aidt.admin.exception.ResourceNotFoundException;
import com.kta.aidt.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    
    private final UserRepository userRepository;
    
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return convertToResponseDto(user);
    }
    
    @Transactional
    public UserResponseDto createUser(UserCreateDto createDto) {
        if (userRepository.existsByUserid(createDto.getUserid())) {
            throw new BadRequestException("User with this userid already exists");
        }
        
        User user = new User();
        user.setUserid(createDto.getUserid());
        user.setPassword(createDto.getPassword());
        user.setName(createDto.getName());
        user.setRole(createDto.getRole());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        User savedUser = userRepository.save(user);
        return convertToResponseDto(savedUser);
    }
    
    @Transactional
    public UserResponseDto updateUser(Long id, UserUpdateDto updateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        if (updateDto.getName() != null) {
            user.setName(updateDto.getName());
        }
        if (updateDto.getRole() != null) {
            user.setRole(updateDto.getRole());
        }
        user.setUpdatedAt(LocalDateTime.now());
        
        User savedUser = userRepository.save(user);
        return convertToResponseDto(savedUser);
    }
    
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }
    
    private UserResponseDto convertToResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUserid(user.getUserid());
        dto.setName(user.getName());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}