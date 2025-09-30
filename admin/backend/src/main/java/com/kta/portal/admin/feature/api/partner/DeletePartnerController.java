package com.kta.portal.admin.feature.api.partner;

import com.kta.portal.admin.dto.ResponseDto;
import com.kta.portal.admin.exception.ResourceNotFoundException;
import com.kta.portal.admin.feature.repository.PartnerRepository;
import com.kta.portal.admin.feature.repository.model.Partner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeletePartnerController {
    
    private final DeletePartnerService deletePartnerService;
    
    @DeleteMapping("/api/partners/{id}")
    public ResponseEntity<ResponseDto<Void>> deletePartner(@PathVariable UUID id) {
        deletePartnerService.deletePartner(id);
        return ResponseEntity.ok(ResponseDto.success(null));
    }
}

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class DeletePartnerService {
    
    private final PartnerRepository partnerRepository;
    
    @Transactional
    public void deletePartner(UUID id) {
        Optional<Partner> partnerOpt = partnerRepository.findById(id);
        if (partnerOpt.isEmpty()) {
            throw new ResourceNotFoundException("Partner not found");
        }
        partnerRepository.delete(partnerOpt.get());
    }
}