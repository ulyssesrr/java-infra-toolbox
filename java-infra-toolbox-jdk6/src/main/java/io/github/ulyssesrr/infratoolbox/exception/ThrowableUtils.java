package io.github.ulyssesrr.infratoolbox.exception;

import lombok.NonNull;

public final class ThrowableUtils {

    private ThrowableUtils() {}

    /**
     * Returns the root cause of {@code throwable} with the specified maximum depth.
     *
     * @param throwable The throwable to get the root cause for.
     * @param maxDepth Maximum depth to search for the root cause.
     * @return the root cause of {@code throwable}, possibly itself, or {@code null} if it's not found within the specified depth.
     * @throws NullPointerException if {@code throwable} argument is null.
     */
    public static Throwable getRootCause(@NonNull Throwable throwable, int maxDepth) {
        Throwable root = throwable;

        Throwable cause;
        for (int i = 0; (cause = root.getCause()) != null && i < maxDepth; i++) {
            root = cause;
        }

        if (root.getCause() != null) {
            return null;
        }

        return root;
    }
}
