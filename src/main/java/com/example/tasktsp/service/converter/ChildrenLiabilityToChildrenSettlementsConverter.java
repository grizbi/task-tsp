package com.example.tasktsp.service.converter;

import com.example.tasktsp.repository.entity.Child;
import com.example.tasktsp.service.dto.ChildSettlement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChildrenLiabilityToChildrenSettlementsConverter {
    public static List<ChildSettlement> convert(Map<Child, Integer> childLiabilitySummary) {
        List<ChildSettlement> childSettlements = new ArrayList<>();
        for (Map.Entry<Child, Integer> entry : childLiabilitySummary.entrySet()) {
            Child child = entry.getKey();
            BigDecimal liability = child.getSchoolId().getHourPrice().multiply(BigDecimal.valueOf(entry.getValue()));
            ChildSettlement childSettlement = ChildSettlement.builder()
                    .liability(liability)
                    .firstName(child.getFirstName())
                    .lastName(child.getLastName())
                    .chargeableHoursSpentInSchool(entry.getValue())
                    .build();

            childSettlements.add(childSettlement);
        }
        return childSettlements;
    }
}
