package io.github.ulyssesrr.infratoolbox.logging;

import java.io.Closeable;
import java.util.LinkedHashSet;
import java.util.Set;

import io.github.ulyssesrr.infratoolbox.logging.adapter.LoggerAdapter;
import lombok.Cleanup;

/**
 * A scope for managing MDC (Mapped Diagnostic Context) entries with automatic cleanup.
 *
 * <p>This class provides a convenient way to manage MDC key-value pairs that are
 * automatically cleaned up when the scope is closed. It implements {@link Closeable}
 * making it suitable for use with try-with-resources (Java 7+) or with Lombok's
 * {@code @Cleanup} annotation (Java 6).</p>
 *
 * <p>MDC (Mapped Diagnostic Context) is a map where application code can store values
 * that will be logged along with each log event. This is useful for adding contextual
 * information to logs, such as request IDs, user IDs, or transaction IDs.</p>
 *
 * <h2>Usage with try-with-resources (Java 8+)</h2>
 * <pre>
 * LoggerAdapter adapter = new Slf4jLoggerAdapter(MyClass.class);
 * try (MdcScope scope = new MdcScope(adapter)) {
 *     scope.put("requestId", request.getId());
 *     scope.put("userId", user.getId());
 *     adapter.error("Failed to process request", exception);
 * } // MDC keys are automatically removed here
 * </pre>
 *
 * <h2>Usage with Lombok {@link Cleanup} (Java 6)</h2>
 * <pre>
 * LoggerAdapter adapter = new Slf4jLoggerAdapter(MyClass.class);
 * {@literal @}Cleanup
 * MdcScope scope = new MdcScope(adapter);
 * scope.put("requestId", request.getId());
 * scope.put("userId", user.getId());
 * adapter.error("Failed to process request", exception);
 * // MDC keys are automatically removed when scope goes out of scope
 * </pre>
 *
 * <p>This class is typically used in conjunction with {@link MdcExceptionLogger} which
 * automatically manages MDC scope for exception logging with fingerprinting and
 * version tracking.</p>
 *
 * @author Ulysses R. Ribeiro
 * @see LoggerAdapter
 * @see MdcExceptionLogger
 */
public class MdcScope implements Closeable {

    private final LoggerAdapter adapter;

    private final Set<String> keys = new LinkedHashSet<String>();

    /**
     * Creates a new MDC scope with the specified logger adapter.
     *
     * @param adapter the logger adapter to use for MDC operations (cannot be null)
     */
    public MdcScope(LoggerAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Puts a value into the MDC (Mapped Diagnostic Context) under the specified key.
     *
     * <p>The key-value pair will be automatically cleaned up when this scope is closed.</p>
     *
     * @param key the MDC key (cannot be null)
     * @param value the value to store (will be converted to string for SLF4j)
     */
    public void put(String key, Object value) {
        adapter.putMdc(key, value);
        keys.add(key);
    }

    /**
     * Removes all MDC entries that were added via {@link #put(String, Object)} calls
     * on this scope.
     *
     * <p>This method should be called when the scope is no longer needed, typically
     * via try-with-resources or Lombok's {@code @Cleanup} annotation.</p>
     *
     * <p>Note: This method is idempotent - calling it multiple times has no additional effect.</p>
     */
    public void close() {
        for (String k : keys) {
            adapter.removeMdc(k);
        }
    }

}
