package io.github.ulyssesrr.infratoolbox.exception.fingerprint;

import java.util.concurrent.atomic.AtomicInteger;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public class TestDefaultExceptionFingerprinter implements WithAssertions {

    private static class ExceptionThrower {

        private static final AtomicInteger counter = new AtomicInteger();

        public void throwRuntimeExceptionWithDifferentMessage() {
            throw new RuntimeException("Message #" + counter.getAndIncrement());
        }

        public void throwRuntimeExceptionWithCause() {
            try {
                throwRuntimeExceptionWithDifferentMessage();
            } catch (Exception e) {
                throw new RuntimeException("Message #" + counter.getAndIncrement());
            }
        }

        public Throwable createChain(int causalChainLength) {
            Throwable cause = null;
            for (int i = 0; i < causalChainLength; i++) {
                String message = "Exception #" + i;
                if (i == 0) {
                    message += " (ROOT)";
                }
                RuntimeException e = new RuntimeException(message, cause);
                cause = e;
            }
            return new RuntimeException("Top", cause);
        }
    }

    @Test
    public void testConcurrentFingerprintingProducesConsistentResults() throws Exception {
        DefaultExceptionFingerprinter fingerprinter = DefaultExceptionFingerprinter.getInstance();

        Exception[] exceptions = new Exception[2];

        Thread thread1 = new Thread(() -> {
            try {
                ExceptionThrower et = new ExceptionThrower();
                et.throwRuntimeExceptionWithDifferentMessage();
            } catch (RuntimeException e) {
                e.printStackTrace();
                exceptions[0] = e;
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                ExceptionThrower et = new ExceptionThrower();
                et.throwRuntimeExceptionWithDifferentMessage();
            } catch (RuntimeException e) {
                e.printStackTrace();
                exceptions[1] = e;
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertThat(fingerprinter.fingerprint(exceptions[0])).isEqualTo(fingerprinter.fingerprint(exceptions[1]));
    }

    @Test
    public void testConcurrentFingerprintingProducesConsistentResultsWithCause() throws Exception {
        DefaultExceptionFingerprinter fingerprinter = DefaultExceptionFingerprinter.getInstance();

        Exception[] exceptions = new Exception[2];

        Thread thread1 = new Thread(() -> {
            try {
                ExceptionThrower et = new ExceptionThrower();
                et.throwRuntimeExceptionWithCause();
            } catch (RuntimeException e) {
                e.printStackTrace();
                exceptions[0] = e;
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                ExceptionThrower et = new ExceptionThrower();
                et.throwRuntimeExceptionWithCause();
            } catch (RuntimeException e) {
                e.printStackTrace();
                exceptions[1] = e;
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertThat(fingerprinter.fingerprint(exceptions[0])).isEqualTo(fingerprinter.fingerprint(exceptions[1]));
    }

    @Test
    public void testRootCauseIncluded() {
        Throwable e = new ExceptionThrower().createChain(2);


        DefaultExceptionFingerprinter fingerprinterWithRoot = DefaultExceptionFingerprinter.builder()
                .causalChainDepthLimit(1)
                .ensureRootCauseIncluded(true)
                .build();

        DefaultExceptionFingerprinter fingerprinterWithDepth = DefaultExceptionFingerprinter.builder()
                .causalChainDepthLimit(30)
                .ensureRootCauseIncluded(false)
                .build();

        String fpWithRoot = fingerprinterWithRoot.fingerprint(e);
        String fpWithDepth = fingerprinterWithDepth.fingerprint(e);
        assertThat(fpWithRoot).isEqualTo(fpWithDepth);

        DefaultExceptionFingerprinter fingerprinterWithBoth = DefaultExceptionFingerprinter.builder()
                .causalChainDepthLimit(2)
                .ensureRootCauseIncluded(true)
                .build();

        String fpWithBoth = fingerprinterWithBoth.fingerprint(e);
        assertThat(fpWithBoth).isEqualTo(fpWithRoot);
    }

    @Test
    public void testExceptionWithoutCause() {
        Throwable e = new ExceptionThrower().createChain(0);


        DefaultExceptionFingerprinter fingerprinterWithRoot = DefaultExceptionFingerprinter.builder()
                .causalChainDepthLimit(1)
                .ensureRootCauseIncluded(true)
                .build();

        DefaultExceptionFingerprinter fingerprinterWithDepth = DefaultExceptionFingerprinter.builder()
                .causalChainDepthLimit(2)
                .ensureRootCauseIncluded(false)
                .build();

        assertThat(fingerprinterWithRoot.fingerprint(e)).isEqualTo(fingerprinterWithDepth.fingerprint(e));
    }
}
