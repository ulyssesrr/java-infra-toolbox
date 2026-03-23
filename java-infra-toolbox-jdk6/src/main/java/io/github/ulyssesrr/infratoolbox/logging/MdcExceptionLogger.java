package io.github.ulyssesrr.infratoolbox.logging;

import lombok.Builder;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NonNull;
import io.github.ulyssesrr.infratoolbox.exception.ThrowableUtils;
import io.github.ulyssesrr.infratoolbox.exception.fingerprint.DefaultExceptionFingerprinter;
import io.github.ulyssesrr.infratoolbox.exception.fingerprint.ExceptionFingerprinter;
import io.github.ulyssesrr.infratoolbox.logging.adapter.LoggerAdapter;
import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;
import io.github.ulyssesrr.infratoolbox.semver.encoder.SemverEncoder;
import io.github.ulyssesrr.infratoolbox.semver.encoder.SemverStringEncoder;

/**
 * An exception logger that enriches log events with MDC (Mapped Diagnostic Context) entries
 * containing exception fingerprints and version information.
 *
 * <p>This class provides a convenient way to log exceptions while automatically populating
 * MDC with useful diagnostic information. It supports:</p>
 *
 * <ul>
 *   <li>Exception fingerprinting - generates unique identifiers for exceptions to help
 *       identify recurring issues</li>
 *   <li>Root cause fingerprinting - fingerprints the root cause of exceptions</li>
 *   <li>Application Version tracking - stores both original and encoded version information</li>
 * </ul>
 *
 * <p>The MDC entries are automatically cleaned up after each log operation using
 * {@link MdcScope}, ensuring that log events don't leak context to unrelated operations.</p>
 *
 * <h2>Usage Example</h2>
 * <pre>
 * // Create the exception logger with default settings
 * MdcExceptionLogger logger = MdcExceptionLogger.builder()
 *     .adapter(new Slf4jLoggerAdapter(MyClass.class))
 *     .version(new SemanticVersion("1.2.3", 1, 2, 3, null))
 *     .build();
 *
 * // Log an exception with MDC enrichment
 * try {
 *     // some code that throws an exception
 * } catch (Exception e) {
 *     logger.error("Operation failed", e);
 * }
 * </pre>
 *
 * <p>The above code will populate MDC with entries like:</p>
 * <ul>
 *   <li>{@code exception_fingerprint} - fingerprint of the caught exception</li>
 *   <li>{@code root_exception_fingerprint} - fingerprint of the root cause</li>
 *   <li>{@code original_version} - "1.2.3"</li>
 *   <li>{@code encoded_version} - "0001.0002.0003" (or similar encoded form)</li>
 * </ul>
 *
 * <h2>Customizing MDC Keys</h2>
 * <pre>
 * MdcExceptionLogger logger = MdcExceptionLogger.builder()
 *     .adapter(adapter)
 *     .fingerprintMdcKey("error_signature")
 *     .rootCauseFingerprintMdcKey("root_error_signature")
 *     .originalVersionMdcKey("app_version")
 *     .encodedVersionMdcKey("app_version_encoded")
 *     .build();
 * </pre>
 *
 * <p>Set any key to {@code null} to disable that MDC entry.</p>
 *
 * @author Ulysses R. Ribeiro
 * @see MdcScope
 * @see LoggerAdapter
 * @see ExceptionFingerprinter
 * @see SemanticVersion
 */
@Getter
@Builder
public class MdcExceptionLogger {

    /**
     * The logger adapter used for logging operations.
     * This adapter handles the specifics of different logging frameworks (SLF4J, Log4j, etc.)
     */
    @NonNull
    private final LoggerAdapter adapter;

    /**
     * The fingerprinter used to generate exception fingerprints.
     * Set to {@code null} to disable all fingerprints vaues in MDC.
     * Defaults to {@link DefaultExceptionFingerprinter#getInstance()}.
     */
    @Builder.Default
    private final ExceptionFingerprinter fingerprinter = DefaultExceptionFingerprinter.getInstance();

    /**
     * The MDC key used to store the exception fingerprint.
     * Set to {@code null} to disable fingerprint storage.
     */
    @Builder.Default
    private final String fingerprintMdcKey = "exception_fingerprint";

    /**
     * The semantic version to include in MDC for version tracking.
     * Set to {@code null} to disable version storage.
     */
    @Builder.Default
    private final SemanticVersion version = null;

    /**
     * The MDC key used to store the original version string.
     * Only used when {@link #version} is not {@code null}.
     * Set to {@code null} to disable original version storage.
     */
    @Builder.Default
    private final String originalVersionMdcKey = "original_version";

    /**
     * The MDC key used to store the encoded version.
     * Only used when {@link #version} is not {@code null}.
     * Set to {@code null} to disable encoded version storage.
     */
    @Builder.Default
    private final String encodedVersionMdcKey = "encoded_version";

    /**
     * The encoder used to convert the semantic version to an encoded value.
     * Defaults to {@link SemverStringEncoder#getInstance()}.
     * Set to {@code null} to disable all version encoding vaues in MDC.
     */
    @Builder.Default
    private final SemverEncoder<?> versionEncoder = SemverStringEncoder.getInstance();

