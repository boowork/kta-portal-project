package com.kta.portal.admin.feature.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("partners")
public class Partner {
    @Id
    private UUID id;
    private String partnerName;
    private Boolean isActive;
    private LocalDateTime createdAt;
}