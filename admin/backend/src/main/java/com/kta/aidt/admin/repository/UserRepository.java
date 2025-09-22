package com.kta.aidt.admin.repository;

import com.kta.aidt.admin.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    Optional<User> findByUserid(String userid);
    boolean existsByUserid(String userid);
}