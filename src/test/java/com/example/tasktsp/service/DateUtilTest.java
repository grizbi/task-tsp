package com.example.tasktsp.service;

import com.example.tasktsp.repository.entity.Attendance;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilTest {
    private static Attendance createAttendanceWithMonth(int month) {
        LocalDateTime dateTime = LocalDateTime.of(2024, month, 15, 12, 0);
        Timestamp timestamp = Timestamp.valueOf(dateTime);

        return Attendance.builder().entryDate(timestamp).build();
    }

    @Test
    void shouldRemoveAttendancesFromUnwantedMonths() {
        List<Attendance> attendances = new ArrayList<>();
        attendances.add(createAttendanceWithMonth(3));
        attendances.add(createAttendanceWithMonth(4));
        attendances.add(createAttendanceWithMonth(5));

        DateUtil.removeAttendancesFromUnwantedMonths(2, attendances);

        assertEquals(0, attendances.size());
    }


}