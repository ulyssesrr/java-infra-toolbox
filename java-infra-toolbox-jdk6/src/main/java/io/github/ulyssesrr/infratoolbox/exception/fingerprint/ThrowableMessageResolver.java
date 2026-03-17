package io.github.ulyssesrr.infratoolbox.exception.fingerprint;

public interface ThrowableMessageResolver {

    /**
     * Resolves the message of the given throwable.
     *
     * @param throwable
     * @return Exception message, cannot be null.
     */
    String getMessage(Throwable throwable);

}
