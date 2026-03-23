package io.github.ulyssesrr.infratoolbox.logging.adapter;

import lombok.NonNull;

/**
 * A unified logging interface that abstracts various logging frameworks (SLF4J, Log4j, etc.)
 * and provides consistent logging capabilities across different logging implementations.
 *
 * <p>This adapter provides a simplified logging API focused on exception logging with MDC
 * (Mapped Diagnostic Context) support. It allows applications to log messages at different
 * severity levels along with associated exceptions.</p>
 *
 * <p>Implementations of this interface handle the specifics of different logging frameworks:</p>
 * <ul>
 *   <li>{@link io.github.ulyssesrr.infratoolbox.logging.adapter.Slf4jLoggerAdapter} - for SLF4J</li>
 *   <li>{@link io.github.ulyssesrr.infratoolbox.logging.adapter.Log4jLoggerAdapter} - for Log4j 1.x</li>
 *   <li>{@link io.github.ulyssesrr.infratoolbox.logging.adapter.NoopLoggerAdapter} - no-op implementation</li>
 * </ul>
 *
 * <p>Usage example:</p>
 * <pre>
 * LoggerAdapter logger = new Slf4jLoggerAdapter(MyClass.class);
 * logger.putMdc("requestId", request.getId());
 * logger.error("Failed to process request", exception);
 * logger.removeMdc("requestId");
 * </pre>
 *
 * @author Ulysses R. Ribeiro
 * @see io.github.ulyssesrr.infratoolbox.logging.MdcScope
 * @see io.github.ulyssesrr.infratoolbox.logging.MdcExceptionLogger
 */
public interface LoggerAdapter {

    /**
     * Puts a value into the MDC (Mapped Diagnostic Context) under the specified key.
     *
     * @param key the MDC key (cannot be null)
     * @param value the value to store (will be converted to string for SLF4j)
     */
    void putMdc(@NonNull String key, Object value);

    /**
     * Removes a value from the MDC (Mapped Diagnostic Context) for the specified key.
     *
     * @param key the MDC key to remove (cannot be null)
     */
    void removeMdc(@NonNull String key);

    /**
     * Logs an informational message with an associated exception.
     *
     * @param message the log message (may be null)
     * @param throwable the exception to log (may be null)
     */
    void info(String message, Throwable throwable);

    /**
     * Logs a warning message with an associated exception.
     *
     * @param message the log message (may be null)
     * @param throwable the exception to log (may be null)
     */
    void warn(String message, Throwable throwable);

    /**
     * Logs an error message with an associated exception.
     *
     * @param message the log message (may be null)
     * @param throwable the exception to log (may be null)
     */
    void error(String message, Throwable throwable);

}
