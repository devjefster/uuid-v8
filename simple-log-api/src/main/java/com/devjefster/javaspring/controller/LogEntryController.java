package com.devjefster.javaspring.controller;

import com.devjefster.javaspring.model.LogEntry;
import com.devjefster.javaspring.service.LogEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogEntryController {

    private final LogEntryService logEntryService;

    @PostMapping
    public LogEntry createLog(@RequestParam String message) {
        return logEntryService.createLog(message);
    }

    @GetMapping("/{id}")
    public LogEntry getLogById(@PathVariable UUID id) {
        return logEntryService.getLogById(id);
    }

    @GetMapping
    public List<LogEntry> getLogsInTimeRange(
            @RequestParam Instant start,
            @RequestParam Instant end) {
        return logEntryService.getLogsInTimeRange(start, end);
    }
}
