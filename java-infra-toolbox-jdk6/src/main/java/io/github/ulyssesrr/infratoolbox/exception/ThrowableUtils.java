package io.github.ulyssesrr.infratoolbox.exception;

import lombok.NonNull;

public final class ThrowableUtils {
    
    private ThrowableUtils() {}

    /**
     * Returns the root cause of {@code throwable} with the specified maximum depth.
     * 
     * @param throwable The throwable to get the root cause for.
     * @param maxDepth Maximum depth to search for the root cause.
     * @return the root cause of {@code throwable}, or {@code null} if there is no root cause or it is not found within the specified depth.
     * @throws NullPointerException if {@code throwable} argument is null.
     */
    public static Throwable getRootCause(@NonNull Throwable throwable, int maxDepth) {
        Throwable cause;
        for (int i = 0; (cause = throwable.getCause()) != null && i < maxDepth; i++) {
            throwable = cause;
        }

        if (throwable.getCause() != null) {
            return null;
        }
        return throwable;
    }
}
