package io.github.ulyssesrr.infratoolbox.exception;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public class TestThrowableUtils implements WithAssertions {

    @Test
    public void testGetRootCauseWithNoCause() {
        RuntimeException exception = new RuntimeException("No cause");
        Throwable rootCause = ThrowableUtils.getRootCause(exception, 10);
        assertThat(rootCause).isSameAs(exception);
    }

    @Test
    public void testGetRootCauseWithSingleCause() {
        RuntimeException cause = new RuntimeException("Root cause");
        RuntimeException exception = new RuntimeException("Wrapper", cause);
        Throwable rootCause = ThrowableUtils.getRootCause(exception, 10);
        assertThat(rootCause).isSameAs(cause);
    }

    @Test
    public void testGetRootCauseWithMultipleCauses() {
        RuntimeException rootCause = new RuntimeException("Root cause");
        RuntimeException middleCause = new RuntimeException("Middle cause", rootCause);
        RuntimeException topException = new RuntimeException("Top exception", middleCause);
        Throwable result = ThrowableUtils.getRootCause(topException, 10);
        assertThat(result).isSameAs(rootCause);
    }

    @Test
    public void testGetRootCauseWithMaxDepthZero() {
        RuntimeException cause = new RuntimeException("Cause");
        RuntimeException exception = new RuntimeException("Exception", cause);

        Throwable result = ThrowableUtils.getRootCause(exception, 0);
        assertThat(result).isNull();
    }

    @Test
    public void testGetRootCauseExceedsMaxDepth() {
        RuntimeException rootCause = new RuntimeException("Root cause");
        RuntimeException middleCause1 = new RuntimeException("Cause 1", rootCause);
        RuntimeException middleCause2 = new RuntimeException("Cause 2", middleCause1);
        RuntimeException middleCause3 = new RuntimeException("Cause 3", middleCause2);
        RuntimeException topException = new RuntimeException("Top", middleCause3);

        // maxDepth=2 but there are 3 causes, so it should return null
        // because it couldn't find the actual root within the depth
        Throwable result = ThrowableUtils.getRootCause(topException, 2);
        assertThat(result).isNull();
    }

    @Test
    public void testGetRootCauseAtDepthLimit() {
        RuntimeException cause = new RuntimeException("Cause");
        RuntimeException exception = new RuntimeException("Exception", cause);

        // The cause has no further cause, so it should return the cause
        Throwable result = ThrowableUtils.getRootCause(exception, 1);
        assertThat(result).isSameAs(cause);
    }

    @Test
    public void testGetRootCauseWithChainedExceptions() {
        Exception level1 = new Exception("Level 1");
        Exception level2 = new Exception("Level 2", level1);
        Exception level3 = new Exception("Level 3", level2);
        Exception level4 = new Exception("Level 4", level3);

        Throwable rootCause = ThrowableUtils.getRootCause(level4, 5);
        assertThat(rootCause).isSameAs(level1);
    }

    @Test
    public void testGetRootCauseWithLargeMaxDepth() {
        RuntimeException rootCause = new RuntimeException("Root");
        RuntimeException e1 = new RuntimeException("E1", rootCause);
        RuntimeException e2 = new RuntimeException("E2", e1);

        Throwable result = ThrowableUtils.getRootCause(e2, 100);
        assertThat(result).isSameAs(rootCause);
    }

    @Test
    public void testGetRootCausePreservesOriginalException() {
        // When there is no cause chain, the original exception is returned
        IllegalArgumentException exception = new IllegalArgumentException("Test message");
        Throwable result = ThrowableUtils.getRootCause(exception, 5);
        assertThat(result).isSameAs(exception);
        assertThat(result.getMessage()).isEqualTo("Test message");
    }
}
