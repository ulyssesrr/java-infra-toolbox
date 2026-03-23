package io.github.ulyssesrr.infratoolbox.logging;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.ulyssesrr.infratoolbox.exception.fingerprint.ExceptionFingerprinter;
import io.github.ulyssesrr.infratoolbox.logging.adapter.LoggerAdapter;
import io.github.ulyssesrr.infratoolbox.semver.JdkSemanticVersionParser;
import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;
import io.github.ulyssesrr.infratoolbox.semver.encoder.SemverEncoder;

/**
 * @author Ulysses R. Ribeiro
 */
@ExtendWith(MockitoExtension.class)
public class TestMdcExceptionLogger implements WithAssertions {

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private LoggerAdapter adapter;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ExceptionFingerprinter fingerprinter;

    private SemanticVersion version = JdkSemanticVersionParser.INSTANCE.parse("1.0.0");

    @SuppressWarnings("rawtypes")
    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private SemverEncoder versionEncoder;

    private MdcExceptionLogger mdcExceptionLogger;



    @Test
    void testParameters() {
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .build();

        mdcExceptionLogger.info(null, null);
        mdcExceptionLogger.warn(null, null);
        mdcExceptionLogger.error(null, null);

        final String message = "message!";
        mdcExceptionLogger.info(message, null);
        mdcExceptionLogger.warn(message, null);
        mdcExceptionLogger.error(message, null);

        final Throwable throwable = new NullPointerException();
        mdcExceptionLogger.info(message, throwable);
        mdcExceptionLogger.warn(message, throwable);
        mdcExceptionLogger.error(message, throwable);
    }

    @Test
    void testNullFingerprinterAndNullVersion() {
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .fingerprinter(null)
                .version(null)
                .build();

        final String message = "message";
        mdcExceptionLogger.error(message, null);
        verify(adapter).error(message, null);
        verifyNoMoreInteractions(adapter);
    }

    @Test
    void testNullExceptionFingerprinter() {
        final String originalVersionMdcKey = "originalVersionMdcKey";
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .fingerprinter(null)
                .originalVersionMdcKey(originalVersionMdcKey)
                .version(version)
                .build();

        final String message = "message";
        mdcExceptionLogger.error(message, null);
        verify(adapter).error(message, null);
        verify(adapter).putMdc(originalVersionMdcKey, version.getOriginalVersion());
    }

    @Test
    void testNonNullExceptionFingerprinter() {
        final String fingerprintMdcKey = "error_fingerprint";
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .fingerprinter(fingerprinter)
                .fingerprintMdcKey(fingerprintMdcKey)
                .rootCauseFingerprintMdcKey(null)
                .build();

        final Exception exception = new RuntimeException("test error");
        final String expectedFingerprint = "fingerprint123";
        when(fingerprinter.fingerprint(exception)).thenReturn(expectedFingerprint);

        final String message = "message";
        mdcExceptionLogger.error(message, exception);
        verify(adapter).error(message, exception);
        verify(fingerprinter).fingerprint(exception);
        verify(adapter).putMdc(fingerprintMdcKey, expectedFingerprint);
        verify(adapter).removeMdc(fingerprintMdcKey);
        verifyNoMoreInteractions(adapter, fingerprinter);
    }

    @Test
    void testNonNullSemanticVersion() {
        final String originalVersionMdcKey = "originalVersionMdcKey";
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .version(version)
                .versionEncoder(null)
                .originalVersionMdcKey(originalVersionMdcKey)
                .build();

        final String message = "message";
        mdcExceptionLogger.error(message, null);
        verify(adapter).error(message, null);
        verify(adapter).putMdc(originalVersionMdcKey, version.getOriginalVersion());
        verify(adapter).removeMdc(originalVersionMdcKey);
        verifyNoMoreInteractions(adapter);
    }

    @Test
    void testNullSemanticVersion() {
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .version(null)
                .build();

        final String message = "message";
        mdcExceptionLogger.error(message, null);
        verify(adapter).error(message, null);
        verifyNoMoreInteractions(adapter);
    }

    @Test
    void testNonNullSemverEncoder() {
        final String encodedVersionMdcKey = "encoded_ver";
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .version(version)
                .versionEncoder(versionEncoder)
                .encodedVersionMdcKey(encodedVersionMdcKey)
                .build();

        final String encodedValue = "0001.0000.0000";
        when(versionEncoder.toValue(version)).thenReturn(encodedValue);

        final String message = "message";
        mdcExceptionLogger.error(message, null);
        verify(adapter).error(message, null);
        verify(adapter).putMdc("original_version", version.getOriginalVersion());
        verify(versionEncoder).toValue(version);
        verify(adapter).putMdc(encodedVersionMdcKey, encodedValue);
    }

    @Test
    void testNullFingerprintMdcKey() {
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .fingerprinter(fingerprinter)
                .fingerprintMdcKey(null)
                .rootCauseFingerprintMdcKey(null)
                .build();

        final Exception exception = new RuntimeException("test error");

        final String message = "message";
        mdcExceptionLogger.error(message, exception);
        verify(adapter).error(message, exception);
        verifyNoMoreInteractions(adapter, fingerprinter);
    }

