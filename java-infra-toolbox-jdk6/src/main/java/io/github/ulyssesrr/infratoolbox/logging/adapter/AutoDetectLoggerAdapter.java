package io.github.ulyssesrr.infratoolbox.logging.adapter;

public class AutoDetectLoggerAdapter implements LoggerAdapter {

    private final LoggerAdapter delegate;

    public AutoDetectLoggerAdapter() {

        LoggerAdapter found;

        try {
            Class.forName("org.slf4j.Logger");
            found = new Slf4jLoggerAdapter();
        } catch (Throwable e1) {

            try {
                Class.forName("org.apache.log4j.Logger");
                found = new Reload4jLoggerAdapter();
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

    public void error(String message, Throwable throwable) {
        delegate.error(message, throwable);
    }

}
