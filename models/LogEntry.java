package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import enums.LogLevel;

public class LogEntry {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final LocalDateTime timestamp;
    private final LogLevel level;
    private final String username;
    private final String action;
    private final String details;

    public LogEntry(
            LogLevel level,
            String username,
            String action,
            String details) {

        this.timestamp = LocalDateTime.now();
        this.level = level;
        this.username = username;
        this.action = action;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getUsername() {
        return username;
    }

    public String getAction() {
        return action;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "[" + timestamp.format(FORMATTER) + "] "
                + "[" + level + "] "
                + "[User: " + username + "] "
                + "[Action: " + action + "] "
                + details;
    }
}
