package com.kta.portal.admin.feature.repository;

import com.kta.portal.admin.feature.repository.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID>, ListPagingAndSortingRepository<User, UUID> {
    @Override
    @NonNull
    List<User> findAll();
    
    @Override
    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);
    
    boolean existsByUserid(String userid);
}
