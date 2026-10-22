package com.task.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.task.rental.entity.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    long countByHasDiskTrue();

    @Query("SELECT COUNT(v) FROM Visitor v")
    long countAllVisitors();
}