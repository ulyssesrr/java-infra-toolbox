package io.github.ulyssesrr.infratoolbox.exception.fingerprint;

/**
 * An interface for generating unique fingerprints from {@link Throwable} instances.
 *
 * <p>An exception fingerprint is a string representation that uniquely identifies
 * the "signature" of an exception, allowing semantically similar exceptions to be
 * grouped together. This is useful for logging, monitoring, and deduplication purposes.
 *
 * <p>The fingerprint is typically computed by hashing the exception's class name,
 * relevant stack trace elements, and other structural properties while ignoring
 * dynamic content like parametrized error messages.
 *
 * <p>Implementations should ensure that:
 * <ul>
 *   <li>Exceptions of the same type thrown from the same location produce identical fingerprints</li>
 *   <li>The fingerprint is consistent across multiple invocations with the same exception</li>
 *   <li>The operation is thread-safe</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * ExceptionFingerprinter fingerprinter = DefaultExceptionFingerprinter.getInstance();
 *
 * try {
 *     // Some code that throws an exception
 * } catch (Exception e) {
 *     String fingerprint = fingerprinter.fingerprint(e);
 *     // Use fingerprint for logging or deduplication
 * }
 * }</pre>
 *
 * @author Ulysses R. Ribeiro
 * @see DefaultExceptionFingerprinter
 * @see ExceptionHasher
 */
public interface ExceptionFingerprinter {

    /**
     * Generates a fingerprint for the given throwable.
     *
     * <p>The fingerprint is a string representation that uniquely identifies
     * the exception's "signature", allowing exceptions with the same structural
     * characteristics to produce identical fingerprints.
     *
     * <p>The fingerprint is typically computed by hashing:
     * <ul>
     *   <li>The exception's class name</li>
     *   <li>The exception's stack trace (filtered according to implementation)</li>
     *   <li>The causal chain (causes of the exception)</li>
     * </ul>
     *
     * <p>Dynamic content such as specific error messages is typically excluded
     * from the fingerprint computation to allow exceptions thrown from the same
     * code location to produce identical fingerprints regardless of the message content.
     *
     * @param throwable The throwable to generate a fingerprint for. Must not be {@code null}.
     * @return A non-null string representation that serves as a unique identifier for the exception's signature.
     */
    String fingerprint(Throwable throwable);

}
