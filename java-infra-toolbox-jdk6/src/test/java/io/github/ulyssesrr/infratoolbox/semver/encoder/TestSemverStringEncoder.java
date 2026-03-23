package io.github.ulyssesrr.infratoolbox.semver.encoder;

import org.junit.jupiter.api.Test;

import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;

public class TestSemverStringEncoder implements TestSemverEncoder<String> {

    @Override
    public SemverEncoder<String> createSemverEncoder() {
        return SemverStringEncoder.getInstance();
    }



    @Test
    void testSuffixOrdering() {
        SemverEncoder<String> encoder = createSemverEncoder();

        SemanticVersion v100alpha = new SemanticVersion("1.0.0-alpha", 1, 0, 0, "alpha");
        SemanticVersion v100beta = new SemanticVersion("1.0.0-beta", 1, 0, 0, "beta");

        assertThat(encoder.toValue(v100alpha)).isLessThan(encoder.toValue(v100beta));
    }

    @Test
    void testNullSuffixIsGreaterThanWithSuffix() {
        SemverEncoder<String> encoder = createSemverEncoder();

        SemanticVersion v100 = new SemanticVersion("1.0.0", 1, 0, 0, null);
        SemanticVersion v100alpha = new SemanticVersion("1.0.0-alpha", 1, 0, 0, "alpha");

        assertThat(encoder.toValue(v100)).isGreaterThan(encoder.toValue(v100alpha));
    }

    @Test
    void testSameVersionWithDifferentSuffixes() {
        SemverEncoder<String> encoder = createSemverEncoder();

        SemanticVersion v100alpha1 = new SemanticVersion("1.0.0-alpha.1", 1, 0, 0, "alpha.1");
        SemanticVersion v100alpha2 = new SemanticVersion("1.0.0-alpha.2", 1, 0, 0, "alpha.2");

        assertThat(encoder.toValue(v100alpha1)).isLessThan(encoder.toValue(v100alpha2));
    }

}
