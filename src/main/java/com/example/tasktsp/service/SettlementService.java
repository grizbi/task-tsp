package com.example.tasktsp.service;

import com.example.tasktsp.repository.AttendanceRepository;
import com.example.tasktsp.repository.ChildRepository;
import com.example.tasktsp.repository.ParentRepository;
import com.example.tasktsp.repository.SchoolRepository;
import com.example.tasktsp.repository.entity.Attendance;
import com.example.tasktsp.repository.entity.Child;
import com.example.tasktsp.repository.entity.Parent;
import com.example.tasktsp.repository.entity.School;
import com.example.tasktsp.repository.exception.UserNotFoundException;
import com.example.tasktsp.service.converter.ChildrenLiabilityToChildrenSettlementsConverter;
import com.example.tasktsp.service.dto.ChildSettlement;
import com.example.tasktsp.service.dto.ParentSettlementSummary;
import com.example.tasktsp.service.dto.SchoolSettlement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettlementService {
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final AttendanceRepository attendanceRepository;
    private final SchoolRepository schoolRepository;

    public ParentSettlementSummary getMonthlyParentSettlement(Long parentId, int month) {
        Parent parent = parentRepository.findById(parentId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        List<Child> children = childRepository.findChildrenByParentId(parent);
        List<Attendance> attendances = attendanceRepository.getAttendanceByChildIdIn(children);

        DateUtil.removeAttendancesFromUnwantedMonths(month, attendances);

        Map<Child, Integer> childrenLiabilitySummary = ChildrenLiabilitySummaryService.getChildrenLiabilitySummary(attendances);

        ParentSettlementSummary parentSettlementSummary = ParentSettlementSummary
                .builder()
                .parentFirstName(parent.getFirstName())
                .parentLastName(parent.getLastName())
                .build();

        parentSettlementSummary.setChildrenSettlements(ChildrenLiabilityToChildrenSettlementsConverter.convert(childrenLiabilitySummary));
        parentSettlementSummary.setTotalLiability(parentSettlementSummary.getChildrenSettlements()
                .stream()
                .map(ChildSettlement::getLiability)
                .reduce(BigDecimal.valueOf(0.00), BigDecimal::add));

        return parentSettlementSummary;
    }

    public SchoolSettlement getMonthlySchoolSettlement(Long schoolId, int month) {
        School school = schoolRepository.findById(schoolId).orElseThrow(() -> {
            throw new IllegalStateException();
        });
        List<Child> children = childRepository.findChildrenBySchoolId(school);
        List<Attendance> attendances = attendanceRepository.getAttendanceByChildIdIn(children);

        DateUtil.removeAttendancesFromUnwantedMonths(month, attendances);

        Map<Child, Integer> childLiabilitySummary = ChildrenLiabilitySummaryService.getChildrenLiabilitySummary(attendances);

        BigDecimal monthlySchoolLiability = ChildrenLiabilityToChildrenSettlementsConverter.convert(childLiabilitySummary)
                .stream()
                .map(ChildSettlement::getLiability)
                .reduce(BigDecimal.valueOf(0.00), BigDecimal::add);

        return SchoolSettlement.builder()
                .schoolName(school.getName())
                .monthlyLiability(monthlySchoolLiability).build();
    }

}
