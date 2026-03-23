package io.github.ulyssesrr.infratoolbox.logging.adapter;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Log4jLoggerAdapter implements LoggerAdapter {

    @NonNull
    private final Logger logger;

    public Log4jLoggerAdapter(String loggerName) {
        this(Logger.getLogger(loggerName));
    }

    public Log4jLoggerAdapter(Class<?> clazz) {
        this(Logger.getLogger(clazz));
    }

    public void putMdc(String key, Object value) {
        MDC.put(key, value);
    }

    public void removeMdc(String key) {
        MDC.remove(key);
    }

    public void info(String message, Throwable throwable) {
        logger.info(message, throwable);
    }

    public void warn(String message, Throwable throwable) {
        logger.warn(message, throwable);
    }

    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

}
