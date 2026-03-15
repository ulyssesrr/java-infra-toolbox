package io.github.ulyssesrr.infratoolbox.logging.adapter;

public interface LoggerAdapter {

    void putMdc(String key, Object value);

    void removeMdc(String key);

    void error(String message, Throwable throwable);

}
