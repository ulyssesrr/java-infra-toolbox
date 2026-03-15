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

        assertThat(fingerprinter.fingerprintOf(exceptions[0])).isEqualTo(fingerprinter.fingerprintOf(exceptions[1]));
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

        assertThat(fingerprinter.fingerprintOf(exceptions[0])).isEqualTo(fingerprinter.fingerprintOf(exceptions[1]));
    }
}
