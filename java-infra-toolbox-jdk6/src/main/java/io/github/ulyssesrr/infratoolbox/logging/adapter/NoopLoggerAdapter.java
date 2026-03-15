package io.github.ulyssesrr.infratoolbox.logging.adapter;

public class NoopLoggerAdapter implements LoggerAdapter {

    public void putMdc(String key, Object value) {}

    public void removeMdc(String key) {}

    public void error(String message, Throwable throwable) {

        System.err.println("No logger implementation available");
        if (throwable != null) {
            throwable.printStackTrace();
        }

    }

}
