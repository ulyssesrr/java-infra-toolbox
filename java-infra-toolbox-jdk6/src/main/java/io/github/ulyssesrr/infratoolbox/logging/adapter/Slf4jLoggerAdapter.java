package io.github.ulyssesrr.infratoolbox.logging.adapter;

import org.slf4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLoggerAdapter implements LoggerAdapter {

    private final Logger logger = LoggerFactory.getLogger("MdcExceptionLogger");

    public void putMdc(String key, Object value) {

        if (key == null) return;

        MDC.put(key, String.valueOf(value));
    }

    public void removeMdc(String key) {

        MDC.remove(key);

    }

    public void error(String message, Throwable throwable) {

        logger.error(message, throwable);

    }

}
