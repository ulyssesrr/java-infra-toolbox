package io.github.ulyssesrr.infratoolbox.logging;

import lombok.Builder;
import lombok.Cleanup;
import lombok.NonNull;
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
    private final String fingerprintKey = null;

    @Builder.Default
    private final SemanticVersion version = null;

    @Builder.Default
    private final String encodedVersionKey = null;

    @NonNull
    @Builder.Default
    private final SemverEncoder<?> versionEncoder = SemverStringEncoder.getInstance();

    public static MdcExceptionLogger getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final MdcExceptionLogger INSTANCE = MdcExceptionLogger.builder().build();
    }

    public void log(String message, Throwable throwable) {
        @Cleanup
        MdcScope mdcScope = new MdcScope(adapter);

        if (throwable != null && fingerprinter != null && fingerprintKey != null) {
            String fp = fingerprinter.fingerprintOf(throwable);
            mdcScope.put(fingerprintKey, fp);
        }

        if (version != null) {
            if (versionEncoder != null && encodedVersionKey != null) {
                mdcScope.put(encodedVersionKey, versionEncoder.toValue(version));
            }
        }

    }

}
