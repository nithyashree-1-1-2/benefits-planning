package com.marvel.benefits_planning.repository;

import com.marvel.benefits_planning.entity.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, Long> {

    List<ChangeLog> findByEntityTypeAndEntityIdOrderByChangedAtDesc(String entityType, Long entityId);
}