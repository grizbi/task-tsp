package com.example.tasktsp.controller;

import com.example.tasktsp.service.SettlementService;
import com.example.tasktsp.service.dto.ParentSettlementSummary;
import com.example.tasktsp.service.dto.SchoolSettlement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementService settlementService;

    @GetMapping("/parents/{id}/settlement")
    public ParentSettlementSummary getMonthlyParentSettlement(@PathVariable Long id, @RequestParam int month) {
        return settlementService.getMonthlyParentSettlement(id, month);
    }

    @GetMapping("/schools/{id}/settlement")
    public SchoolSettlement getMonthlySchoolSettlement(@PathVariable Long id, @RequestParam int month) {
        return settlementService.getMonthlySchoolSettlement(id, month);
    }
}
