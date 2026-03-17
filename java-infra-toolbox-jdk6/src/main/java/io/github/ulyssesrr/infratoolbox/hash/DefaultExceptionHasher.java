package io.github.ulyssesrr.infratoolbox.hash;

import io.github.ulyssesrr.infratoolbox.exception.fingerprint.ExceptionHasher;
import io.github.ulyssesrr.infratoolbox.exception.fingerprint.ThrowableMessageResolver;
import io.github.ulyssesrr.infratoolbox.exception.fingerprint.StackFramePredicate;
import io.github.ulyssesrr.infratoolbox.exception.fingerprint.ThrowablePredicate;
import lombok.Builder;

@Builder(toBuilder = true)
public class DefaultExceptionHasher implements ExceptionHasher {

    @Builder.Default
    private final boolean includeExceptionClass = true;

    @Builder.Default
    private final boolean includeMethodName = true;

    @Builder.Default
    private final ThrowablePredicate messagePredicate = null;

    @Builder.Default
    private final ThrowableMessageResolver messageResolver = null;

    @Builder.Default
    private final StackFramePredicate framePredicate = null;

    @Builder.Default
    private final int stackFrames = 1;

    @Builder.Default
    private final boolean includeStackFrameMethodName = true;

    @Builder.Default
    private final boolean includeStackFrameFileName = true;

    @Builder.Default
    private final boolean includeStackFrameLineNumber = true;


    public static DefaultExceptionHasher getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {

        private static final DefaultExceptionHasher INSTANCE = DefaultExceptionHasher.builder().build();

    }

    @Override
    public void hash(StatefulHasher hasher, Throwable throwable, int depth) {
        if (includeExceptionClass) {
            hasher.putString(throwable.getClass().getName());
        }

        StackTraceElement[] stack = throwable.getStackTrace();
        if (stack != null) {
            int count = 0;

            for (int i = 0; i < stack.length && count < stackFrames; i++) {
                StackTraceElement e = stack[i];
                if (framePredicate != null && !framePredicate.test(e, throwable)) {
                    continue;
                }

                if (includeStackFrameMethodName) {
                    hasher.putString(e.getMethodName());
                }

                if (includeStackFrameFileName) {
                    hasher.putString(e.getFileName());
                }

                if (includeStackFrameLineNumber) {
                    hasher.putInt(e.getLineNumber());
                }

                count++;
            }
        }

        if (messagePredicate != null) {
            if (messagePredicate.test(throwable)) {
                if (messageResolver != null) {
                    hasher.putString(messageResolver.getMessage(throwable));
                } else {
                    String msg = throwable.getMessage() != null ? throwable.getMessage() : "";
                    hasher.putString(msg);
                }
            }
        }
    }
}
