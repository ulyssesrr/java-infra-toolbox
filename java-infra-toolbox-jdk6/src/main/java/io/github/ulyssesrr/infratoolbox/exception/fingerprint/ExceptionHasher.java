package io.github.ulyssesrr.infratoolbox.exception.fingerprint;

import io.github.ulyssesrr.infratoolbox.hash.StatefulHasher;

public interface ExceptionHasher {
    
    void hash(StatefulHasher hasher, Throwable throwable, int depth);
}
