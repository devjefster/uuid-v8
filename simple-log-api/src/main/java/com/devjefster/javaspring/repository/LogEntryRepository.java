package com.devjefster.javaspring.repository;

import com.devjefster.javaspring.model.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface LogEntryRepository extends JpaRepository<LogEntry, UUID> {
    List<LogEntry> findByTimestampBetweenOrderByTimestamp(Instant start, Instant end);
}
