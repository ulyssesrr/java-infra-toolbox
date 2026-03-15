package io.github.ulyssesrr.infratoolbox.logging.adapter;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

public class Reload4jLoggerAdapter implements LoggerAdapter {

    private final Logger logger = Logger.getLogger("MdcExceptionLogger");

    public void putMdc(String key, Object value) {

        if (key == null) return;

        MDC.put(key, value);

    }

    public void removeMdc(String key) {
        MDC.remove(key);

    }

    public void error(String message, Throwable throwable) {

        logger.error(message, throwable);

    }

}
