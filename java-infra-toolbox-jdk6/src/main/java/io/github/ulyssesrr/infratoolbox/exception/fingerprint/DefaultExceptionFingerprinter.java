package io.github.ulyssesrr.infratoolbox.exception.fingerprint;

import io.github.ulyssesrr.infratoolbox.exception.ThrowableUtils;
import io.github.ulyssesrr.infratoolbox.hash.DefaultExceptionHasher;
import io.github.ulyssesrr.infratoolbox.hash.JdkHasher32;
import io.github.ulyssesrr.infratoolbox.hash.StatefulHasher;
import io.github.ulyssesrr.infratoolbox.hash.StatefulHasherFactory;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder(toBuilder = true)
@RequiredArgsConstructor
public class DefaultExceptionFingerprinter implements ExceptionFingerprinter {


    @Builder.Default
    @NonNull
    private final StatefulHasherFactory hasherFactory = JdkHasher32.getDefaultFactory();

    @Builder.Default
    @NonNull
    private final ExceptionHasher exceptionHasher = DefaultExceptionHasher.getInstance();

    @Builder.Default
    private final int causalChainDepthLimit = 128;

    @Builder.Default
    private final boolean ensureRootCauseIncluded = true;

    @Builder.Default
    private final int maxRootCauseSearchDepth = 128;

    public static DefaultExceptionFingerprinter getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final DefaultExceptionFingerprinter INSTANCE = DefaultExceptionFingerprinter.builder().build();
    }

    public String fingerprint(@NonNull Throwable throwable) {
        StatefulHasher hasher = hasherFactory.create();

        exceptionHasher.hash(hasher, throwable, 0);

        Throwable current = throwable;
        for (int i = 0; i < causalChainDepthLimit && (current = current.getCause()) != null; i++) {
            int depth = current.getCause() == null ? -1 : i + 1;
            exceptionHasher.hash(hasher, current, depth);
        }

        // current == null at this point means that the causal chain is shorter than causalChainDepthLimit
        // current.getCause() == null means causalChainDepthLimit was exactly equal to the causal chain length
        boolean rootAlreadyIncluded = current == null || current.getCause() == null;
        if (ensureRootCauseIncluded && !rootAlreadyIncluded) {
            current = ThrowableUtils.getRootCause(current, maxRootCauseSearchDepth);
            exceptionHasher.hash(hasher, current, -1);
        }

        return hasher.hash();
    }

}
