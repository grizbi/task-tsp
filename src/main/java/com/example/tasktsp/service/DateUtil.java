package com.example.tasktsp.service;

import com.example.tasktsp.repository.entity.Attendance;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public final class DateUtil {

    public static void removeAttendancesFromUnwantedMonths(int month, List<Attendance> attendances) {
        attendances.removeIf(attendance -> {
            Timestamp entryDateTimeStamp = attendance.getEntryDate();
            LocalDate localDateEntryDate = entryDateTimeStamp.toLocalDateTime().toLocalDate();
            int monthAttendance = localDateEntryDate.getMonthValue();
            return monthAttendance != month;
        });

    }
}
