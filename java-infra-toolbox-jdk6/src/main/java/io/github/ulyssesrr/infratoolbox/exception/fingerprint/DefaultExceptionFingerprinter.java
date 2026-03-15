package io.github.ulyssesrr.infratoolbox.exception.fingerprint;

import io.github.ulyssesrr.infratoolbox.exception.ThrowableUtils;
import io.github.ulyssesrr.infratoolbox.hash.DefaultExceptionHasher;
import io.github.ulyssesrr.infratoolbox.hash.JdkHasher32;
import io.github.ulyssesrr.infratoolbox.hash.StatefulHasher;
import io.github.ulyssesrr.infratoolbox.hash.StatefulHasherFactory;
import io.github.ulyssesrr.infratoolbox.logging.adapter.AutoDetectLoggerAdapter;
import io.github.ulyssesrr.infratoolbox.logging.adapter.LoggerAdapter;
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
    private final int includeCausalChainDepth = 128;

    @Builder.Default
    private final boolean includeRootCause = true;

    @Builder.Default
    private final int maxRootCauseSearchDepth = 128;

    @Builder.Default
    private final LoggerAdapter loggerAdapter = new AutoDetectLoggerAdapter();

    public static DefaultExceptionFingerprinter getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final DefaultExceptionFingerprinter INSTANCE = DefaultExceptionFingerprinter.builder().build();
    }

    public String fingerprintOf(Throwable throwable) {
        StatefulHasher hasher = hasherFactory.create();
        try {
            exceptionHasher.hash(hasher, throwable, 0);

            Throwable target = throwable;
            for (int i = 0; (target = target.getCause()) != null && i < includeCausalChainDepth; i++) {
                int depth = target.getCause() == null ? -1 : i + 1;
                exceptionHasher.hash(hasher, throwable, depth);
            }

            boolean rootAlreadyIncluded = target.getCause() == null;
            if (includeRootCause && rootAlreadyIncluded) {
                target = ThrowableUtils.getRootCause(target, maxRootCauseSearchDepth);
                exceptionHasher.hash(hasher, target, -1);
            }
        } catch (Throwable t) {
            if (loggerAdapter != null) {
                loggerAdapter.error("Error while fingerprinting exception", t);
            }
        }

        return hasher.hash();
    }

}
