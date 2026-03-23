package io.github.ulyssesrr.infratoolbox.semver.encoder;

import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Encodes a {@link SemanticVersion} into a padded string representation.
 *
 * <p>
 * This encoder converts a semantic version into a string format where each
 * version
 * component (MAJOR, MINOR, PATCH) is zero-padded to a fixed number of digits.
 * This allows
 * for lexicographic string comparison to produce the same result as semantic
 * version comparison.
 * The string format is {@code MAJOR.MINOR.PATCH} with an optional suffix
 * indicator.
 * </p>
 *
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>{@code
 * SemanticVersion version = new SemanticVersion("1.2.3", 1, 2, 3, null);
 * SemverStringEncoder encoder = SemverStringEncoder.getInstance();
 * String encoded = encoder.toValue(version);
 * // encoded = "000001.000002.000003;"
 * }</pre>
 *
 * <p>
 * Example with pre-release version:
 * </p>
 *
 * <pre>{@code
 * SemanticVersion version = new SemanticVersion("1.0.0-alpha", 1, 0, 0, "alpha");
 * String encoded = encoder.toValue(version);
 * // encoded = "000001.000000.000000:alpha"
 * }</pre>
 *
 * <p>
 * Example with custom digit configuration:
 * </p>
 *
 * <pre>{@code
 * SemverStringEncoder encoder = SemverStringEncoder.builder()
 *         .majorDigits(4)
 *         .minorDigits(4)
 *         .patchDigits(4)
 *         .includeSuffix(false)
 *         .build();
 * String encoded = encoder.toValue(new SemanticVersion("1.2.3", 1, 2, 3, null));
 * // encoded = "0001.0002.0003"
 * }</pre>
 *
 * @author Ulysses R. Ribeiro
 * @see SemanticVersion
 * @see SemverEncoder
 * @see SemverIntEncoder
 * @see SemverLongEncoder
 * @see <a href="https://semver.org/">Semantic Versioning Specification</a>
 */
@Getter
@Builder
@RequiredArgsConstructor
public final class SemverStringEncoder implements SemverEncoder<String> {

    public static final String RELEASE_MARKER = ";";

    public static final String PRERELEASE_MARKER = ":";

    /**
     * The number of digits to use for the MAJOR version component.
     * Defaults to 6.
     */
    @Builder.Default
    private final int majorDigits = 6;

    /**
     * The number of digits to use for the MINOR version component.
     * Defaults to 6.
     */
    @Builder.Default
    private final int minorDigits = 6;

    /**
     * The number of digits to use for the PATCH version component.
     * Defaults to 6.
     */
    @Builder.Default
    private final int patchDigits = 6;

    /**
     * The maximum length of the suffix to include in the encoded string.
     * The suffix is truncated if it exceeds this length.
     * Defaults to 12.
     */
    @Builder.Default
    private final int suffixLength = 12;

    /**
     * Whether to include the version suffix in the encoded string.
     * When true, release versions (no suffix) are marked with {@code ;}
     * and pre-release versions are marked with {@code :} followed by the suffix.
     * Defaults to true.
     */
    @Builder.Default
    private final boolean includeSuffix = true;

    /**
     * Returns a singleton instance with default configuration.
     * *
     * <p>
     * Default configuration uses 6 digits for major, 6 digits for minor, and 6
     * digits for patch,
     * producing strings like {@code "000001.000002.000003;"}. Release versions
     * (without suffix)
     * are marked with a semicolon ({@code ;}), while pre-release versions are
     * marked with a colon
     * ({@code :}) followed by the suffix (truncated to 12 characters by default).
     * </p>
     *
     * @return the singleton encoder instance
     */
    public static SemverStringEncoder getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final SemverStringEncoder INSTANCE = SemverStringEncoder.builder().build();
    }

    /**
     * Encodes the given semantic version into a padded string representation.
     *
     * <p>
     * The version components are zero-padded to the configured number of digits.
     * If includeSuffix is true, the output includes a separator and optional
     * suffix:
     * </p>
     * <ul>
     * <li>{@code ;} - for release versions (no suffix)</li>
     * <li>{@code :<suffix>} - for pre-release versions</li>
     * </ul>
     *
     * <p>
     * The string format allows for lexicographic comparison to produce the same
     * result as semantic version comparison.
     * </p>
     *
     * @param v the semantic version to encode, cannot be {@code null}
     * @return the encoded string representation of the semantic version
     */
    public String toValue(SemanticVersion v) {
        StringBuilder sb = new StringBuilder();
        sb.append(pad(v.getMajor(), this.getMajorDigits()));
        sb.append('.');
        sb.append(pad(v.getMinor(), this.getMinorDigits()));
        sb.append('.');
        sb.append(pad(v.getPatch(), this.getPatchDigits()));

        if (includeSuffix) {
            String suffix = v.getSuffix();
            if (suffix == null) {
                // release marker (HIGH)
                sb.append(RELEASE_MARKER);
            } else {
                // prerelease marker (LOW)
                sb.append(PRERELEASE_MARKER);
                sb.append(suffix.substring(0, Math.min(suffixLength, suffix.length())));
            }
        }

        return sb.toString();
    }

    /**
     * Pads the given integer value to the specified length with leading zeros.
     *
     * @param value  the value to pad
     * @param length the target length in digits
     * @return the zero-padded string representation
     */
    private String pad(int value, int length) {
        return String.format("%0" + length + "d", value);
    }
}
