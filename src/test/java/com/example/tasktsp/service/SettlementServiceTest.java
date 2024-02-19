package com.example.tasktsp.service;

import com.example.tasktsp.repository.AttendanceRepository;
import com.example.tasktsp.repository.ChildRepository;
import com.example.tasktsp.repository.ParentRepository;
import com.example.tasktsp.repository.SchoolRepository;
import com.example.tasktsp.repository.entity.Attendance;
import com.example.tasktsp.repository.entity.Child;
import com.example.tasktsp.repository.entity.Parent;
import com.example.tasktsp.repository.entity.School;
import com.example.tasktsp.service.dto.ChildSettlement;
import com.example.tasktsp.service.dto.ParentSettlementSummary;
import com.example.tasktsp.service.dto.SchoolSettlement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SettlementServiceTest {

    private static final String SCHOOL_NAME = "Test School";
    private static final String PARENT_FIRST_NAME = "John";
    private static final String PARENT_LAST_NAME = "Doe";
    private static final String CHILD_FIRST_NAME = "Tomasz";
    private static final String CHILD_LAST_NAME = "Kozlowski";
    private static final String CHILD_FIRST_NAME2 = "Johny";
    private static final String CHILD_LAST_NAME2 = "Bravo";
    @Mock
    private ParentRepository parentRepository;
    @Mock
    private ChildRepository childRepository;
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private SchoolRepository schoolRepository;
    @InjectMocks
    private SettlementService settlementService;

    public static List<School> getSchools() {
        return Arrays.asList(
                School.builder()
                        .id(1L)
                        .name(SCHOOL_NAME)
                        .hourPrice(new BigDecimal("15.00"))
                        .build(),
                School.builder()
                        .id(2L)
                        .name(SCHOOL_NAME)
                        .hourPrice(new BigDecimal("30.00"))
                        .build()
        );
    }

    private static List<Attendance> getAttendances(List<Child> children) {
        return new ArrayList<>(Arrays.asList(
                Attendance.builder()
                        .entryDate(Timestamp.valueOf("2024-02-17 05:00:00"))
                        .exitDate(Timestamp.valueOf("2024-02-17 16:00:00"))
                        .childId(children.get(0))
                        .build(),

                Attendance.builder()
                        .entryDate(Timestamp.valueOf("2024-02-17 04:00:00"))
                        .exitDate(Timestamp.valueOf("2024-02-17 15:00:00"))
                        .childId(children.get(1))
                        .build()
        ));
    }

    private static void assertChildrenSettlements(ChildSettlement childSettlement, String expectedFirstName,
                                                  String expectedLastName, BigDecimal expectedLiability) {

        assertEquals(expectedFirstName, childSettlement.getFirstName());
        assertEquals(expectedLastName, childSettlement.getLastName());
        assertEquals(expectedLiability, childSettlement.getLiability());
        assertEquals(6, childSettlement.getChargeableHoursSpentInSchool());
    }

    private static Parent getParent() {
        return Parent.builder()
                .id(1L)
                .firstName(PARENT_FIRST_NAME)
                .lastName(PARENT_LAST_NAME)
                .build();
    }

    @Test
    void shouldGetMonthlyParentSettlement() {
        Parent parent = getParent();

        List<School> schools = getSchools();

        List<Child> children = Arrays.asList(
                Child.builder().firstName(CHILD_FIRST_NAME).lastName(CHILD_LAST_NAME).parentId(parent).schoolId(schools.get(0)).build(),
                Child.builder().firstName(CHILD_FIRST_NAME2).lastName(CHILD_LAST_NAME2).parentId(parent).schoolId(schools.get(1)).build()
        );

        List<Attendance> attendances = getAttendances(children);

        when(parentRepository.findById(1L)).thenReturn(Optional.of(parent));
        when(childRepository.findChildrenByParentId(parent)).thenReturn(children);
        when(attendanceRepository.getAttendanceByChildIdIn(children)).thenReturn(attendances);

        ParentSettlementSummary settlementSummary = settlementService.getMonthlyParentSettlement(1L, 2);

        assertEquals(PARENT_FIRST_NAME, settlementSummary.getParentFirstName());
        assertEquals(PARENT_LAST_NAME, settlementSummary.getParentLastName());
        assertEquals(new BigDecimal("270.00"), settlementSummary.getTotalLiability());
        assertEquals(2, settlementSummary.getChildrenSettlements().size());
        assertChildrenSettlements(settlementSummary.getChildrenSettlements().get(0), CHILD_FIRST_NAME, CHILD_LAST_NAME,
                new BigDecimal("90.00"));
        assertChildrenSettlements(settlementSummary.getChildrenSettlements().get(1), CHILD_FIRST_NAME2, CHILD_LAST_NAME2,
                new BigDecimal("180.00"));
    }

    @Test
    void shouldGetMonthlySchoolSettlement() {
        School school = getSchools().get(0);

        List<Child> children = Arrays.asList(
                Child.builder().firstName(CHILD_FIRST_NAME).lastName(CHILD_LAST_NAME).schoolId(school).build(),
                Child.builder().firstName(CHILD_FIRST_NAME2).lastName(CHILD_LAST_NAME2).schoolId(school).build()
        );

        List<Attendance> attendances = getAttendances(children);

        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(childRepository.findChildrenBySchoolId(school)).thenReturn(children);
        when(attendanceRepository.getAttendanceByChildIdIn(children)).thenReturn(attendances);

        SchoolSettlement settlement = settlementService.getMonthlySchoolSettlement(1L, 2);

        assertEquals(SCHOOL_NAME, settlement.getSchoolName());
        assertEquals(new BigDecimal("180.00"), settlement.getMonthlyLiability());
    }
}