    /**
     * The MDC key used to store the root cause exception fingerprint.
     * Set to {@code null} to disable root cause fingerprint storage.
     */
    @Builder.Default
    private final String rootCauseFingerprintMdcKey = "root_exception_fingerprint";

    /**
     * The maximum depth to search for the root cause when calculating the root cause fingerprint.
     */
    @Builder.Default
    private final int maxRootCauseSearchDepth = 128;

    /**
     * Logs an informational message with an associated exception, enriching the MDC
     * with exception fingerprint and version information.
     *
     * <p>The following MDC entries are populated before logging:</p>
     * <ul>
     *   <li>{@code exception_fingerprint} - fingerprint of the thrown exception</li>
     *   <li>{@code root_exception_fingerprint} - fingerprint of the root cause</li>
     *   <li>{@code original_version} - original version string (if version is set)</li>
     *   <li>{@code encoded_version} - encoded version (if version is set)</li>
     * </ul>
     *
     * <p>All MDC entries are automatically cleaned up after logging.</p>
     *
     * @param message the log message (may be null)
     * @param throwable the exception to log (may be null)
     */
    public void info(String message, Throwable throwable) {
        @Cleanup
        MdcScope mdcScope = new MdcScope(adapter);

        fillMdc(throwable, mdcScope);

        adapter.info(message, throwable);
    }

    /**
     * Logs a warning message with an associated exception, enriching the MDC
     * with exception fingerprint and version information.
     *
     * <p>The following MDC entries are populated before logging:</p>
     * <ul>
     *   <li>{@code exception_fingerprint} - fingerprint of the thrown exception</li>
     *   <li>{@code root_exception_fingerprint} - fingerprint of the root cause</li>
     *   <li>{@code original_version} - original version string (if version is set)</li>
     *   <li>{@code encoded_version} - encoded version (if version is set)</li>
     * </ul>
     *
     * <p>All MDC entries are automatically cleaned up after logging.</p>
     *
     * @param message the log message (may be null)
     * @param throwable the exception to log (may be null)
     */
    public void warn(String message, Throwable throwable) {
        @Cleanup
        MdcScope mdcScope = new MdcScope(adapter);

        fillMdc(throwable, mdcScope);

        adapter.warn(message, throwable);
    }

    /**
     * Logs an error message with an associated exception, enriching the MDC
     * with exception fingerprint and version information.
     *
     * <p>The following MDC entries are populated before logging:</p>
     * <ul>
     *   <li>{@code exception_fingerprint} - fingerprint of the thrown exception</li>
     *   <li>{@code root_exception_fingerprint} - fingerprint of the root cause</li>
     *   <li>{@code original_version} - original version string (if version is set)</li>
     *   <li>{@code encoded_version} - encoded version (if version is set)</li>
     * </ul>
     *
     * <p>All MDC entries are automatically cleaned up after logging.</p>
     *
     * @param message the log message (may be null)
     * @param throwable the exception to log (may be null)
     */
    public void error(String message, Throwable throwable) {
        @Cleanup
        MdcScope mdcScope = new MdcScope(adapter);

        fillMdc(throwable, mdcScope);

        adapter.error(message, throwable);
    }

    /**
     * Populates the MDC with exception fingerprint and version information.
     *
     * <p>This method adds the following MDC entries (if configured):</p>
     * <ul>
     *   <li>Root cause fingerprint (from {@link #rootCauseFingerprintMdcKey})</li>
     *   <li>Exception fingerprint (from {@link #fingerprintMdcKey})</li>
     *   <li>Original version (from {@link #originalVersionMdcKey}, if version is set)</li>
     *   <li>Encoded version (from {@link #encodedVersionMdcKey}, if version is set)</li>
     * </ul>
     *
     * @param throwable the exception to generate fingerprints from (may be null)
     * @param mdcScope the MDC scope to populate (non-null)
     */
    private void fillMdc(Throwable throwable, @NonNull MdcScope mdcScope) {
        if (throwable != null && fingerprinter != null) {
            if (fingerprintMdcKey != null) {
                String fp = fingerprinter.fingerprint(throwable);
                mdcScope.put(fingerprintMdcKey, fp);
            }

            if (rootCauseFingerprintMdcKey != null) {
                Throwable rootCause = ThrowableUtils.getRootCause(throwable, maxRootCauseSearchDepth);
                if (rootCause != null) {
                    String rootFp = fingerprinter.fingerprint(rootCause);
                    mdcScope.put(rootCauseFingerprintMdcKey, rootFp);
                }
            }
        }

        if (version != null) {
            if (originalVersionMdcKey != null) {
                mdcScope.put(originalVersionMdcKey, version.getOriginalVersion());
            }

            if (versionEncoder != null && encodedVersionMdcKey != null) {
                mdcScope.put(encodedVersionMdcKey, versionEncoder.toValue(version));
            }
        }
    }

}
