package com.devjefster.javaspring.model;

import com.devjefster.javaspring.utils.UUIDv8Generator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "logs")
@Getter
public class LogEntry {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "log_message", nullable = false)
    private String message;

    public LogEntry() {
    }

    public LogEntry(String message) {
        this.id = UUIDv8Generator.generate();
        this.timestamp = Instant.now();
        this.message = message;
    }

}
