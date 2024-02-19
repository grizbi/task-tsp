package com.example.tasktsp.repository;

import com.example.tasktsp.repository.entity.Attendance;
import com.example.tasktsp.repository.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> getAttendanceByChildIdIn(List<Child> childId);
}
