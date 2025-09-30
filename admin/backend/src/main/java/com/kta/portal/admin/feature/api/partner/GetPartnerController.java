package com.kta.portal.admin.feature.api.partner;

import com.kta.portal.admin.dto.ResponseDto;
import com.kta.portal.admin.exception.ResourceNotFoundException;
import com.kta.portal.admin.feature.repository.PartnerRepository;
import com.kta.portal.admin.feature.repository.model.Partner;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GetPartnerController {
    
    private final GetPartnerService getPartnerService;
    
    @GetMapping("/api/partners/{id}")
    public ResponseEntity<ResponseDto<GetPartnerHttpResponseDto>> getPartnerById(@PathVariable UUID id) {
        GetPartnerHttpResponseDto partner = getPartnerService.getPartnerById(id);
        return ResponseEntity.ok(ResponseDto.success(partner));
    }
}

@Service
@RequiredArgsConstructor
class GetPartnerService {
    
    private final PartnerRepository partnerRepository;
    
    public GetPartnerHttpResponseDto getPartnerById(UUID id) {
        Optional<Partner> partnerOpt = partnerRepository.findById(id);
        if (partnerOpt.isEmpty()) {
            throw new ResourceNotFoundException("Partner not found");
        }
        return convertToHttpResponseDto(partnerOpt.get());
    }
    
    private GetPartnerHttpResponseDto convertToHttpResponseDto(Partner partner) {
        GetPartnerHttpResponseDto dto = new GetPartnerHttpResponseDto();
        dto.setId(partner.getId());
        dto.setPartnerName(partner.getPartnerName());
        dto.setIsActive(partner.getIsActive());
        dto.setCreatedAt(partner.getCreatedAt());
        return dto;
    }
}

@Data
class GetPartnerHttpResponseDto {
    private UUID id;
    private String partnerName;
    private Boolean isActive;
    private LocalDateTime createdAt;
}