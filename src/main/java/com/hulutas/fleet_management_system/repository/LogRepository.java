package com.hulutas.fleet_management_system.repository;

import com.hulutas.fleet_management_system.model.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntry, Long> {
}
