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
    private final int causalChainDepthLimit = 128;

    @Builder.Default
    private final boolean ensureRootCauseIncluded = true;

    @Builder.Default
    private final int maxRootCauseSearchDepth = 128;

    @NonNull
    private final LoggerAdapter loggerAdapter = new AutoDetectLoggerAdapter(DefaultExceptionFingerprinter.class);

    public static DefaultExceptionFingerprinter getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final DefaultExceptionFingerprinter INSTANCE = DefaultExceptionFingerprinter.builder().build();
    }

    public String fingerprint(Throwable throwable) {
        StatefulHasher hasher = hasherFactory.create();
        try {
            exceptionHasher.hash(hasher, throwable, 0);

            Throwable current = throwable;
            for (int i = 0; i < causalChainDepthLimit && (current = current.getCause()) != null; i++) {
                int depth = current.getCause() == null ? -1 : i + 1;
                exceptionHasher.hash(hasher, current, depth);
            }

            boolean rootAlreadyIncluded = current.getCause() == null;
            if (ensureRootCauseIncluded && !rootAlreadyIncluded) {
                current = ThrowableUtils.getRootCause(current, maxRootCauseSearchDepth);
                exceptionHasher.hash(hasher, current, -1);
            }
        } catch (Throwable t) {
            if (loggerAdapter != null) {
                loggerAdapter.error("Error while fingerprinting exception", t);
            }
        }

        return hasher.hash();
    }

}
