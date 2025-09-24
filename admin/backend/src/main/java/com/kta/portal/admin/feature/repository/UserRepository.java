package com.kta.portal.admin.feature.repository;

import com.kta.portal.admin.feature.repository.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    List<User> findAll();
    boolean existsByUserid(String userid);
}
