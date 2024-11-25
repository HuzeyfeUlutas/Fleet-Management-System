package com.hulutas.fleet_management_system.service;

import com.hulutas.fleet_management_system.model.LogEntry;
import com.hulutas.fleet_management_system.repository.LogRepository;
import com.hulutas.fleet_management_system.service.impl.DatabaseLogServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatabaseLogServiceImplTest {

    @Mock
    private LogRepository logRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private DatabaseLogServiceImpl logService;

    @Test
    void shouldSaveLogEntryAndLogErrorLevel() {
        String level = "ERROR";
        String message = "This is an error message";
        ArgumentCaptor<LogEntry> logEntryCaptor = ArgumentCaptor.forClass(LogEntry.class);

        logService.log(level, message);

        verify(logRepository, times(1)).save(logEntryCaptor.capture());

        LogEntry savedLog = logEntryCaptor.getValue();
        assertEquals(level, savedLog.getLevel());
        assertEquals(message, savedLog.getMessage());
    }

    @Test
    void shouldSaveLogEntryAndLogDebugLevel() {
        String level = "DEBUG";
        String message = "This is a debug message";
        ArgumentCaptor<LogEntry> logEntryCaptor = ArgumentCaptor.forClass(LogEntry.class);

        logService.log(level, message);

        verify(logRepository, times(1)).save(logEntryCaptor.capture());

        LogEntry savedLog = logEntryCaptor.getValue();
        assertEquals(level, savedLog.getLevel());
        assertEquals(message, savedLog.getMessage());
    }

    @Test
    void shouldSaveLogEntryAndLogWarnLevel() {
        String level = "WARN";
        String message = "This is a warn message";
        ArgumentCaptor<LogEntry> logEntryCaptor = ArgumentCaptor.forClass(LogEntry.class);

        logService.log(level, message);

        verify(logRepository, times(1)).save(logEntryCaptor.capture());

        LogEntry savedLog = logEntryCaptor.getValue();
        assertEquals(level, savedLog.getLevel());
        assertEquals(message, savedLog.getMessage());
    }

    @Test
    void shouldSaveLogEntryAndLogDefaultInfoLevelForUnknownLevel() {
        String level = "UNKNOWN";
        String message = "This is a default info message";
        ArgumentCaptor<LogEntry> logEntryCaptor = ArgumentCaptor.forClass(LogEntry.class);

        logService.log(level, message);

        verify(logRepository, times(1)).save(logEntryCaptor.capture());

        LogEntry savedLog = logEntryCaptor.getValue();
        assertEquals(level, savedLog.getLevel());
        assertEquals(message, savedLog.getMessage());
    }
}
