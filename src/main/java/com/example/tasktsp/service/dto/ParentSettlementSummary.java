package com.example.tasktsp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParentSettlementSummary {
    List<ChildSettlement> childrenSettlements = new ArrayList<>();
    private BigDecimal totalLiability;
    private String parentFirstName;
    private String parentLastName;
}
