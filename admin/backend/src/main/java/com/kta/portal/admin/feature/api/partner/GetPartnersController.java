package com.kta.portal.admin.feature.api.partner;

import com.kta.portal.admin.dto.ResponseDto;
import com.kta.portal.admin.feature.repository.PartnerRepository;
import com.kta.portal.admin.feature.repository.model.Partner;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GetPartnersController {
    
    private final GetPartnersService getPartnersService;
    
    @GetMapping("/api/partners")
    public ResponseEntity<ResponseDto<GetPartnersPageResponseDto>> getAllPartners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        GetPartnersPageResponseDto partners = getPartnersService.getAllPartners(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ResponseDto.success(partners));
    }
}

@Service
@RequiredArgsConstructor
class GetPartnersService {
    
    private final PartnerRepository partnerRepository;
    
    public GetPartnersPageResponseDto getAllPartners(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Partner> partnerPage = partnerRepository.findAll(pageable);
        
        List<GetPartnersHttpResponseDto> partners = partnerPage.getContent().stream()
                .map(this::convertToHttpResponseDto)
                .collect(Collectors.toList());
        
        GetPartnersPageResponseDto response = new GetPartnersPageResponseDto();
        response.setContent(partners);
        response.setPage(partnerPage.getNumber());
        response.setSize(partnerPage.getSize());
        response.setTotalElements(partnerPage.getTotalElements());
        response.setTotalPages(partnerPage.getTotalPages());
        response.setFirst(partnerPage.isFirst());
        response.setLast(partnerPage.isLast());
        
        return response;
    }
    
    private GetPartnersHttpResponseDto convertToHttpResponseDto(Partner partner) {
        GetPartnersHttpResponseDto dto = new GetPartnersHttpResponseDto();
        dto.setId(partner.getId());
        dto.setPartnerName(partner.getPartnerName());
        dto.setIsActive(partner.getIsActive());
        dto.setCreatedAt(partner.getCreatedAt());
        return dto;
    }
}

@Data
class GetPartnersHttpResponseDto {
    private UUID id;
    private String partnerName;
    private Boolean isActive;
    private LocalDateTime createdAt;
}

@Data
class GetPartnersPageResponseDto {
    private List<GetPartnersHttpResponseDto> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
}