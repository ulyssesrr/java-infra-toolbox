package io.github.ulyssesrr.infratoolbox.semver;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public class TestJdkSemanticVersionParser implements WithAssertions {

    private final SemanticVersionParser parser = JdkSemanticVersionParser.INSTANCE;

    @Test
    public void testParseSimpleVersion() {
        SemanticVersion version = parser.parse("1.0.0");
        assertThat(version.getOriginalVersion()).isEqualTo("1.0.0");
        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(0);
        assertThat(version.getPatch()).isEqualTo(0);
        assertThat(version.getSuffix()).isNull();
    }

    @Test
    public void testParseVersionWithSuffix() {
        SemanticVersion version = parser.parse("1.0.0-beta");
        assertThat(version.getOriginalVersion()).isEqualTo("1.0.0-beta");
        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(0);
        assertThat(version.getPatch()).isEqualTo(0);
        assertThat(version.getSuffix()).isEqualTo("beta");
    }

    @Test
    public void testParseVersionWithNumericPatchAndSuffix() {
        SemanticVersion version = parser.parse("2.3.4-SNAPSHOT");
        assertThat(version.getOriginalVersion()).isEqualTo("2.3.4-SNAPSHOT");
        assertThat(version.getMajor()).isEqualTo(2);
        assertThat(version.getMinor()).isEqualTo(3);
        assertThat(version.getPatch()).isEqualTo(4);
        assertThat(version.getSuffix()).isEqualTo("SNAPSHOT");
    }

    @Test
    public void testParseVersionWithAlphaSuffix() {
        SemanticVersion version = parser.parse("1.2.3-alpha.1");
        assertThat(version.getOriginalVersion()).isEqualTo("1.2.3-alpha.1");
        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(2);
        assertThat(version.getPatch()).isEqualTo(3);
        assertThat(version.getSuffix()).isEqualTo("alpha.1");
    }

    @Test
    public void testParseMajorOnly() {
        SemanticVersion version = parser.parse("1");
        assertThat(version.getOriginalVersion()).isEqualTo("1");
        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(0);
        assertThat(version.getPatch()).isEqualTo(0);
        assertThat(version.getSuffix()).isNull();
    }

    @Test
    public void testParseMajorOnlyWithDot() {
        SemanticVersion version = parser.parse("1.");
        assertThat(version.getOriginalVersion()).isEqualTo("1.");
        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(0);
        assertThat(version.getPatch()).isEqualTo(0);
        assertThat(version.getSuffix()).isNull();
    }

    @Test
    public void testParseMajorMinorOnly() {
        SemanticVersion version = parser.parse("1.0");
        assertThat(version.getOriginalVersion()).isEqualTo("1.0");
        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(0);
        assertThat(version.getPatch()).isEqualTo(0);
        assertThat(version.getSuffix()).isNull();
    }

    @Test
    public void testParseMajorMinorOnlyWithDot() {
        SemanticVersion version = parser.parse("1.0.");
        assertThat(version.getOriginalVersion()).isEqualTo("1.0.");
        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(0);
        assertThat(version.getPatch()).isEqualTo(0);
        assertThat(version.getSuffix()).isNull();
    }

    @Test
    public void testParseMajorMinorSuffixOnly() {
        SemanticVersion version = parser.parse("1.0.A");
        assertThat(version.getOriginalVersion()).isEqualTo("1.0.A");
        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(0);
        assertThat(version.getPatch()).isEqualTo(0);
        assertThat(version.getSuffix()).isEqualTo("A");
    }

    @Test
    public void testParseVersionWithPatchOnlyAndSuffix() {
        SemanticVersion version = parser.parse("1.0.1-RC1");
        assertThat(version.getOriginalVersion()).isEqualTo("1.0.1-RC1");
        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(0);
        assertThat(version.getPatch()).isEqualTo(1);
        assertThat(version.getSuffix()).isEqualTo("RC1");
    }

    @Test
    public void testParseEmptyPatchWithSuffix() {
        SemanticVersion version = parser.parse("1.0.0+build.123");
        assertThat(version.getOriginalVersion()).isEqualTo("1.0.0+build.123");
        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(0);
        assertThat(version.getPatch()).isEqualTo(0);
        assertThat(version.getSuffix()).isEqualTo("build.123");
    }

    @Test
    public void testParseEmptyPatchWithBuildNumberSuffix() {
        SemanticVersion version = parser.parse("1.0.0+123");
        assertThat(version.getOriginalVersion()).isEqualTo("1.0.0+123");
        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(0);
        assertThat(version.getPatch()).isEqualTo(0);
        assertThat(version.getSuffix()).isEqualTo("123");
    }

    @Test
    public void testParsePatchWithNoSuffix() {
        SemanticVersion version = parser.parse("1.2.3");
        assertThat(version.getOriginalVersion()).isEqualTo("1.2.3");
        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(2);
        assertThat(version.getPatch()).isEqualTo(3);
        assertThat(version.getSuffix()).isNull();
    }

    @Test
    public void testParseZeroVersion() {
        SemanticVersion version = parser.parse("0.0.0");
        assertThat(version.getOriginalVersion()).isEqualTo("0.0.0");
        assertThat(version.getMajor()).isEqualTo(0);
        assertThat(version.getMinor()).isEqualTo(0);
        assertThat(version.getPatch()).isEqualTo(0);
        assertThat(version.getSuffix()).isNull();
    }

    @Test
    public void testParseHighVersionNumbers() {
        SemanticVersion version = parser.parse("999.888.777");
        assertThat(version.getOriginalVersion()).isEqualTo("999.888.777");
        assertThat(version.getMajor()).isEqualTo(999);
        assertThat(version.getMinor()).isEqualTo(888);
        assertThat(version.getPatch()).isEqualTo(777);
        assertThat(version.getSuffix()).isNull();
    }
}