    @Test
    void testCustomFingerprintMdcKey() {
        final String customFingerprintMdcKey = "custom_fingerprint";
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .fingerprinter(fingerprinter)
                .fingerprintMdcKey(customFingerprintMdcKey)
                .rootCauseFingerprintMdcKey(null)
                .build();

        final Exception exception = new RuntimeException("test error");
        final String expectedFingerprint = "fingerprint123";
        when(fingerprinter.fingerprint(exception)).thenReturn(expectedFingerprint);

        final String message = "message";
        mdcExceptionLogger.error(message, exception);
        verify(adapter).error(message, exception);
        verify(fingerprinter).fingerprint(exception);
        verify(adapter).putMdc(customFingerprintMdcKey, expectedFingerprint);
        verify(adapter).removeMdc(customFingerprintMdcKey);
        verifyNoMoreInteractions(adapter, fingerprinter);
    }

    @Test
    void testNullOriginalVersionMdcKey() {
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .version(version)
                .originalVersionMdcKey(null)
                .encodedVersionMdcKey(null)
                .build();

        final String message = "message";
        mdcExceptionLogger.error(message, null);
        verify(adapter).error(message, null);
        verifyNoMoreInteractions(adapter);
    }

    @Test
    void testCustomOriginalVersionMdcKey() {
        final String customOriginalVersionMdcKey = "custom_original_version";
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .version(version)
                .originalVersionMdcKey(customOriginalVersionMdcKey)
                .encodedVersionMdcKey(null)
                .build();

        final String message = "message";
        mdcExceptionLogger.error(message, null);
        verify(adapter).error(message, null);
        verify(adapter).putMdc(customOriginalVersionMdcKey, version.getOriginalVersion());
        verify(adapter).removeMdc(customOriginalVersionMdcKey);
        verifyNoMoreInteractions(adapter);
    }

    @Test
    void testCustomEncodedVersionMdcKey() {
        final String customEncodedVersionMdcKey = "custom_encoded_version";
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .version(version)
                .versionEncoder(versionEncoder)
                .encodedVersionMdcKey(customEncodedVersionMdcKey)
                .build();

        final String encodedValue = "0001.0000.0000";
        when(versionEncoder.toValue(version)).thenReturn(encodedValue);

        final String message = "message";
        mdcExceptionLogger.error(message, null);
        verify(adapter).error(message, null);
        verify(adapter).putMdc(mdcExceptionLogger.getOriginalVersionMdcKey(), version.getOriginalVersion());
        verify(versionEncoder).toValue(version);
        verify(adapter).putMdc(customEncodedVersionMdcKey, encodedValue);
    }

    @Test
    void testNullRootCauseFingerprintMdcKey() {
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .fingerprinter(fingerprinter)
                .rootCauseFingerprintMdcKey(null)
                .build();

        final Exception exception = new RuntimeException("test error");
        final String expectedFingerprint = "fingerprint123";
        when(fingerprinter.fingerprint(exception)).thenReturn(expectedFingerprint);

        final String message = "message";
        mdcExceptionLogger.error(message, exception);
        verify(adapter).error(message, exception);
        verify(fingerprinter).fingerprint(exception);
        verify(adapter).putMdc(mdcExceptionLogger.getFingerprintMdcKey(), expectedFingerprint);
        verify(adapter).removeMdc(mdcExceptionLogger.getFingerprintMdcKey());
        verifyNoMoreInteractions(adapter, fingerprinter);
    }

    @Test
    void testCustomRootCauseFingerprintMdcKey() {
        final String customRootCauseFingerprintMdcKey = "custom_root_cause_fingerprint";
        mdcExceptionLogger = MdcExceptionLogger.builder()
                .adapter(adapter)
                .fingerprinter(fingerprinter)
                .rootCauseFingerprintMdcKey(customRootCauseFingerprintMdcKey)
                .build();


        final Exception rootException = new RuntimeException("root error");
        final Exception exception = new RuntimeException("test error", rootException);
        final String expectedFingerprint = "fingerprint123";
        final String rootCauseFingerprint = "root_cause_fingerprint";
        when(fingerprinter.fingerprint(exception)).thenReturn(expectedFingerprint);
        when(fingerprinter.fingerprint(rootException)).thenReturn(rootCauseFingerprint);

        final String message = "message";
        mdcExceptionLogger.error(message, exception);
        verify(adapter).error(message, exception);
        verify(fingerprinter).fingerprint(exception);
        verify(fingerprinter).fingerprint(rootException);
        verify(adapter).putMdc(mdcExceptionLogger.getFingerprintMdcKey(), expectedFingerprint);
        verify(adapter).putMdc(customRootCauseFingerprintMdcKey, rootCauseFingerprint);
        verify(adapter).removeMdc(mdcExceptionLogger.getFingerprintMdcKey());
        verify(adapter).removeMdc(customRootCauseFingerprintMdcKey);
        verifyNoMoreInteractions(adapter, fingerprinter);
    }

}
