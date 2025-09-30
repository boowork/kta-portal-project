package com.kta.portal.admin.feature.repository;

import com.kta.portal.admin.feature.repository.model.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public interface PartnerRepository extends CrudRepository<Partner, UUID>, ListPagingAndSortingRepository<Partner, UUID> {
    @Override
    @NonNull
    List<Partner> findAll();
    
    @Override
    @NonNull
    Page<Partner> findAll(@NonNull Pageable pageable);
}