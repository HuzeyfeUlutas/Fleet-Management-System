package com.hulutas.fleet_management_system.service.impl;

import com.hulutas.fleet_management_system.model.LogEntry;
import com.hulutas.fleet_management_system.repository.LogRepository;
import com.hulutas.fleet_management_system.service.LogService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class DatabaseLogServiceImpl implements LogService {
    private final LogRepository logRepository;
    private static final Logger logger = LoggerFactory.getLogger(DatabaseLogServiceImpl.class);

    public DatabaseLogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void log(String level, String message) {
        LogEntry logEntry = new LogEntry(level, message, LocalDateTime.now());
        logRepository.save(logEntry);

        switch (level.toUpperCase()) {
            case "ERROR":
                logger.error(message);
                break;
            case "DEBUG":
                logger.debug(message);
                break;
            case "WARN":
                logger.warn(message);
                break;
            default:
                logger.info(message);
        }
    }
}
