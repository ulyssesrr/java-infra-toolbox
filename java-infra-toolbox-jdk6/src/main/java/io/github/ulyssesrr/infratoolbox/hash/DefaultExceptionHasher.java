package io.github.ulyssesrr.infratoolbox.hash;

import io.github.ulyssesrr.infratoolbox.exception.fingerprint.ExceptionHasher;
import io.github.ulyssesrr.infratoolbox.exception.fingerprint.MessageNormalizer;
import io.github.ulyssesrr.infratoolbox.exception.fingerprint.StackFramePredicate;
import lombok.Builder;

@Builder(toBuilder = true)
public class DefaultExceptionHasher implements ExceptionHasher {

    @Builder.Default
    private final boolean includeExceptionClass = true;

    @Builder.Default
    private final boolean includeMethodName = true;

    @Builder.Default
    private final boolean includeMessage = false;

    @Builder.Default
    private final MessageNormalizer messageNormalizer = null;

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
                if (framePredicate != null && !framePredicate.test(e)) {
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

        if (includeMessage) {
            String msg = throwable.getMessage();
            if (msg != null) {
                if (messageNormalizer != null) {
                    hasher.putString(messageNormalizer.normalize(msg));
                } else {
                    hasher.putString(msg);
                }
            }
        }
    }
}
