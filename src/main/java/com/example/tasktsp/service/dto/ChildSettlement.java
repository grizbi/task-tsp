package com.example.tasktsp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChildSettlement {
    private BigDecimal liability;
    private long chargeableHoursSpentInSchool;
    private String firstName;
    private String lastName;
}
