package io.github.ulyssesrr.infratoolbox.logging.adapter;

public class AutoDetectLoggerAdapter implements LoggerAdapter {

    private final LoggerAdapter delegate;

    public AutoDetectLoggerAdapter(String loggerName) {
        LoggerAdapter found;

        try {
            Class.forName("org.slf4j.Logger");
            found = new Slf4jLoggerAdapter(loggerName);
        } catch (Throwable e1) {
            try {
                Class.forName("org.apache.log4j.Logger");
                found = new Log4jLoggerAdapter(loggerName);
            } catch (Throwable e2) {
                found = new NoopLoggerAdapter();
            }
        }

        this.delegate = found;
    }

    public AutoDetectLoggerAdapter(Class<?> clazz) {
        LoggerAdapter found;

        try {
            Class.forName("org.slf4j.Logger");
            found = new Slf4jLoggerAdapter(clazz);
        } catch (Throwable e1) {
            try {
                Class.forName("org.apache.log4j.Logger");
                found = new Log4jLoggerAdapter(clazz);
            } catch (Throwable e2) {
                found = new NoopLoggerAdapter();
            }
        }

        this.delegate = found;
    }

    public void putMdc(String key, Object value) {
        delegate.putMdc(key, value);
    }

    public void removeMdc(String key) {
        delegate.removeMdc(key);
    }

    public void info(String message, Throwable throwable) {
        delegate.info(message, throwable);
    }

    public void warn(String message, Throwable throwable) {
        delegate.warn(message, throwable);
    }

    public void error(String message, Throwable throwable) {
        delegate.error(message, throwable);
    }

}
