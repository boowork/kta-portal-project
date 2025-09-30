package com.kta.portal.admin.feature.api.partner;

import com.kta.portal.admin.dto.ResponseDto;
import com.kta.portal.admin.exception.ResourceNotFoundException;
import com.kta.portal.admin.feature.repository.PartnerRepository;
import com.kta.portal.admin.feature.repository.model.Partner;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PutPartnerController {
    
    private final PutPartnerService putPartnerService;
    
    @PutMapping("/api/partners/{id}")
    public ResponseEntity<ResponseDto<PutPartnerHttpResponseDto>> updatePartner(@PathVariable UUID id, @Valid @RequestBody PutPartnerHttpRequestDto requestDto) {
        PutPartnerHttpResponseDto partner = putPartnerService.updatePartner(id, requestDto);
        return ResponseEntity.ok(ResponseDto.success(partner));
    }
}

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class PutPartnerService {
    
    private final PartnerRepository partnerRepository;
    
    @Transactional
    public PutPartnerHttpResponseDto updatePartner(UUID id, PutPartnerHttpRequestDto requestDto) {
        Optional<Partner> partnerOpt = partnerRepository.findById(id);
        if (partnerOpt.isEmpty()) {
            throw new ResourceNotFoundException("Partner not found");
        }
        
        Partner partner = partnerOpt.get();
        if (requestDto.getPartnerName() != null) {
            partner.setPartnerName(requestDto.getPartnerName());
        }
        if (requestDto.getIsActive() != null) {
            partner.setIsActive(requestDto.getIsActive());
        }
        
        Partner savedPartner = partnerRepository.save(partner);
        return convertToHttpResponseDto(savedPartner);
    }
    
    private PutPartnerHttpResponseDto convertToHttpResponseDto(Partner partner) {
        PutPartnerHttpResponseDto dto = new PutPartnerHttpResponseDto();
        dto.setId(partner.getId());
        dto.setPartnerName(partner.getPartnerName());
        dto.setIsActive(partner.getIsActive());
        dto.setCreatedAt(partner.getCreatedAt());
        return dto;
    }
}

@Data
class PutPartnerHttpRequestDto {
    private String partnerName;
    private Boolean isActive;
}

@Data
class PutPartnerHttpResponseDto {
    private UUID id;
    private String partnerName;
    private Boolean isActive;
    private LocalDateTime createdAt;
}