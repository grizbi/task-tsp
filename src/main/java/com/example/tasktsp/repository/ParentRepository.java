package com.example.tasktsp.repository;

import com.example.tasktsp.repository.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
}
