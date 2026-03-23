package io.github.ulyssesrr.infratoolbox.semver.encoder;

import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;
import lombok.Getter;

/**
 * Encodes a {@link SemanticVersion} into a 64-bit long value using bit packing.
 *
 * <p>This encoder packs the MAJOR, MINOR, and PATCH version components into a single
 * long value, allowing for efficient storage and comparison. The encoding preserves
 * the natural ordering of semantic versions - higher versions produce higher long values.
 * The bit positions are determined by the configured bit counts for each component.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * SemanticVersion version = new SemanticVersion("1.2.3", 1, 2, 3, null);
 * SemverLongEncoder encoder = SemverLongEncoder.getInstance();
 * Long encoded = encoder.toValue(version);
 * // encoded can be compared: version 2.0.0 > version 1.9.9
 * }</pre>
 *
 * <p>Example with custom bit allocation:</p>
 * <pre>{@code
 * // Use 32 bits for major, 16 for minor, 16 for patch
 * SemverLongEncoder encoder = new SemverLongEncoder(32, 16, 16);
 * Long encoded = encoder.toValue(new SemanticVersion("3000000.20000.50", 3000000, 20000, 50, null));
 * }</pre>
 *
 * @author Ulysses R. Ribeiro
 * @see SemanticVersion
 * @see SemverEncoder
 * @see SemverIntEncoder
 * @see SemverStringEncoder
 * @see <a href="https://semver.org/">Semantic Versioning Specification</a>
 */
@Getter
public final class SemverLongEncoder implements SemverEncoder<Long> {

    /**
     * The number of bits allocated to the MAJOR version component.
     */
    private final int majorBits;

    /**
     * The number of bits allocated to the MINOR version component.
     */
    private final int minorBits;

    /**
     * The number of bits allocated to the PATCH version component.
     */
    private final int patchBits;

    /**
     * Returns a singleton instance with default bit allocation (22, 21, 21).
     *
     * @return the singleton encoder instance
     */
    public static SemverLongEncoder getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final SemverLongEncoder INSTANCE = new SemverLongEncoder(22, 21, 21);
    }

    /**
     * Creates a new SemverLongEncoder with the specified bit allocation.
     *
     * <p>The sum of majorBits, minorBits, and patchBits must equal 64.</p>
     *
     * @param majorBits the number of bits to allocate for the MAJOR version (must be positive)
     * @param minorBits the number of bits to allocate for the MINOR version (must be positive)
     * @param patchBits the number of bits to allocate for the PATCH version (must be positive)
     * @throws IllegalArgumentException if the sum of bits is not 64
     */
    public SemverLongEncoder(int majorBits, int minorBits, int patchBits) {
        if (majorBits + minorBits + patchBits != 64) {
            throw new IllegalArgumentException("The sum of majorBits, minorBits, and patchBits must equal 64");
        }
        this.majorBits = majorBits;
        this.minorBits = minorBits;
        this.patchBits = patchBits;
    }

    /**
     * Encodes the given semantic version into a 64-bit long value.
     *
     * <p>The version components are packed into the long using the configured bit counts.
     * The MAJOR component occupies the most significant bits, followed by MINOR, then PATCH.
     * The encoding preserves version ordering - comparing the encoded longs will produce
     * the same result as comparing the original semantic versions.</p>
     *
     * @param v the semantic version to encode, cannot be {@code null}
     * @return the encoded long value representing the semantic version
     */
    public Long toValue(SemanticVersion v) {
        long major = ((long)v.getMajor()) << (minorBits + patchBits);
        long minor = ((long)v.getMinor()) << patchBits;
        long patch = v.getPatch();

        return major | minor | patch;
    }

}
