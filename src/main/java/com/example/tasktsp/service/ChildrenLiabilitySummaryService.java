package com.example.tasktsp.service;

import com.example.tasktsp.repository.entity.Attendance;
import com.example.tasktsp.repository.entity.Child;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ChildrenLiabilitySummaryService {
    public static Map<Child, Integer> getChildrenLiabilitySummary(List<Attendance> attendances) {
        Map<Child, Integer> childrenLiabilitySummary = new LinkedHashMap<>();
        for (Attendance attendance : attendances) {
            int chargeableHours = calculateChargeableHours(attendance);
            childrenLiabilitySummary.put(attendance.getChildId(), childrenLiabilitySummary.getOrDefault(
                    attendance.getChildId(), 0) + chargeableHours);
        }
        return childrenLiabilitySummary;
    }

    private static int calculateChargeableHours(Attendance attendance) {
        LocalTime entryLocalTime = attendance.getEntryDate().toLocalDateTime().toLocalTime();
        LocalTime exitLocalTime = attendance.getExitDate().toLocalDateTime().toLocalTime();
        int chargeableHours = 0;

        List<Integer> freeHours = IntStream.range(7, 12).boxed().toList();

        for (int i = entryLocalTime.getHour(); i < exitLocalTime.getHour(); i++) {
            if (!freeHours.contains(i)) {
                chargeableHours++;
            }
        }

        if (!freeHours.contains(exitLocalTime.getHour()) && exitLocalTime.getMinute() > 0) {
            chargeableHours++;
        }

        return chargeableHours;
    }
}
