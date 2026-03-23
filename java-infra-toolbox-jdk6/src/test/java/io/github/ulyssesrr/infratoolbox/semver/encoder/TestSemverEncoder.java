package io.github.ulyssesrr.infratoolbox.semver.encoder;

import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public interface TestSemverEncoder<T extends Comparable<T>> extends WithAssertions {


    SemverEncoder<T> createSemverEncoder();

    @Test
    default void testMajorVersionOrdering() {
        SemverEncoder<T> encoder = createSemverEncoder();

        SemanticVersion v100 = new SemanticVersion("1.0.0", 1, 0, 0, null);
        SemanticVersion v090 = new SemanticVersion("0.9.0", 0, 9, 0, null);

        assertThat(encoder.toValue(v100)).isGreaterThan(encoder.toValue(v090));
    }

    @Test
    default void testMinorVersionOrdering() {
        SemverEncoder<T> encoder = createSemverEncoder();

        SemanticVersion v110 = new SemanticVersion("1.1.0", 1, 1, 0, null);
        SemanticVersion v109 = new SemanticVersion("1.0.9", 1, 0, 9, null);

        assertThat(encoder.toValue(v110)).isGreaterThan(encoder.toValue(v109));
    }

    @Test
    default void testPatchVersionOrdering() {
        SemverEncoder<T> encoder = createSemverEncoder();

        SemanticVersion v101 = new SemanticVersion("1.0.1", 1, 0, 1, null);
        SemanticVersion v100 = new SemanticVersion("1.0.0", 1, 0, 0, null);

        assertThat(encoder.toValue(v101)).isGreaterThan(encoder.toValue(v100));
    }

    @Test
    default void testZeroVersionIsLowest() {
        SemverEncoder<T> encoder = createSemverEncoder();

        SemanticVersion v000 = new SemanticVersion("0.0.0", 0, 0, 0, null);
        SemanticVersion v100 = new SemanticVersion("1.0.0", 1, 0, 0, null);

        assertThat(encoder.toValue(v000)).isLessThan(encoder.toValue(v100));
    }
}
