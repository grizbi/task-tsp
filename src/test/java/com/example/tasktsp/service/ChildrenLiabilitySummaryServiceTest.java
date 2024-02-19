package com.example.tasktsp.service;

import com.example.tasktsp.repository.entity.Attendance;
import com.example.tasktsp.repository.entity.Child;
import com.example.tasktsp.repository.entity.School;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChildrenLiabilitySummaryServiceTest {

    private static Attendance createAttendance(Child child, int entryHour, int exitHour) {
        LocalDateTime entryDateTime = LocalDateTime.of(2024, 1, 1, entryHour, 0);
        LocalDateTime exitDateTime = LocalDateTime.of(2024, 1, 1, exitHour, 0);

        return Attendance.builder()
                .childId(child)
                .entryDate(Timestamp.valueOf(entryDateTime))
                .exitDate(Timestamp.valueOf(exitDateTime))
                .build();
    }

    @Test
    void shouldGetChildrenLiabilitySummary() {
        List<School> schools = SettlementServiceTest.getSchools();

        List<Attendance> attendances = new ArrayList<>();
        Child child1 = Child.builder().firstName("Chris").lastName("Dabrowski").schoolId(schools.get(0)).build();
        Child child2 = Child.builder().firstName("Dariusz").lastName("Nowakowski").schoolId(schools.get(1)).build();

        attendances.add(createAttendance(child1, 5, 14));
        attendances.add(createAttendance(child1, 8, 12));
        attendances.add(createAttendance(child2, 9, 16));
        attendances.add(createAttendance(child2, 4, 16));

        Map<Child, Integer> summary = ChildrenLiabilitySummaryService.getChildrenLiabilitySummary(attendances);

        assertEquals(4, summary.get(child1));
        assertEquals(11, summary.get(child2));
    }

    @Test
    void shouldGetSummaryMapWhenAttendancesListIsEmpty() {
        Map<Child, Integer> summary = ChildrenLiabilitySummaryService.getChildrenLiabilitySummary(new ArrayList<>());

        assertEquals(0, summary.size());
    }

}