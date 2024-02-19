package com.example.tasktsp.repository;

import com.example.tasktsp.repository.entity.Child;
import com.example.tasktsp.repository.entity.Parent;
import com.example.tasktsp.repository.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Long> {
    List<Child> findChildrenByParentId(Parent parentId);
    List<Child> findChildrenBySchoolId(School schoolId);
}
