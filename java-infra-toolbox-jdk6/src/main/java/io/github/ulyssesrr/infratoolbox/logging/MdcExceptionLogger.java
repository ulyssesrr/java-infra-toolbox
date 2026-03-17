package io.github.ulyssesrr.infratoolbox.logging;

import lombok.Builder;
import lombok.Cleanup;
import lombok.NonNull;
import io.github.ulyssesrr.infratoolbox.exception.ThrowableUtils;
import io.github.ulyssesrr.infratoolbox.exception.fingerprint.DefaultExceptionFingerprinter;
import io.github.ulyssesrr.infratoolbox.exception.fingerprint.ExceptionFingerprinter;
import io.github.ulyssesrr.infratoolbox.logging.adapter.AutoDetectLoggerAdapter;
import io.github.ulyssesrr.infratoolbox.logging.adapter.LoggerAdapter;
import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;
import io.github.ulyssesrr.infratoolbox.semver.encoder.SemverEncoder;
import io.github.ulyssesrr.infratoolbox.semver.encoder.SemverStringEncoder;

@Builder
public class MdcExceptionLogger {

    @NonNull
    @Builder.Default
    private final LoggerAdapter adapter = new AutoDetectLoggerAdapter();

    @NonNull
    @Builder.Default
    private final ExceptionFingerprinter fingerprinter = DefaultExceptionFingerprinter.getInstance();

    @Builder.Default
    private final String fingerprintMdcKey = null;

    @Builder.Default
    private final SemanticVersion version = null;

    @Builder.Default
    private final String originalVersionMdcKey = null;

    @Builder.Default
    private final String encodedVersionMdcKey = null;

    @NonNull
    @Builder.Default
    private final SemverEncoder<?> versionEncoder = SemverStringEncoder.getInstance();

    @Builder.Default
    private final String rootCauseFingerprintMdcKey = null;

    @Builder.Default
    private final int maxRootCauseSearchDepth = 128;

    public static MdcExceptionLogger getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final MdcExceptionLogger INSTANCE = MdcExceptionLogger.builder().build();
    }

    public void log(String message, Throwable throwable) {
        @Cleanup
        MdcScope mdcScope = new MdcScope(adapter);

        if (rootCauseFingerprintMdcKey != null && fingerprinter != null) {
            Throwable rootCause = ThrowableUtils.getRootCause(throwable, maxRootCauseSearchDepth);
            if (rootCause != null) {
                String rootFp = fingerprinter.fingerprintOf(rootCause);
                mdcScope.put(rootCauseFingerprintMdcKey, rootFp);
            }
        }

        if (throwable != null && fingerprinter != null && fingerprintMdcKey != null) {
            String fp = fingerprinter.fingerprintOf(throwable);
            mdcScope.put(fingerprintMdcKey, fp);
        }

        if (version != null) {
            if (originalVersionMdcKey != null) {
                mdcScope.put(originalVersionMdcKey, version.getOriginalVersion());
            }

            if (versionEncoder != null && encodedVersionMdcKey != null) {
                mdcScope.put(encodedVersionMdcKey, versionEncoder.toValue(version));
            }
        }


    }

}
