package managers;

import java.util.ArrayList;
import java.util.List;

import enums.LogLevel;
import models.LogEntry;

public class LogManager {

    private final List<LogEntry> logs = new ArrayList<>();

    public void addLog(
            LogLevel level,
            String username,
            String action,
            String details) {

        logs.add(new LogEntry(level, username, action, details));
    }

    public List<LogEntry> getLogs() {
        return new ArrayList<>(logs);
    }

    public void printLogs() {
        for (LogEntry log : logs) {
            System.out.println(log);
        }
    }
}
