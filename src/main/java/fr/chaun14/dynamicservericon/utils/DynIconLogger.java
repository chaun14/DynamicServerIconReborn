package fr.chaun14.dynamicservericon.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DynIconLogger {
    private final Logger logger;
    private final String prefix;

    public DynIconLogger(Logger baseLogger, String prefix) {
        this.logger = baseLogger;
        this.prefix = prefix;
    }

    public void info(String message) {
        logger.info(format(message, "[INFO]"));
    }

    public void warning(String message) {
        logger.warning(format(message, "[WARNING]"));
    }

    public void severe(String message) {
        logger.severe(format(message, "[SEVERE]"));
    }

    public void debug(String message) {
        logger.fine(format(message, "[DEBUG]"));
    }

    public void log(Level level, String message) {
        logger.log(level, format(message, ""));
    }

    public void log(Level level, String message, Throwable throwable) {
        logger.log(level, format(message, ""), throwable);
    }

    private String format(String message, String type) {
        return prefix.strip() + type + " " + message;
    }
}
