package com.devjefster.javaspring.service;

import com.devjefster.javaspring.model.LogEntry;
import com.devjefster.javaspring.repository.LogEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogEntryService {
    private final LogEntryRepository logEntryRepository;


    public LogEntry createLog(String message) {
        LogEntry logEntry = new LogEntry(message);
        return logEntryRepository.save(logEntry);
    }

    public List<LogEntry> getLogsInTimeRange(Instant start, Instant end) {
        return logEntryRepository.findByTimestampBetweenOrderByTimestamp(start, end);
    }

    public LogEntry getLogById(UUID id) {
        return logEntryRepository.findById(id).orElse(null);
    }
}
