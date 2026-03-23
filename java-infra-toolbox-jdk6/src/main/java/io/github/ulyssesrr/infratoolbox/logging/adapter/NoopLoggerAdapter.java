package io.github.ulyssesrr.infratoolbox.logging.adapter;

public class NoopLoggerAdapter implements LoggerAdapter {

    public void putMdc(String key, Object value) {}

    public void removeMdc(String key) {}

    @Override
    public void info(String message, Throwable throwable) {}

    @Override
    public void warn(String message, Throwable throwable) {}

    @Override
    public void error(String message, Throwable throwable) {}

}
