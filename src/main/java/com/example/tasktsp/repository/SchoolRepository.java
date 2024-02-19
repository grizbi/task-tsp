package com.example.tasktsp.repository;

import com.example.tasktsp.repository.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
