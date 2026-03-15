package io.github.ulyssesrr.infratoolbox.exception.fingerprint;

public interface StackFramePredicate {
    boolean test(StackTraceElement element);
}
