package io.github.ulyssesrr.infratoolbox.exception.fingerprint;

public interface ThrowablePredicate {

    boolean test(Throwable throwable);
}
