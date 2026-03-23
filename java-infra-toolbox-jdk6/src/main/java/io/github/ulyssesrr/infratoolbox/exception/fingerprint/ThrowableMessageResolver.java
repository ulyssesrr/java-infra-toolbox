package io.github.ulyssesrr.infratoolbox.exception.fingerprint;

import lombok.NonNull;

public interface ThrowableMessageResolver {

    /**
     * Resolves a human-readable message from the given {@link Throwable}.
     *
     * @param throwable the exception to extract the message from, must not be
     *                  {@code null}
     * @return the resolved exception message (never {@code null})
     */
    String getMessage(@NonNull Throwable throwable);

}
