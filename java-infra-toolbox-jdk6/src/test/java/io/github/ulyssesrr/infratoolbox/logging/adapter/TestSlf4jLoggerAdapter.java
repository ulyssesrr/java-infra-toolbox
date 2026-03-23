package io.github.ulyssesrr.infratoolbox.logging.adapter;

import static org.mockito.Mockito.verify;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

@ExtendWith(MockitoExtension.class)
class TestSlf4jLoggerAdapter implements WithAssertions {

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private Logger logger;

    private Slf4jLoggerAdapter loggerAdapter;

    @BeforeEach
    void setUp() {
        loggerAdapter = new Slf4jLoggerAdapter(logger);
    }

    @Test
    void testInfoDelegatesToLogger() {
        String message = "Test info message";
        Throwable throwable = new RuntimeException("Test exception");

        loggerAdapter.info(message, throwable);

        verify(logger).info(message, throwable);
    }

    @Test
    void testWarnDelegatesToLogger() {
        String message = "Test warn message";
        Throwable throwable = new RuntimeException("Test exception");

        loggerAdapter.warn(message, throwable);

        verify(logger).warn(message, throwable);
    }

    @Test
    void testErrorDelegatesToLogger() {
        String message = "Test error message";
        Throwable throwable = new RuntimeException("Test exception");

        loggerAdapter.error(message, throwable);

        verify(logger).error(message, throwable);
    }

    @Test
    void testInfoWithNullThrowable() {
        String message = "Test info message";

        loggerAdapter.info(message, (Throwable) null);

        verify(logger).info(message, (Throwable) null);
    }

    @Test
    void testWarnWithNullThrowable() {
        String message = "Test warn message";

        loggerAdapter.warn(message, (Throwable) null);

        verify(logger).warn(message, (Throwable) null);
    }

    @Test
    void testErrorWithNullThrowable() {
        String message = "Test error message";

        loggerAdapter.error(message, (Throwable) null);

        verify(logger).error(message, (Throwable) null);
    }
}
